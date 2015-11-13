<!DOCTYPE html>
<html lang="en">
    <head>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#getButton", function() {
				$.getJSON("http://localhost:8080/BasketballTeamPlugin/ExtraServlet/", function(data) {
					var json = $.parseJSON(data);
					$.each(json, function(i, item) {
						//$("#teamdiv").append(data[i].concat("<br>"));
						$("#teamdiv").append("boop ");
					});
				});
                //$.get("http://localhost:8080/BasketballTeamPlugin/ExtraServlet/", function(responseText) {
                  //  $("#teamdiv").text(responseText);
                //});
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
             	 url:"http://localhost:8080/BasketballTeamPlugin/PutServlet/",
             	 data: $("#team_name_u").serialize()+" "+$("#num_members").serialize(),
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
             	 url:"http://localhost:8080/BasketballTeamPlugin/DeleteServlet/",
             	 data: $("#team_name_d").serialize(),
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
        <p>Team Name:</p>
        <input type="text" name="team_name_u" id ="team_name_u">
        <p>Number of Members:</p>
        <input type="text" name="num_members" id ="num_members">
        <br></br>
        <button id="putButton">Update Number of Players</button>
    	<br></br>
        <button id="putNewButton">Ajax PUT to newFile.txt</button>
    	<br></br>
    	<input type="text" name="team_name_dd" id ="team_name_dd">
    	<button id="deleteButton">Ajax DELETE</button>
		<div/>
        
    </body>
</html>