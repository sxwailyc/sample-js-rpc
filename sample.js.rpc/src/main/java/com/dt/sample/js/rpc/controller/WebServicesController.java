package com.dt.sample.js.rpc.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.dt.sample.js.rpc.model.RpcRequest;
import com.dt.sample.js.rpc.util.DateUtil;
import com.dt.sample.js.rpc.util.Json;

@Controller
@RequestMapping(value = WebServicesController.DIR)
public class WebServicesController {

	public static final String DIR = "/services/";

	protected static Log logger = LogFactory.getLog(WebServicesController.class);

	/**
	 * 得到方法,约定service里不允许同名方法
	 * 
	 * @param obj
	 * @param method
	 * @return
	 */
	private Method getMethod(Object obj, String methodName) {
		Method[] methods = obj.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}

	private Object[] getParams(HttpServletRequest request, Object[] params, Class<?>[] paramTypes) {

		Object[] newParams = null;

		int pos = 0;
		if (paramTypes.length > 0 && paramTypes[0] == HttpServletRequest.class) {
			newParams = new Object[params.length + 1];
			newParams[0] = request;
			pos = 1;
		} else {
			newParams = new Object[params.length];
		}

		for (int i = 0; i < params.length; i++) {
			Class<?> cls = paramTypes[pos + i];
			Object o = null;
			Object param = params[i];
			if (param == null) {
				newParams[i + pos] = o;
				continue;
			}
			if (cls == Integer.class || cls == int.class) {
				o = Integer.parseInt(params[i].toString());
			} else if (cls == String.class) {
				o = params[i].toString();
			} else if (cls == Long.class || cls == long.class) {
				o = Long.parseLong(params[i].toString());
			} else if (cls == Double.class || cls == double.class) {
				o = Double.parseDouble(params[i].toString());
			} else if (cls == Date.class) {
				try {
					o = DateUtil.str2Date(params[i].toString());
				} catch (Exception e) {
					o = params[i];
				}
			} else {
				try {
					o = Json.toObject(Json.toJson(params[i]), cls);
				} catch (Exception e) {
					o = params[i];
				}
			}
			newParams[i + pos] = o;
		}
		return newParams;
	}

	private Object getService(ApplicationContext context, String serviceName) {
		try {
			if (context.containsBean(serviceName + "WebServiceImpl")) {
				return context.getBean(serviceName + "WebServiceImpl");
			}

			if (context.containsBean(serviceName + "WebService")) {
				return context.getBean(serviceName + "WebService");
			}

		} catch (Exception e) {
			logger.info("service[" + serviceName + "]不存在");
		}

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "{serviceName}")
	public Map<String, Object> call(HttpServletRequest request, @RequestBody RpcRequest rpcRequest, @PathVariable String serviceName) {

		ApplicationContext context = RequestContextUtils.getWebApplicationContext(request);

		Map<String, Object> result = new HashMap<String, Object>();
		String error = null;

		logger.info("serviceName[" + serviceName + "], method[" + rpcRequest.getMethod() + "].params[" + rpcRequest.getParams() + "]");

		Object service = this.getService(context, serviceName);

		if (service != null) {

			Method m = null;

			service.getClass().getMethods();

			m = getMethod(service, rpcRequest.getMethod());

			if (m != null) {
				try {

					Object[] params = getParams(request, rpcRequest.getParams().toArray(), m.getParameterTypes());
					Object obj = m.invoke(service, params);
					result.put("result", obj);

				} catch (InvocationTargetException ie) {
					logger.error(ie.getMessage(), ie);
					error = ie.getTargetException().getMessage();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					error = "服务器异常";
				}
			} else {
				error = "service对应的方法不存在.[" + rpcRequest.getMethod() + "]";
			}

		} else {
			error = "service不存在.[" + serviceName + "]";
		}

		result.put("error", error);
		result.put("id", rpcRequest.getId());

		return result;
	}
}
