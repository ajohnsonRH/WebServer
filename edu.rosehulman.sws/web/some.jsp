<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#getButton", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
                $.get("http://localhost:8080/BasketballTeamPlugin/ExtraServlet/", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#getdiv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
            });
        </script>
          <script>
            $(document).on("click", "#postButton", function() { 
            
              $.ajax(
              {
             	 type: 'POST',
             	 url:"http://localhost:8080/BasketballTeamPlugin/PostServlet/",
             	 data: $("#team_name_id").serialize(),
             	 dataType: "text",
             	 success:function(resultData){alert("save completed!")}
              });
            });
        </script>
         <script>
            $(document).on("click", "#putButton", function() { 
              $.ajax(
              {
             	 type: 'PUT',
             	 url:"http://localhost:8080/GetPlugin/PutServlet/",
             	 data: "magic.txt:overwriting text",
             	 dataType: "text",
             	 success:function(resultData){alert("save completed!")}
              });
            });
        </script>
         <script>
            $(document).on("click", "#putNewButton", function() { 
              $.ajax(
              {
             	 type: 'PUT',
             	 url:"http://localhost:8080/GetPlugin/PutServlet/",
             	 data: "newFile.txt:new file text",
             	 dataType: "text",
             	 success:function(resultData){alert("save completed!")}
              });
            });
        </script>
        <script>
            $(document).on("click", "#deleteButton", function() { 
              $.ajax(
              {
             	 type: 'DELETE',
             	 url:"http://localhost:8080/GetPlugin/DeleteServlet/",
             	 data: "newFile.txt",
             	 dataType: "text",
             	 success:function(resultData){alert("Delete completed!")}
              });
            });
        </script>
    </head>
    <body>
    	<input type="text" name="team_name_id" id ="team_name_id">
    	<button id="extraButton">Add a new Team</button>
    	<div/>
        <button id="getButton">Ajax GET</button>
        <div id="getdiv"></div>
        
        <button id="postButton">Ajax POST</button>
        <div id="postdiv"></div>
        
        <button id="putButton">Ajax PUT to magic.txt</button>
		<div/>
        <button id="putNewButton">Ajax PUT to newFile.txt</button>
		<div/>
        <button id="deleteButton">Ajax DELETE</button>
		<div/>
        
    </body>
</html>