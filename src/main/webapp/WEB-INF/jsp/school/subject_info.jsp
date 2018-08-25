<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<c:if test="${not empty subject_list}">
    <c:forEach items="${subject_list}" var="subject">
        <tr>
            <td>${subject.subjectNum}</td>
            <td><input type="text" class = "form-control subject-name" value="${subject.name}" disabled></td>
            <td><input type="text" class = "form-control subject-credit" value=${subject.credit} disabled></td>
            <td>
                <button class="btn btn-outline-primary modify-subject-info">수정</button>
                <button class="btn btn-outline-danger delete-subject-info" >삭제</button>
            </td>
        </tr>
    </c:forEach>
</c:if>
<c:if test="${empty subject_list}">
    <tr>
        <td colspan="6" style="text-align:center">조회된 데이터가 없습니다.</td>
    </tr>
</c:if>
<tr id="input-new-info" style="border-top: 2px solid darkgrey;">
    <td>New</td>
    <td><input id= "name" class = "form-control" type = "text" placeholder="Name" required></td>
    <td><input id= "credit" class = "form-control subject-credit" type = "number" min = "1" max="12" width="30px" placeholder="Credit" required></td>
    <td><button id="save-subject-info" class ="btn btn-outline-success btn-block">추가하기</button></td>
</tr>
