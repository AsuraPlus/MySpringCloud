package com.atguigu.springcloud.alibaba.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface AccountService {
    public void decrease(@RequestParam("useId") Long userId,@RequestParam("money") BigDecimal money);
}
