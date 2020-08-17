package com.atguigu.springcloud.alibaba.domain;


import lombok.Data;


@Data
public class Storage {
    //产品id
    private Long id;
    //产品总库存
    private Integer total;
    //已用库存
    private Integer used;
    //剩余库存
    private Integer residue;
}
