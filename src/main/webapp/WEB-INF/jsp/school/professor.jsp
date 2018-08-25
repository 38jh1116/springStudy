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
    <h1 class = "menu-title">PROFESSOR INFORMATION</h1>
    <div id="option-menu" >
        <div id="sort-by">
            <label class="radio-inline"><input type="radio" name="sort-opt" value="professor_num" checked> 직번순</label>
            <label class="radio-inline"><input type="radio" name="sort-opt" value="name"> 이름순</label>
        </div>
        <div id="search-by" class="dropdown">
            <select>
                <option value="all" selected="selected">검색조건</option>
                <option value="professor_num">직번</option>
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
            <th>직번</th>
            <th>이름</th>
            <th>주민번호</th>
            <th>휴대폰번호</th>
            <th>이메일</th>
            <th>연구실위치</th>
            <th>연구실번호</th>
            <th> </th>
        </tr>
        </thead>
        <tbody id="professor-info">
        </tbody>
    </table>
</div>
</body>
<script>

    var current_sort_option = "professor_num";
    var current_search_option = "all";
    var current_search_target = "";

    function searchProfessorInfo() {
        alert("조회");

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
            url:  "/school/professor/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#professor-info").html(result);
                $("#save-professor-info").on("click", saveNewProfessorInfo);
                $(".modify-professor-info").on("click",modifyProfessorInfo);
                $(".delete-professor-info").on("click", removeProfessorInfo);
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
            url:  "/school/professor/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#professor-info").html(result);
                $("#save-professor-info").on("click", saveNewProfessorInfo);
                $(".modify-professor-info").on("click",modifyProfessorInfo);
                $(".delete-professor-info").on("click", removeProfessorInfo);
            }, error: function () {

            }, complete: function () {

            }
        });
    }

    function saveNewProfessorInfo(){
        var name = $("#name").val();
        var RRN = $("#RRN").val();
        var phoneNum = $("#phone_number").val();
        var email = $("#email").val();
        var officeAddress = $("#office_address").val();
        var officeNum = $("#office_number").val();

        var new_professor = {
            "name" : name,
            "RRN" : RRN,
            "phoneNum" : phoneNum,
            "email" : email,
            "officeAddress" : officeAddress,
            "officeNum" : officeNum
        };
        $.ajax({
            method:"POST",
            data: new_professor,
            url:"/school/professor/save_professor",
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

    function modifyProfessorInfo(event) {
        alert("수정");

        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

        if(clicked_btn.text() === "수정"){
            target_tds.children().prop("disabled",false);
            clicked_btn.text("저장");
        }else{
            var target_professor_num = target_tds.first().text();
            var target_professor_name = target_tds.children('.professor-name').val();
            var target_professor_RRN = target_tds.children('.professor-RRN').val();
            var target_professor_phone = target_tds.children('.professor-phone').val();
            var target_professor_email = target_tds.children('.professor-email').val();
            var target_professor_office_address = target_tds.children('.professor-office-address').val();
            var target_professor_office_number = target_tds.children('.professor-office-number').val();

            var update_professor_info = {
                "professorNum" : target_professor_num,
                "name" : target_professor_name,
                "RRN" : target_professor_RRN,
                "phoneNum" : target_professor_phone,
                "email" : target_professor_email,
                "officeAddress" : target_professor_office_address,
                "officeNum" : target_professor_office_number
            };

            $.ajax({
                method:"POST",
                data: update_professor_info,
                url:"/school/professor/modify_professor",
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

    function removeProfessorInfo(event) {
        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

        if(confirm("삭제 하시겠습니까?")) {
            var target_professor_num = target_tds.first().text();

            $.ajax({
                method: "POST",
                data : {"professorNum" : target_professor_num},
                url: "/school/professor/remove_professor",
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
        $("#search-btn").on("click",searchProfessorInfo);
    });
</script>
</html>
