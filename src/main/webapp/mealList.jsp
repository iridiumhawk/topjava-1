<%--
  Created by IntelliJ IDEA.
  User: hawk
  Date: 12.09.2016
  Time: 2:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<title>Meal list</title>
<style>
    p {font-size: 24px}
    div {font-size: 24px; text-align: center}
    .normal {
        color: green;

    }

    .exceeded {
        color: red;
    }
</style>

</head>
<body>

<h2><a href="index.html">Homee</a></h2>
<h2>Meal list</h2>

<table>
    <tr>
        <th width="20%">Дата</th><th width="40%">Еда</th><th width="20%">Калории</th>
    </tr>
    <c:forEach var="meal" items="${meallist}">
        <c:set var="st" value="normal"></c:set>
        <c:if test="${meal.exceed}" >
           <c:set var="st" value="exceeded"></c:set>
        </c:if>
        <tr>
            <td class=${st}>
                <div>${meal.getDate()} : ${meal.getTime()}</div>
            </td>
            <td class=${st}>
                <div><c:out value="${meal.description}"/></div>
            </td>
            <td class=${st}>
                <div><c:out value="${meal.calories}"/></div>

            </td>

        </tr>

    </c:forEach>

</table>


</body>
</html>
