package com.example.dynamicscheduledemo.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.example.dynamicscheduledemo.constants.WorkStatusEnum;
import com.example.dynamicscheduledemo.dao.WorkCronDao;
import com.example.dynamicscheduledemo.dao.WorkInfoDao;
import com.example.dynamicscheduledemo.entity.WorkCronInfo;
import com.example.dynamicscheduledemo.schedule.WorkTaskRunnable;
import com.example.dynamicscheduledemo.service.WorkInfoService;

/**
 * @author grafie
 * @since 2021/12/24 8:46
 */
@EnableScheduling
@Configuration
public class DynamicSchedulingConfigurer implements SchedulingConfigurer, CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DynamicSchedulingConfigurer.class);
    /**
     * key->taskId，value->ScheduledFuture
     */
    private final ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduledFutureMap = new ConcurrentHashMap<>();
    private volatile ScheduledTaskRegistrar taskRegistrar;
    @Autowired
    private WorkInfoDao workInfoDao;
    @Autowired
    private WorkInfoService workInfoService;
    @Autowired
    private WorkCronDao workCronDao;

    @Override
    public void run(String... args) {
        // 不做异常处理，如果报错会影响启动
        initWhenStartApp();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
        setScheduler();
    }

    private ExecutorService createExecutorService() {
        // 创建一个线程池，按需创建
        return Executors.newScheduledThreadPool(5);
    }

    private TaskScheduler setScheduler() {
        if (taskRegistrar.getScheduler() == null) {
            synchronized (DynamicSchedulingConfigurer.class) {
                if (taskRegistrar.getScheduler() == null) {
                    taskRegistrar.setScheduler(createExecutorService());
                }
            }
        }
        return taskRegistrar.getScheduler();
    }

    /**
     * 添加定时任务
     *
     * @param workCronInfo 定时任务信息
     * @return java.lang.Boolean  添加任务结果
     */
    public Boolean addTask(WorkCronInfo workCronInfo) {
        Integer status = workInfoDao.queryWorkInfoStatus(workCronInfo.getWorkInfoId());
        if (!WorkStatusEnum.VALID.getCode().equals(status)) {
            workCronDao.updateStatus(workCronInfo.getWorkInfoId(), WorkStatusEnum.INVALID.getCode());
            logger.warn("添加失败，work info 状态非启用状态,更新cron表状态为 -1 失效");
            return Boolean.FALSE;
        }
        WorkTaskRunnable workTaskRunnable = new WorkTaskRunnable(workCronInfo, workInfoService);
        CronTrigger cronTrigger = new CronTrigger(workCronInfo.getCron());
        TaskScheduler taskScheduler = taskRegistrar.getScheduler();
        if (taskScheduler == null) {
            taskScheduler = setScheduler();
        }
        ScheduledFuture<?> future = taskScheduler.schedule(workTaskRunnable, cronTrigger);
        if (future != null) {
            scheduledFutureMap.put(workCronInfo.getWorkInfoId(), future);
            logger.info("workInfoId为{}的工作开始执行，定时任务cron为{}", workCronInfo.getWorkInfoId(), workCronInfo.getCron());
        }
        return Boolean.TRUE;
    }

    public void stopTask(Integer workInfoId) {
        if (exist(workInfoId)) {
            ScheduledFuture<?> future = scheduledFutureMap.get(workInfoId);
            if (future != null) {
                // 如果想立即停止，就true，如果允许执行完，就false
                future.cancel(true);
                scheduledFutureMap.remove(workInfoId);
                logger.info("停止任务，workInfoId->{}", workInfoDao);
            }
        } else {
            logger.info("线程池中不包含workInfoId为{}的任务，无需跳过", workInfoId);
        }
    }

    public Boolean exist(Integer workInfoId) {
        return scheduledFutureMap.containsKey(workInfoId);
    }

    @PreDestroy
    public void destroy() {
        // 销毁configurer之前，先把taskRegistrar销毁掉
        taskRegistrar.destroy();
    }

    private void initWhenStartApp() {
        logger.info("项目启动，加载数据库中的定时任务，dateTime=>{}", LocalDateTime.now());
        List<WorkCronInfo> workCronInfoList = workCronDao.queryCronByStatusAndEndDateTime(
            WorkStatusEnum.VALID.getCode(), LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
        if (workCronInfoList.isEmpty()) {
            logger.info("定时任务为空，跳过加载");
        }
        workCronInfoList.forEach(this::addTask);
    }
}
