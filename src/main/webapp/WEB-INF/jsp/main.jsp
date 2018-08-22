<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <title>Main Page</title>
</head>
<body>

<c:if test="${user ne null}">
    로그인 ID :  ${user.id}<br/>
</c:if>
</body>
</html>