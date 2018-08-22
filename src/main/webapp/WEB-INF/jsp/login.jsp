<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <link rel="stylesheet" href="/css/account.css" type="text/css"/>
    <meta charset="UTF-8">
    <title>로그인 • Imstargram</title>
    <script type = "text/javascript" src = "/js/jquery-3.3.1.min.js"></script>
    <script>
        function login(){
            var userId= $("#user_id").val();
            var userPw = $("#user_pw").val();

            $.ajax({
                method: "POST",
                url: "/login",
                data: {
                    "id" : userId ,
                    "password" : userPw
                },
                success:function(result) {
                    jQuery(location).prop("href","/main?id=" + userId );
                }, error: function() {

                }, complete: function() {

                }
            });
        }
    </script>
</head>
<body>
    <section id="main_section">
        <div id="main_box">
            <div id="login_box" class="white_box">
                <h1 id="logo"></h1>
                <div id="login_form_box" class="form_box">
                    <table>
                        <tr><td><input class="input_box" type="text" name = "id" id="user_id" placeholder="전화번호, 사용자 이름 또는 이메일"></td></tr>
                        <tr><td><input class="input_box" type="password" name = "password" id="user_pw" placeholder="비밀번호"></td></tr>
                        <tr><td><button class="btn_non_active" type="button" name="submit" id="submit" onclick="login()" >로그인</button></td></tr>
                    </table>
                </div>
                <a href="#" style="font-size : 0.8em">비밀번호를 잊으셨나요?</a>
            </div>
            <div id="account_check_box" class="white_box">
                <p>계정이 없으신가요? <a href="/register">가입하기</a></p>
            </div>
            <div id="download_box">
                <p>앱을 다운로드 하세요.</p>
                <a href="#"><img src="/images/download_apple.png"></a>
                <a href="#"><img src="/images/download_google.png"></a>
            </div>
        </div>
    </section>

</body>
</html>