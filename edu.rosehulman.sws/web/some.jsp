<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            body {
        background-color: #d0e4fe;
        font-size: 150%;
        }
        h1 {
            color: orange;
            text-align: center;
        }
        #divider {
            height: 50px;
            background: #d0e4fe
}
        </style>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#getButton", function() {
<<<<<<< HEAD
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
=======
            	$.ajax(
    		    {
                   type: 'GET',
                   url:"http://localhost:8080/BasketballTeamPlugin/GetServlet/",
                   success:function(resultData){
                	   $('#name_list').html(resultData).wrap('<pre />');//.split('l').join('&#10;'));
                	   }
                });
>>>>>>> 7b05f4af30847dafc942fb6dbc17a0056c1b572b
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
        <h1> The Best Website Since Sliced Bread</h1>
        <p> List Teams </p>
        <button id="getButton">List Teams</button>
        <p id=name_list></p>
    	<div id="divider"></div>
    	<p> Add Team </p>
        <input type="text" name="team_name_id" id ="team_name_id" placeholder="New team name">
        <button id="addATeam">Add Team</button>
        <div id="divider"></div>
        <p> Update Players</p>
        <input type="text" name="team_name_u" id ="team_name_u" placeholder="Team name">
        <input type="text" name="num_members" id ="num_members" placeholder="Number of players">
        <button id="putButton">Update Number of Players</button>
        <div id="divider"></div>
        <p> Delete Team </p>
        <input type="text" name="team_name_d" id ="team_name_d" placeholder="Team name">
        <button id="deleteButton">Delete Team</button>
        <div id="divider"></div>
    	<button id="putNewButton">Ajax PUT to newFile.txt</button>
        
    </body>
</html>