<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#getButton", function() { // When HTML DOM "click" event is invoked on element with ID "somebutton", execute the following function...
                $.get("http://localhost:8080/BasketballTeamPlugin/ExtraServlet/", function(responseText) {   // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
                    $("#teamdiv").text(responseText);           // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
                });
            });
        </script>
          <script>
            $(document).on("click", "#addATeam", function() { 
            
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
    	<button id="addATeam">Add a new Team</button>
    	<br></br>
        <button id="getButton">List teams</button>
        <div id="teamdiv"></div>
        <p>Old team name:</p>
        <input type="text" name="old_team" id ="old_team">
        <p>New team name:</p>
        <input type="text" name="new_team" id ="new_team">
        <br></br>
        <button id="putButton">Update Team name</button>
    	<br></br>
        <button id="putNewButton">Ajax PUT to newFile.txt</button>
    	<br></br>
        <button id="deleteButton">Ajax DELETE</button>
		<div/>
        
    </body>
</html>