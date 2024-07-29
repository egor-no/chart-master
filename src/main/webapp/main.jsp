<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "TOP 40!" %>
</h1>
<br/>
<a href="hello-servlet">Here will be chart</a>
<table>
<c:forEach items="${songs}" var="song">
    <tr>
        <td>${song.name}</td>
        <td>${song.artists}</td>
        <td>${song.peak}</td>
        <td>${song.weeks}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>