package com.atguigu.springcloud.alibaba.dao;

import com.atguigu.springcloud.alibaba.domian.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {

    //新建订单
    public void create(Order order);

    //修改订单 从0 改到 1
    void update(@Param("userId") Long userId, @Param("status") Integer status);

}
