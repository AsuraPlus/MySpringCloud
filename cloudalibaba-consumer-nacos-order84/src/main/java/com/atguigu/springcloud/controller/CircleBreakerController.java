package com.atguigu.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/fallBack/{id}")
    //@SentinelResource(value = "fallBack") 没有配置
    //@SentinelResource(value = "fallBack", fallback = "handlerFallBack") //fallBack只负责业务异常
    //@SentinelResource(value = "fallBack",blockHandler = "blockHandler") //只负责控制台配置违规
    @SentinelResource(value = "fallBack",
            fallback = "handlerFallBack",
            blockHandler = "blockHandler",
            exceptionsToIgnore = {IllegalArgumentException.class}
    )
    public CommonResult<Payment> fallBack(@PathVariable("id") Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/"+id, CommonResult.class, id);
        if(id == 4){
            throw new IllegalArgumentException("IllegalArgumentException，非法参数异常....");
        }else if(result.getData() == null){
            throw new NullPointerException("NullPointerException，空指针异常....");
        }
        return result;
    }

    //本例是fallBack
    public CommonResult<Payment> handlerFallBack(@PathVariable("id") Long id, Throwable e){
        Payment payment = new Payment(id, null);
        return new CommonResult<>(444, "兜底异常handlerFallBack，excetpion内容："+ e.getMessage(), payment);
    }

    //本例是blockHandler
    public CommonResult blockHandler(@PathVariable  Long id, BlockException blockException) {
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(445,"blockHandler-sentinel限流,无此流水: blockException  "+blockException.getMessage(),payment);
    }

    //------------------------openFeign

    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
       return paymentService.paymentSQL(id);
    }


}
