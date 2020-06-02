package com.atguigu.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "thread pool:" + Thread.currentThread().getName()+" paymentInfo_OK, id = "+ id;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String paymentInfo_Timeout(Integer id){
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "thread pool:" + Thread.currentThread().getName()+" paymentInfo_Timeout, id = "+ id + "****************spend 3 seconds";
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "server error";
    }
}
