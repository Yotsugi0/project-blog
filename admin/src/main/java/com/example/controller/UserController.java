package com.example.controller;

import com.example.domain.ResponseResult;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status){
        return userService.getUserList(pageNum,pageSize,userName,phonenumber,status);
    }
}
