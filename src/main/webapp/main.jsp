<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>TOP40 - Music Chart</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <style><%@include file="/css/style.css"%></style>
    <script type = "text/javascript" >
        $(document).ready(function() {
            $('[name="history-link"]').on('click', function () {
                event.preventDefault();
                var songDiv =  $(this).closest('[name="song"]')
                if (songDiv.find('[name="position"]').length) {
                    if (songDiv.find('[name="song-history"]').is(":visible")) {
                        songDiv.find('[name="song-history"]').slideUp(500);
                        songDiv.removeClass('history-open');
                    } else {
                        songDiv.find('[name="song-history"]').slideDown(500);
                        songDiv.addClass('history-open');
                    }
                } else {
                    var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();
                    var idChart = $(document).find('[name="chart-number"]').text();
                    $.get("songhistory?idSong=" + idSong + "&chartNumber=" + idChart, function(songhistory) {
                        var peak = songhistory.peak;
                        var currentChart = songhistory.currentChart;
                        $.each(songhistory.chartRuns, function (index, chartRun) {
                            var chartRunDiv = $(document).find('[name="chart-run-template"]').clone();
                            chartRunDiv.attr('name', 'chart-run');

                            if (chartRun.firstChart.date != chartRun.lastChart.date) {
                                chartRunDiv.find('[name="chart-run-header"]').html(chartRun.firstChart.date + " - " + chartRun.lastChart.date);
                                chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunTwo');
                            } else {
                                chartRunDiv.find('[name="chart-run-header"]').html(chartRun.firstChart.date);
                                chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunOne');
                            }

                            var chartId = chartRun.firstChart.id;
                            $.each(chartRun.positions, function(i, position) {

                                chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').html(position);

                                if (position == peak) {
                                    chartRunDiv.find('[name="position"]:last').addClass('peak');
                                } else {
                                    chartRunDiv.find('[name="position"]:last').removeClass('peak');
                                }

                                if (chartId == currentChart) {
                                    chartRunDiv.find('[name="position"]:last').addClass('current');
                                } else {
                                    chartRunDiv.find('[name="position"]:last').removeClass('current');
                                }

                                chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('href', '/?chartNumber=' + chartId);
                                chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('title', 'Посмотреть чарт №' + chartId);
                                var positionDiv = chartRunDiv.find('[name="position"]:last').clone();
                                chartRunDiv.find('[name="positions"]').append(positionDiv);
                                chartId++;
                            });
                            chartRunDiv.find('[name="position"]:last').remove();

                            songDiv.find('[name="song-history"]').append(chartRunDiv);
                            chartRunDiv.css('display', 'block');
                            songDiv.find('[name="song-history"]').slideDown(500);
                            songDiv.addClass('history-open');
                        });
                    });
                }
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>TOP40!</h1>
    <div class="nav">
        <div name="menu" style="margin-bottom:5px;">
            <b>Главная</b>
            |
            <a href="/chartadd">Добавить чарт</a>
            |
            <a href="/artists">Артисты</a>
            |
            <a href="/songs">Песни</a>
        </div>

        <form method="GET" action="/">
            <label for="chartSearch">Поиск по дате чарта:</label>
            <input name="date" id="chartSearch" type="date" /><input type="submit" value="Искать" />
        </form>
    </div>

    <%@include file="components/chart-table.jsp"%>
</div>

</body>
</html>