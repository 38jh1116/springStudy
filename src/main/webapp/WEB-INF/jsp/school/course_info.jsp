<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<c:if test="${not empty course_list}">
    <c:forEach items="${course_list}" var="course">
        <tr>
            <td class="course-number">${course.courseNum}</td>
            <td colspan="2" class="hidden-subject-list" hidden>
                <select class="form-control" >
                    <option value="" selected="selected">강의 선택</option>
                    <c:forEach items="${subject_list}" var="subject">
                        <option value="${subject.subjectNum}" >${subject.subjectNum} ${subject.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td class="visible-info course-subject-number">${course.subject.subjectNum}</td>
            <td class="visible-info course-subject-name">${course.subject.name}</td>
            <td><input type="text" class = "form-control course-class-number" value="${course.classNum}" disabled></td>

            <td colspan="2" class="hidden-professor-list" hidden>
                <select class="form-control">
                    <option value="" selected="selected">담당 교수 선택</option>
                    <c:forEach items="${professor_list}" var="professor">
                        <option value="${professor.professorNum}" >${professor.professorNum} ${professor.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td class="visible-info course-professor-name" >${course.professor.name}</td>
            <td class="visible-info course-professor-number">${course.professor.professorNum}</td>
            <td class="course-semester">${course.semester}</td>
            <td><input type="text" class = "form-control course-time" value="${course.time}" disabled></td>
            <td class="course-credit">${course.subject.credit}</td>
            <td>
                <button class="btn btn-outline-primary modify-course-info">수정</button>
                <button class="btn btn-outline-danger delete-course-info" >삭제</button>
            </td>
        </tr>
    </c:forEach>
</c:if>
<c:if test="${empty course_list}">
    <tr>
        <td colspan="10" style="text-align:center">조회된 데이터가 없습니다.</td>
    </tr>
</c:if>
<c:if test="${not empty subject_list and not empty professor_list}">
    <tr id="input-new-info" style="border-top: 2px solid darkgrey;">
        <td>New</td>
        <td colspan="2" id="subject-info">
            <select class="form-control" >
                <option value="" selected="selected">강의 선택</option>
                <c:forEach items="${subject_list}" var="subject">
                    <option value="${subject.subjectNum}" >${subject.subjectNum} ${subject.name}</option>
                </c:forEach>
            </select>
        </td>
        <td><input id="class-number" class = "form-control course-class-number" type="number" placeholder="Class" required ></td>
        <td colspan="2" id="professor-info">
            <select class="form-control">
                <option value="" selected="selected">담당 교수 선택</option>
                <c:forEach items="${professor_list}" var="professor">
                    <option value="${professor.professorNum}" > ${professor.name} ${professor.professorNum}</option>
                </c:forEach>
            </select>
        </td>
        <td id="semester">2018-02</td>
        <td><input id="time" type="text" class="form-control" placeholder="Time" required></td>
        <td colspan="3"><button id="save-course-info" class ="btn btn-outline-success btn-block">추가하기</button></td>
    </tr>
</c:if>
<c:if test="${empty subject_list or empty professor_list}">
    <tr>
        <td colspan="10" style="text-align:center">추가 가능한 과목 혹은 담당교수가 없습니다.<a href="/school/professor">교수 관리</a>&nbsp;&nbsp;<a href="/school/subject">과목 관리</a></td>
    </tr>
</c:if>


