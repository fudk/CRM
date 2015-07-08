package com.greatwall.api.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface RechargeService {

    public int add(@WebParam(name = "num1") int num1,  
            @WebParam(name = "num2") int num2);
}
