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
<body style="overflow: scroll;padding:0 100px;" >
<div class="list-container" >
    <h1 class = "menu-title">COURSE INFORMATION</h1>
    <div id="option-menu" >
        <div id="sort-by">
            <label class="radio-inline"><input type="radio" name="sort-opt" value="subject_num" checked>학수번호순</label>
            <label class="radio-inline"><input type="radio" name="sort-opt" value="subject_name" checked>강좌이름순</label>
        </div>
        <div id="search-by" class="dropdown">
            <select>
                <option value="all" selected="selected">검색조건</option>
                <option value="subject_num" >학수번호</option>
                <option value="subject_name">강좌명</option>
                <option value="professor_name">교수명</option>
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
            <th>강좌번호</th>
            <th>학수번호</th>
            <th>과목명</th>
            <th>분반</th>
            <th>담당교수</th>
            <th>담당교수직번</th>
            <th>학기</th>
            <th>시간</th>
            <th>학점</th>
            <th></th>
            <th> </th>
        </tr>
        </thead>
        <tbody id="course-info">
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
            url:  "/school/course/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#course-info").html(result);
                $("#save-course-info").on("click", saveNewCourseInfo);
                $(".modify-course-info").on("click",modifyCourseInfo);
                $(".delete-course-info").on("click", removeCourseInfo);
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
            url:  "/school/course/search",
            data:search_condition,
            contentType: "application/json",
            success: function (result) {
                $("#course-info").html(result);
                $("#save-course-info").on("click", saveNewCourseInfo);
                $(".modify-course-info").on("click",modifyCourseInfo);
                $(".delete-course-info").on("click", removeCourseInfo);
            }, error: function () {

            }, complete: function () {

            }
        });
    }

    function saveNewCourseInfo(){
        var subject_num = $("#subject-info option:selected").val();
        var class_num = $("#class-number").val();
        var professor_num = $("#professor-info option:selected").val();
        var semester = $("#semester").text();
        var time = $("#time").val();

        var new_subject = {
            "subjectNum" : subject_num,
            "classNum" : class_num,
            "professorNum" : professor_num,
            "semester" : semester,
            "time" : time
        };

        $.ajax({
            method:"POST",
            data: new_subject,
            url:"/school/course/save_course",
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
    function modifyCourseInfo(event) {

        var clicked_btn = $(event.target);
        var target_tr = clicked_btn.parent().parent();
        var target_tds = clicked_btn.parent().siblings();

        var hidden_subject_list = target_tr.find(".hidden-subject-list");
        var hidden_professor_list = target_tr.find(".hidden-professor-list");
        var visible_info = target_tr.find(".visible-info");

        var current_subject = target_tr.find('.course-subject-number').text();
        var current_professor = target_tr.find('.course-professor-number').text();

        if(clicked_btn.text() === "수정"){
            hidden_subject_list.prop("hidden",false);
            hidden_subject_list.find('select').val(current_subject);

            hidden_professor_list.prop("hidden",false);
            hidden_professor_list.find('select').val(current_professor);

            visible_info.prop("hidden",true);
            target_tds.children().prop("disabled",false);
            clicked_btn.text("저장");
        }else{
            var target_course_num = target_tr.find(".course-number").text();
            var target_subject_num = hidden_subject_list.find("option:selected").val();
            var target_class_num = target_tds.children(".course-class-number").val();
            var target_professor_num = hidden_professor_list.find("option:selected").val();
            var target_semester = target_tr.find(".course-semester").text();
            var target_time = target_tr.find(".course-time").val();

            var update_subject_info = {
                "courseNum" : target_course_num,
                "subjectNum" : target_subject_num,
                "classNum" : target_class_num,
                "professorNum" : target_professor_num,
                "semester" : target_semester,
                "time" : target_time
            };

            $.ajax({
                method:"POST",
                data: update_subject_info,
                url:"/school/course/modify_course",
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

    function removeCourseInfo(event) {
        var clicked_btn = $(event.target);
        var target_tds = clicked_btn.parent().siblings();

        if(confirm("삭제 하시겠습니까?")) {
            var target_course_num = target_tds.first().text();

            $.ajax({
                method: "POST",
                data : {"courseNum" : target_course_num},
                url: "/school/course/remove_course",
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
