<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Management Program</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type = "text/javascript" src = "/js/jquery-3.3.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/css/main.css" type="text/css">
</head>
<body style="overflow: scroll">
<div class="list-container" >
    <h1 class = "menu-title">STUDENT INFORMATION</h1>
    <div id="option-menu" >
        <div id="sort-by">
            <label class="radio-inline"><input type="radio" name="sort-opt" value="student_num" checked> 학번순</label>
            <label class="radio-inline"><input type="radio" name="sort-opt" value="name"> 이름순</label>
        </div>
        <div id="search-by" class="dropdown">
            <select>
                <option value="all" selected>검색조건</option>
                <option value="student_num">학번</option>
                <option value="name">이름</option>
            </select>
        </div>
        <div id="search-bar" class="form-inline" >
            <input id="search-target" type="text" class="form-control" placeholder="Search" name="search" >
            <button id="search-btn" class="btn btn-default btn-icon">조회</button>
        </div>
    </div>
    <table class="table" id="info-table" name="info-table">
        <thead>
        <tr>
            <th>학번</th>
            <th>이름</th>
            <th>주민번호</th>
            <th>휴대폰번호</th>
            <th>이메일</th>
            <th> </th>
        </tr>
        </thead>
        <tbody id="student-info">
        </tbody>
    </table>
</div>
</body>
<script>

    var current_sort_option = "student_num";
    var current_search_option = "all";
    var current_search_target = "";

    function searchStudentInfo() {

        var sort_by = $("input:radio[name=sort-opt]:checked").val();
        var search_by = $("#search-by option:selected").val();
        var search_target = $("#search-target").val();

        search_by = (search_target === "") ? "all" : search_by;

        var search_condition = {
            "sortOption" : sort_by,
            "searchOption" : search_by,
            "searchTarget" : search_target
        };

        $.ajax({
            method: "GET",
            url:  "/school/student/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#student-info").html(result);
                $("#save-student-info").on("click", saveNewStudentInfo);
                $(".modify-student-info").on("click",modifyStudentInfo);
                $(".delete-student-info").on("click", removeStudentInfo);
                current_sort_option = sort_by;
                current_search_option = search_by;
                current_search_target = search_target;
            }, error: function () {

            }, complete: function () {

            }
        });
    }
    function refreshData(){

        var search_condition = {
            "sortOption" : current_sort_option,
            "searchOption" : current_search_option,
            "searchTarget" : current_search_target
        };

        $.ajax({
            method: "GET",
            url:  "/school/student/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#student-info").html(result);
                $("#save-student-info").on("click", saveNewStudentInfo);
                $(".modify-student-info").on("click",modifyStudentInfo);
                $(".delete-student-info").on("click", removeStudentInfo);
            }, error: function () {

            }, complete: function () {

            }
        });
    }

    function saveNewStudentInfo(){
        var name = $("#name").val();
        var RRN = $("#RRN").val();
        var phoneNum = $("#phone_number").val();
        var email = $("#email").val();

        var new_student = {
            "name" : name,
            "RRN" : RRN,
            "phoneNum" : phoneNum,
            "email" : email
        };
        $.ajax({
            method:"POST",
            data: new_student,
            url:"/school/student/save_student",
            success:function(result){
                alert(result.msg);
                if(result.result === "success") {
                    refreshData();
                }
            }, error: function() {
            }, complete: function() {
            }
        });
    }

    function modifyStudentInfo(event) {

        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

         if(clicked_btn.text() === "수정"){
             target_tds.children().prop("disabled",false);
             clicked_btn.text("저장");
         }else{
             var target_student_num = target_tds.first().text();
             var target_student_name = target_tds.children('.student-name').val();
             var target_student_RRN = target_tds.children('.student-RRN').val();
             var target_student_phone = target_tds.children('.student-phone').val();
             var target_student_email = target_tds.children('.student-email').val();

             var update_student_info = {
                 "studentNum" : target_student_num,
                 "name" : target_student_name,
                 "RRN" : target_student_RRN,
                 "phoneNum" : target_student_phone,
                 "email" : target_student_email
             };

             $.ajax({
                 method:"POST",
                 data: update_student_info,
                 url:"/school/student/modify_student",
                 dataType : "json",
                 success:function(result){
                     alert(result.msg);
                     if(result.result === "success"){
                        refreshData();
                     }
                 }, error: function() {
                 }, complete: function() {
                 }
             });



         }
    }

    function removeStudentInfo(event) {
        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

        if(confirm("삭제 하시겠습니까?")) {
            var target_student_num = target_tds.first().text();

            $.ajax({
                method: "POST",
                data : {"studentNum" : target_student_num},
                url: "/school/student/remove_student",
                dataType: "json",
                success: function (result) {
                    alert(result.msg);
                    if(result.result === "success") {
                        refreshData();
                    }
                }, error: function () {
                }, complete: function () {
                }
            });
        }
    }
    $(document).ready(function(){
        refreshData();
        $("#search-btn").on("click",searchStudentInfo);
    });
</script>
</html>
