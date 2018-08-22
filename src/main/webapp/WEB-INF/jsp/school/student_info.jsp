<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<c:if test="${not empty student_list}">
    <c:forEach items="${student_list}" var="student">
        <tr>
            <td>${student.studentNum}</td>
            <td><input type="text" class = "form-control student-name" value=${student.name} disabled></td>
            <td><input type="text" class = "form-control student-RRN" value=${student.RRN} disabled></td>
            <td><input type="text" class = "form-control student-phone" value=${student.phoneNum} disabled></td>
            <td><input type="text" class = "form-control student-email" value=${student.email} disabled></td>
            <td>
                <button class="btn btn-outline-primary modify-student-info">수정</button>
                <button class="btn btn-outline-danger delete-student-info" >삭제</button>
            </td>
        </tr>
    </c:forEach>
</c:if>
 <c:if test="${empty student_list}">
        <tr>
            <td colspan="6" style="text-align:center">조회된 데이터가 없습니다.</td>
        </tr>
 </c:if>
        <tr id="input-new-info" style="border-top: 2px solid darkgrey;">
            <td>New</td>
            <td><input id= "name" class = "form-control" type = "text" placeholder="Name" required></td>
            <td><input id= "RRN" class = "form-control" type = "text" placeholder="RRN" required></td>
            <td><input id= "phone_number" class = "form-control" type = "text" placeholder="Phone Number" required></td>
            <td><input id="email" class = "form-control" type = "email" placeholder="Email" required></td>
            <td><button id="save-student-info" class ="btn btn-outline-success btn-block">추가하기</button></td>
        </tr>
