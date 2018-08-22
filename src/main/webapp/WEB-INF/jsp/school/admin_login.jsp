<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Management Program</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script type = "text/javascript" src = "/js/jquery-3.3.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <script>
        function login(){
            var userId= $("#user_id").val();
            var userPw = $("#user_pw").val();

            $.ajax({
                method: "POST",
                url: "/school/login",
                data: {
                    "id" : userId ,
                    "password" : userPw
                },
                success:function(result) {
                    jQuery("#userArea").html(result);
                    if(result === "login_success") {
                        jQuery(location).prop("href","/school/app_main?id=" + userId );
                    }else{
                        alert("아이디 또는 비밀번호를 다시 확인하세요.");
                    }
                }, error: function() {

                }, complete: function() {
                }
            });
        }
    </script>
</head>
<body>
<div class="container" style="width:400px; margin-top:250px;">
    <h2 style="text-align: center;margin-bottom:50px;">MANAGEMENT SYSTEM</h2>
        <div class="input-group form-group" >
            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                <input type="text" class="form-control" id="user_id" placeholder="Enter ID" name="user_id">
        </div>
        <div class="input-group form-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
            <input type="password" class="form-control" id="user_pw" placeholder="Enter PASSWORD" name="user_pw">
        </div>
        <div class="form-group form-check">
        </div>
        <button type="submit" class="btn btn-primary btn-block" onclick="login()">Login</button>
</div>
</body>
</html>