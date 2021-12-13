package com.happy.lucky.controller;

import com.happy.lucky.common.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/test")
    public R test() {
        return R.success("做得很好");
    }
}
