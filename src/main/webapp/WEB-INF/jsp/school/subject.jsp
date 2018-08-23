<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Management Program</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href=".css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script type = "text/javascript" src = "/js/jquery-3.3.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/css/main.css" type="text/css">
</head>
<body style="overflow: scroll">
<div class="list-container" >
    <h1 class = "menu-title">SUBJECT INFORMATION</h1>
    <div id="option-menu" >
        <div id="sort-by">
            <label class="radio-inline"><input type="radio" name="sort-opt" value="subject_num" checked> 학수번호순</label>
            <label class="radio-inline"><input type="radio" name="sort-opt" value="name"> 이름순</label>
        </div>
        <div id="search-by" class="dropdown">
            <select>
                <option value="subject_num" selected="selected">학수번호</option>
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
            <th>학수번호</th>
            <th>과목명</th>
            <th>학점</th>
            <th> </th>
        </tr>
        </thead>
        <tbody id="subject-info">
        </tbody>
    </table>
</div>
</body>
<script>

    var current_sort_option = "subject_num";
    var current_search_option = "all";
    var current_search_target = "";

    function searchSubjectInfo() {

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
            url:  "/school/subject/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#subject-info").html(result);
                $("#save-subject-info").on("click", saveNewSubjectInfo);
                $(".modify-subject-info").on("click",modifySubjectInfo);
                $(".delete-subject-info").on("click", removeSubjectInfo);
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
            url:  "/school/subject/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#subject-info").html(result);
                $("#save-subject-info").on("click", saveNewSubjectInfo);
                $(".modify-subject-info").on("click",modifySubjectInfo);
                $(".delete-subject-info").on("click", removeSubjectInfo);
            }, error: function () {

            }, complete: function () {

            }
        });
    }

    function saveNewSubjectInfo(){
        var name = $("#name").val();
        var credit = $("#credit").val();

        var new_subject = {
            "name" : name,
            "credit" : credit
        };
        $.ajax({
            method:"POST",
            data: new_subject,
            url:"/school/subject/save_subject",
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

    function modifySubjectInfo(event) {

        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

        if(clicked_btn.text() === "수정"){
            target_tds.children().prop("disabled",false);
            clicked_btn.text("저장");
        }else{
            var target_subject_num = target_tds.first().text();
            var target_subject_name = target_tds.children('.subject-name').val();
            var target_subject_credit = target_tds.children('.subject-credit').val();

            var update_subject_info = {
                "subjectNum" : target_subject_num,
                "name" : target_subject_name,
                "credit" : target_subject_credit,
            };

            $.ajax({
                method:"POST",
                data: update_subject_info,
                url:"/school/subject/modify_subject",
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

    function removeSubjectInfo(event) {
        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

        if(confirm("삭제 하시겠습니까?")) {
            var target_subject_num = target_tds.first().text();

            $.ajax({
                method: "POST",
                data : {"subjectNum" : target_subject_num},
                url: "/school/subject/remove_subject",
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
        $("#search-btn").on("click",searchSubjectInfo);
    });
</script>
</html>
