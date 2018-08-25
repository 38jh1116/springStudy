<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Management Program</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <script type = "text/javascript" src = "/js/jquery-3.3.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</head>
<body>
<p style="text-align: end; margin:20px">
<c:if test="${login_admin ne null}">
    로그인 ID :  ${login_admin.id}<br/>
</c:if>
</p>
<div class="container" style="width:500px; margin-top:200px;">
    <button type="button" class="btn btn-outline-primary btn-block" onclick="location='/school/student'">Student Menu</button>
    <button type="button" class="btn btn-outline-secondary btn-block" onclick="location='/school/professor'">Professor Menu</button>
    <button type="button" class="btn btn-outline-success btn-block" onclick="location='/school/subject'">Subject Menu</button>
    <button type="button" class="btn btn-outline-info btn-block" onclick="location='/school/course'">Course Menu</button>
    <button type="button" class="btn btn-outline-warning btn-block">Registration Menu</button>
    <button type="button" class="btn btn-outline-danger btn-block">Grade Menu</button>
</div>
</body>
</html>