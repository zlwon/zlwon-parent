package com.zlwon.web.controller;

import com.zlwon.rest.ResultData;
import com.zlwon.server.service.HelloService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/web")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @ApiOperation(value = "")
    @GetMapping(value = "/hello/{word}")
    public ResultData sayHello(@PathVariable String word) {
        return ResultData.one(helloService.hello(word));
    }
}
