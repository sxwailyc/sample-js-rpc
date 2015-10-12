
# sample-js-rpc Is a simple RPC JSON implementation #

## Server ##

    
1. config

      let spring scan the js-rpc core class 

    `<context:component-scan base-package="com.dt.sample.js.rpc.controller"></context:component-scan>`


2. java codeing

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

## Client ##

     service.say('han mei mei') -> hello han mei mei

     service.getTime() -> 1444614301489

     service.addition(1, 2) -> 3