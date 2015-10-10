package com.dt.sample.js.rpc.demo.web.services.impl;

import java.util.Date;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dt.sample.js.rpc.demo.web.services.DemoWebService;

@Service
@Lazy(false)
public class DemoWebServiceImpl implements DemoWebService {

	@Override
	public String say(String name) {
		return "hello " + name;
	}

	@Override
	public Date getTime() {
		return new Date();
	}

	@Override
	public int addition(int num1, int num2) {
		return num1 + num2;
	}

}
