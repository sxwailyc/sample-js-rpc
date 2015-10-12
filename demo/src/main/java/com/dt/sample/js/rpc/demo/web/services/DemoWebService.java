package com.dt.sample.js.rpc.demo.web.services;

import java.util.Date;

public interface DemoWebService {

	public String say(String name);

	public Date getTime();

	public int addition(int num1, int num2);
}
