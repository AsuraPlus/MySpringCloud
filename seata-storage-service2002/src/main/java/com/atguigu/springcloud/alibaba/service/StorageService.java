package com.atguigu.springcloud.alibaba.service;

public interface StorageService {
    //扣减库存
    public void decrease(Long productId, Integer count);
}
