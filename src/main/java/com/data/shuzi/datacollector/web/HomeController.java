package com.data.shuzi.datacollector.web;

import com.data.shuzi.datacollector.limit.ServiceLimit;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author zizuo.zdh
 * @ClassName HomeController
 * @Description TODO
 * @Date 2018/6/25 14:12
 * @Version 1.0
 **/
@RestController
public class HomeController {
    /**
     * @return java.lang.String
     * @author zizuo.zdh
     * @Description TODO
     * @Date 2018/6/25 14:22
     * @Param []
     **/
    @Async("my-task-pool")
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String hello(HttpServletRequest request) {
        return "hello";
    }

   // @ServiceLimit
    @GetMapping(value = "/sayHello")
    public String sayHello(HttpServletRequest request, @RequestParam(value = "name") String name) {

        return "hello\t" + name;
    }
}
