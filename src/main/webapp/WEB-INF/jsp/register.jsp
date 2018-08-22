<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <link rel="stylesheet" href="/css/account.css" type="text/css"/>
    <meta charset="UTF-8">
    <title>가입하기 • Imstargram</title>
    <script type = "text/javascript" src = "/js/jquery-3.3.1.min.js"></script>
    <script>
        function register(){

            var userAccount = $("#user_account").val();
            var userName = $("#user_name").val();
            var userId= $("#user_id").val();
            var userPw = $("#user_pw").val();

            $.ajax({
                method: "POST",
                url: "/register",
                data: {
                    "id" : userId ,
                    "password" : userPw,
                    "account" : userAccount,
                    "name" : userName
                },
                success:function(result) {
                    console.log(result);
                    if (result == "success") {
                        jQuery(location).prop("href","/login");
                    } else {
                        alert("가입에 실패했습니다.");
                    }
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
        <div id="sign_up_box" class="white_box">
            <h1 id="logo"></h1>
            <p>친구들의 사진과 동영상을 보려면 가입하세요.</p>
            <div id="sign_up_form_box" class="form_box">
                <button class="btn_active" ><img src="/images/facebook.png" style="width:15px; margin:auto 3px;"> Facebook으로 로그인</button>
                <div id="divide-line">
                    <div class="short_line"></div> <span> 또는 </span> <div class="short_line"></div>
                </div>
                    <table>
                        <tr><td><input class="input_box" type="text" name = "account" id="user_account" placeholder="휴대폰 번호 또는 이메일 주소"></td></tr>
                        <tr><td><input class="input_box" type="text" name = "name" id="user_name" placeholder="성명"></td></tr>
                        <tr><td><input class="input_box" type="text" name = "id" id="user_id" placeholder="사용자 이름"></td></tr>
                        <tr><td><input class="input_box" type="password" name = "password" id="user_pw" placeholder="비밀번호"></td></tr>
                        <tr><td><button class="btn_active" type="button" name="submit" id="submit" onclick="register()">가입</button></td></tr>
                    </table>
            </div>
            <p >가입하면 Imstargram의 <a href="#">약관</a><a href="#">, 데이터 정책</a> 및 <a href="#">쿠키 정책</a>에 동의하게 됩니다</p>
        </div>
        <div id="account_check_box" class="white_box">
            <p>계정이 있으신가요 ? <a href="/login">로그인</a></p>
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