<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<c:if test="${not empty professor_list}">
    <c:forEach items="${professor_list}" var="professor">
        <tr>
            <td>${professor.professorNum}</td>
            <td><input type="text" class = "form-control professor-name" value=${professor.name} disabled></td>
            <td><input type="text" class = "form-control professor-RRN" value=${professor.RRN} disabled></td>
            <td><input type="text" class = "form-control professor-phone" value=${professor.phoneNum} disabled></td>
            <td><input type="text" class = "form-control professor-email" value=${professor.email} disabled></td>
            <td><input type="text" class = "form-control professor-office-address" value="${professor.officeAddress}" disabled></td>
            <td><input type="text" class = "form-control professor-office-number" value=${professor.officeNum} disabled></td>
            <td>
                <button class="btn btn-outline-primary modify-professor-info">수정</button>
                <button class="btn btn-outline-danger delete-professor-info" >삭제</button>
            </td>
        </tr>
    </c:forEach>
</c:if>
 <c:if test="${empty professor_list}">
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
            <td><input id="office_address" class = "form-control" type = "text" placeholder="Office Address" required></td>
            <td><input id="office_number" class = "form-control" type = "text" placeholder="Office Number" required></td>
            <td><button id="save-professor-info" class ="btn btn-outline-success btn-block">추가하기</button></td>
        </tr>
