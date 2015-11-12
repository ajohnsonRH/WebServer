<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>I am not your friend</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
		var functionArray = ['get', 'put', 'post', 'delete'];
		var callInterval;
		var callAjax = function(){
		  var randFunc = functionArray[Math.floor(Math.random()*functionArray.length)];
		  var servletUrl = "http://localhost:8080/GetPlugin/";
		  var successFunc = successFunc = function(resultData){console.log(result)};
		  var data = "";
		  var isNotGet = true;
		  $("#funcDiv").text(randFunc);
		  
		  if(randFunc === "get"){
			//$.get("http://localhost:8080/GetPlugin/GetServlet/magic.txt", function(responseText) {});
			isNotGet = false;
		  }
		  else if(randFunc === "put"){
			servletUrl = servletUrl.concat("PostServlet/");
			data = "hurr durr ima data";
		  }
		  else if(randFunc === "post"){
			servletUrl = servletUrl.concat("PutServlet/");
			data = "hurr durr ima data";
		  }
		  else if(randFunc === "delete"){
			servletUrl = servletUrl.concat("PostServlet/");
			data = "hurr durr ima data";
		  }
		  
		  if(isNotGet){
			  //$.ajax({
				//type: randFunc,
				// 	 url: servletUrl,
				// 	 data: data,
				// 	 dataType: "text",
				// 	 success:successFunc
			  //});
		  }
		}
      $(document).on("click", "#startTest", function() {
		$("#statusDiv").text("DOS started");
        callInterval = setInterval(callAjax,500);
      });
	  
	  $(document).on("click", "#stopTest", function() {
		$("#statusDiv").text("DOS stopped");
        clearInterval(callInterval);
      });
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  </head>
  <body>
    <button id="startTest">Start DOS</button>
	<button id="stopTest">Stop DOS</button>
	<div id="statusDiv"></div>
	<div id="funcDiv"></div>
  </body>
</html>