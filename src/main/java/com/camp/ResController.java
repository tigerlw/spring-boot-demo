package com.camp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "contacts-api", description = "有关于用户的CURD操作", position = 5)
@RestController
public class ResController {
	
	@RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public String get() {
        return "123";
    }

}
