<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            body {
        font-size: 150%;
        background: url(bread.jpg);
        background-size: 1920px 1080px;
        margin-left: 6cm;
        }
        h1 {
            color: black;
            text-align: center;
        }
        #divider {
            height: 50px;
            background: #00000000
}
        </style>
        <title>SO question 4112686</title>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script>
            $(document).on("click", "#getButton", function() {
            	$.ajax(
    		    {
                   type: 'GET',
                   url:"http://localhost:8080/BasketballTeamPlugin/GetServlet/",
                   success:function(resultData){
                	   $('#name_list').html(resultData).wrap('<pre />');
                	   }
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
        <h1> The Best Website Since Sliced Bread!</h1>
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
    </body>
</html>