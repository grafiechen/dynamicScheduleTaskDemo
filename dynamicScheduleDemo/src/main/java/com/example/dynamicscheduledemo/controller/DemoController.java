package com.example.dynamicscheduledemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dynamicscheduledemo.controller.request.AddWorkInfoRequest;
import com.example.dynamicscheduledemo.service.WorkInfoService;

/**
 * com.example.dynamicscheduledemo.controller
 *
 * @author grafie
 * @since 2021/10/8 10:36
 */
@RestController
@RequestMapping("/demo/api/workInfo")
public class DemoController {
    @Autowired
    private WorkInfoService workInfoService;

    @PostMapping
    public String addWorkInfo(@RequestBody AddWorkInfoRequest request) {
        workInfoService.addWorkInfo(request);
        return "success";
    }

    @DeleteMapping
    public String deleteWorkInfo(@RequestParam("workInfoId") Integer workInfoId) {
        workInfoService.deleteWorkInfo(workInfoId);
        return "success";
    }

    @PutMapping
    public String invalidWorkInfo(@RequestParam("workInfoId") Integer workInfoId) {
        workInfoService.invalidWorkInfo(workInfoId);
        return "success";
    }
}
