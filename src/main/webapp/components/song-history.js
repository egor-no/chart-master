$(document).ready(function() {
    $('[name="history-link"]').on('click', function () {
        event.preventDefault();
        var songDiv =  $(this).closest('[name="song"]')
        if (songDiv.find('[name="position"]').length) {
            if (songDiv.find('[name="song-history"]').is(":visible")) {
                songDiv.find('[name="song-history"]').slideUp(500);
                songDiv.find('[name="mov-info"]').removeClass('history-mov')
                songDiv.removeClass('history-open');
            } else {
                songDiv.find('[name="song-history"]').slideDown(500);
                songDiv.find('[name="mov-info"]').addClass('history-mov')
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
                    songDiv.find('[name="mov-info"]').addClass('history-mov')
                    songDiv.addClass('history-open');
                });
            });
        }
    });
});
