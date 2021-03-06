package com.javatest.controller;

import com.javatest.service.impl.LoopTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/loopTest")
public class LoopTestController {

    @Autowired
    private LoopTestServiceImpl loopTestService;

    @RequestMapping("/test1")
    public String test1(){
        loopTestService.loopTest1();
        return "ok";
    }
    @RequestMapping("/test2")
    public String test2(){
        loopTestService.loopTest2();
        return "ok";
    }
    @RequestMapping("/test3")
    public String test3(){
        loopTestService.loopTest3();
        return "ok";
    }
    @RequestMapping("/test4")
    public String test4(){
        loopTestService.loopTest4();
        return "ok";
    }
    @RequestMapping("/test5")
    public String test5(){
        loopTestService.loopTest5();
        return "ok";
    }
    @RequestMapping("/test6")
    public String test6(){
        loopTestService.loopTest6();
        return "ok";
    }
    @RequestMapping("/test7")
    public String test7(){
        loopTestService.loopTest7();
        return "ok";
    }
    @RequestMapping("/test8")
    public String test8(){
        loopTestService.loopTest8();
        return "ok";
    }
    @RequestMapping("/test9")
    public String test9(){
        loopTestService.loopTest9();
        return "ok";
    }
    @RequestMapping("/test10")
    public String test10(){
        loopTestService.loopTest10();
        return "ok";
    }
    @RequestMapping("/test11")
    public String test11(){
        loopTestService.loopTest11();
        return "ok";
    }

    @RequestMapping("/test12")
    public String test12(){
        loopTestService.loopTest12();
        return "ok";
    }

    @RequestMapping("/test13")
    public String test13(){
        loopTestService.loopTest13();
        return "ok";
    }

    @RequestMapping("/test14")
    public String test14(){
        loopTestService.loopTest14();
        return "ok";
    }

    @RequestMapping("/test15")
    public String test15(){
        loopTestService.loopTest15();
        return "ok";
    }

    @RequestMapping("/test16")
    public String test16(){
        loopTestService.loopTest16();
        return "ok";
    }

    @RequestMapping("/test17")
    public String test17(){
        try {
            loopTestService.loopTest17();
        } catch (IOException e) {
            System.out.println(111);
        }
        return "ok";
    }

    @RequestMapping("/test18")
    public String test18(){
        loopTestService.loopTest18();
        return "ok";
    }

    @RequestMapping("/test19")
    public String test19(){
        loopTestService.loopTest19();
        return "ok";
    }



    // 这个是用来测试方法调用传参是地址值，跟上面的循环没有关系
    @RequestMapping("/variableTest")
    public String variableTest(){
        loopTestService.variableTest();
        return "ok";
    }
}
