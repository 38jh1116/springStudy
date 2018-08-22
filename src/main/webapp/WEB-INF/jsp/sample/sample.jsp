<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <meta charset="utf-8">
    <title>Get HTML</title>
    <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script>
        //<![CDATA[
        function abc() {
            var a = {
                "id": "테스트1",
                "name": "테스트2"
            };

            $.ajax({
                method: "POST",
                url: "/sample",
                contentType: "application/json",
                data: JSON.stringify(a),
                dataType:"text",
                success:function(result) {
                    console.log(result)
                }, error: function() {

                }, complete: function() {

                }
            });
        }
        // ]]>
    </script>
</head>
<body>
<button onclick="abc()">버튼!</button>
</body>
</html>

