<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>sample js rpc demo</title>
<script src="/js/jquery-1.9.1.min.js"></script>
<script src="/js/rpc.js"></script>
<script type="text/javascript">

    var service = new rpc.ServiceProxy("/services/demo", {asynchronous:false, methods: ['say', 'getTime', 'addition']});
    
    $(document).ready(function(){
    	
    	$('#say').click(function(){
    		
    		var name = $('#name').val()
    		try{
    		    var res = service.say(name);
    		    alert(res);
    		}catch(error){
    			alert(error);
    		}
    	});
    	
        $('#getTime').click(function(){
    		
    		try{
    		    var res = service.getTime();
                $('#time').val(res);
    		}catch(error){
    			alert(error);
    		}
    	});
        
        $('#addition').click(function(){
    		
        	var num1 = $('#num1').val()
        	var num2 = $('#num2').val()
    		try{
    		    var res = service.addition(num1, num2);
                $('#num3').val(res);
    		}catch(error){
    			alert(error);
    		}
    	});
    	
    });

</script>
</head>
<body>

<h1>Sample js rpc demo </h1>
<input type="text" id="name" value="韩梅梅" />
<input id="say" type="button" value="say hello"/>
<br/><br/>
<input type="text" id="time" value="" />
<input id="getTime" type="button" value="get server time"/>
<br/><br/>
<input type="text" id="num1" value="1" /> + <input type="text" id="num2" value="2" /> = <input type="text" id="num3" value="" />
<input id="addition" type="button" value="addition"/>
</body>
</html>