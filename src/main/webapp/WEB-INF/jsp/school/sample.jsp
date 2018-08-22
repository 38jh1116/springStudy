<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <table>
        <tr>
            <th>이름</th>
            <th>학번</th>
        </tr>

        <c:if test="${not empty result}">
            <c:forEach items="${result}" var="user">
                <tr>
                    <td>${user.name}</td>
                    <td>${user.id}</td>
                </tr>
            </c:forEach>
        </c:if>

        <c:if test="${empty result}">
            <tr><td colspan="2">데이터가 없습니다.</td></tr>
        </c:if>

    </table>
</div>