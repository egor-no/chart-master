<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1>TOP 40!</h1>
<br/> CAHRT: ${chart.date}
<table>
    <c:forEach items="${chart.positions}" var="position">
        <tr>
            <td>${chart.id}</td>
            <td>${chart.date}</td>
            <td>${position.position}</td>
            <td>${position.lastWeek}</td>
            <td>${position.pk.song.name}</td>
            <td>${position.pk.song.artists}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>