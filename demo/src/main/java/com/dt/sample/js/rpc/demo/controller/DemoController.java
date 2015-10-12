package com.dt.sample.js.rpc.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

	@RequestMapping("/demo.do")
	public ModelAndView demo() {

		return new ModelAndView("demo");
	}

}
