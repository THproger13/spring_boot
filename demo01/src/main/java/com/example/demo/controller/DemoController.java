package com.example.demo.controller;

import com.example.demo.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor// final 이 붙은 애들 대상으로 만드는 생성자
public class DemoController {

//    //생성자 주입
//    private DemoService demoService;
//    //DemoSERVICE 객체를 매개변수로 하는 생성자
//    //기본 생성자는 사용하지 못한다. 이미 demoservice를 매개 변수로 받았기 때문
//    @Autowired
//    public DemoController(DemoService demoService) {
//        this.demoService = demoService;
//    }

    //생성자 주입 좀 더 편하게- final을 붙임으로써 바뀌지 않음을 정의
    private final DemoService demoService;
//    private DemoService1 demoService1;
//    private final DemoService2 demoService2;
//
//    public DemoController(DemoService demoService, DemoService2 demoService2) {
//        this.demoService = demoService;
//        this.demoService2 = demoService2;
    }

