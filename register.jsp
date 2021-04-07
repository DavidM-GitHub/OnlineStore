<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>REST Example</title>
<script>

    function myFunction()
    {
        name= document.getElementById("name").value
		email=document.getElementById("email").value
		password=document.getElementById("password").value
		location=document.getElementById("location").value
		
//<form onsubmit="myFunction()">
		//data=JSON.parse('{"customer":{"id":2,"name":name, "email":email,"password":password,"location":location}}')
data={
        "customer": {
            "email": "al@g.c",
            "id": 7,
            "location": "ratoath",
            "name": "alan",
            "password": "1234"
        }
    }

alert("Carrying out function")

$.post("http://localhost:8080/spca4/restful-services/sampleservice/register/json",
		{
        "customer": {
            "email": email,
            "id": 7,
            "location": location,
            "name": name,
            "password": password
        }
    },
	  function() { alert("Worked"); });

///also tried this
        $.ajax({
            url:         'http://localhost:8080/spca4/restful-services/sampleservice/register/json',
            method:      'POST',
            dataType:    'json',
            data:       data,
            success:        function() { alert("Worked"); },
            error:      function(error) { alert("Error"); }
        });
    }

function store () {
  // (A) VARIABLES TO PASS
  var email = document.getElementById("email").value

  // (B) SAVE TO LOCAL STORAGE
  // localStorage.setItem("KEY", "VALUE");
  localStorage.setItem("email", email);
   console.log("stored email");

  
}

    </script>
	<link href="http://localhost:8080/spca4/styles.css" rel="stylesheet" type="text/css"/>
</head>
<body >
<div class="vertical-center">
    
	 

	
	
	
	<h1>Register Here</h1></br>
<form action= "http://localhost:8080/spca4/restful-services/sampleservice/registercustomer" method="GET"> 
        <p>Name: <input style="margin-left:7.1em" id="name" type="text" name="name" /> </p>
        <p>Email: <input style="margin-left:7.1em" id="email" type="text" name="email" /> </p>
        <p>Password: <input style="margin-left:5em"id="password" type="text" name="password" /> </p></br>
		<p>Address: <input style="margin-left:5em"id="address" type="text" name="address" /> </p></br>
		<p>Payment Method: <input style="margin-left:5em"id="payment" type="text" name="payment" /> </p></br>
        <input class="button" type="submit" value="Register" onclick="store()"/>
		</form></br></br></br></br></br></br></br></br>
		
		
		
	
    </form>
	</div>
</body>
</html>