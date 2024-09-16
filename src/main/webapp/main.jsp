<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>JSP - Hello World</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type = "text/javascript" >
        $(document).ready(function() {
            $('#chart-table tbody tr').each(function () {
                var lw = $(this).find('[name="lw"]').text();
                var pos = $(this).find('[name="pos"]').text();
                var woc = $(this).find('[name="woc"]').text();
                var mov;
                if (lw != '' && lw != '0') {
                    mov = parseInt(lw) - parseInt(pos);
                    if (mov == 0) {
                        mov = '=';
                    } else if (mov > 0) {
                        mov = '+' + mov;
                    }
                } else {
                    if (parseInt(woc) > 1) {
                        mov = 're';
                        $(this).find('[name="mov"]').css('color', 'orange');
                    } else {
                        mov = 'new';
                        $(this).find('[name="mov"]').css('color', 'red');
                    }
                }
                $(this).find('[name="mov"]').text(mov);
            });
        });
    </script>
</head>
<body>
<h1>TOP 40!</h1>
<form method="GET" action="/">
    <label for="chartSearch">Поиск по номеру чарта:</label>
    <input name="chart" id="chartSearch" type="text" /> <input type="submit" value="Искать" />
</form>
<table id="chart-table">
    <thead>
        <tr>
<%--            <th colspan="8"><b>CHART: <fmt:formatDate pattern="dd.MM.yyyy" value="${chart.date}" /> </b></th>--%>
            <th style="text-align: left" colspan="8"><b>CHART: ${chart.date} </b></th>
        </tr>
        <tr>
            <th style="text-align: center">Mov</th>
            <th style="text-align: center">Pos</th>
            <th style="display:none;">LW</th>
            <th style="text-align: center">Artists</th>
            <th style="width:30px;"></th>
            <th style="text-align: center">Title</th>
            <th style="text-align: center">Peak</th>
            <th style="text-align: center">WoC</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${chart.positions}" var="position" varStatus="status">
            <tr>
                <td name="mov" style="text-align: center"></td>
                <td name="pos" style="text-align: center">${position.position}</td>
                <td name="lw" style="display:none;">${position.lastWeek}</td>
                <td style="text-align: right">${position.pk.song.artists}</td>
                <td style="text-align: center"> - </td>
                <td>${position.pk.song.name}</td>
                <td name="peak" style="text-align: center">${peaks[status.index]}</td>
                <td name="woc" style="text-align: center">${woc[status.index]}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>