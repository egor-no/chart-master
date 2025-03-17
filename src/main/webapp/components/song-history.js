
$(document).ready(function() {
    $('[name="edit-link"]').on('click', function () {
        var idSong = $(this).closest('[name="song"]').find('[name="song-id"]').val();
        $(this).attr("href", "/songedit?id=" + idSong + "&search=" + '${search}');
        event.stopPropagation();
    });

    $('[name="song"]').on('click', function() {
        event.preventDefault();
        var no1 = 0,
            top10 = 0,
            top20 = 0;

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
                    var chartRunSize = chartRun.positions.length;
                    var chartRunDiv = $(document).find('[name="chart-run-template"]').clone();
                    chartRunDiv.attr('name', 'chart-run');

                    if (chartRun.firstChart.date != chartRun.lastChart.date) {
                        chartRunDiv.find('[name="chart-run-header"]').html(chartRunSize + " weeks: " + chartRun.firstChart.date + " - " + chartRun.lastChart.date);
                        chartRunDiv.find('[name="chart-run-header"]').addClass('chartRunTwo');
                    } else {
                        chartRunDiv.find('[name="chart-run-header"]').html(chartRunSize + " week: " +  chartRun.firstChart.date);
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

                        if (position == 1) {
                            no1++;
                        }
                        if (position <= 10) {
                            top10++;
                        }

                        if (position <= 20) {
                            top20++;
                        }

                        if (chartId == currentChart) {
                            chartRunDiv.find('[name="position"]:last').addClass('current');
                        } else {
                            chartRunDiv.find('[name="position"]:last').removeClass('current');
                        }

                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').on('click', function () {
                            event.stopPropagation();
                        });
                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('href', '/?chartNumber=' + chartId);
                        chartRunDiv.find('[name="position"]:last').find('[name="chartLink"]').attr('title', 'GOTO: Chart N' + chartId);
                        var positionDiv = chartRunDiv.find('[name="position"]:last').clone();
                        chartRunDiv.find('[name="positions"]').append(positionDiv);
                        chartId++;
                    });
                    chartRunDiv.find('[name="position"]:last').remove();

                    songDiv.find('[name="song-history"]').append(chartRunDiv);
                    chartRunDiv.css('display', 'block');
                    songDiv.find('[name="song-history"]').slideDown(500);
                    songDiv.find('[name="mov-info"]').addClass('history-mov')

                    songDiv.find('#stats-weeks-no1s').html(no1);
                    songDiv.find('#stats-weeks-top10s').html(top10);
                    songDiv.find('#stats-weeks-top20s').html(top20);
                    songDiv.addClass('history-open');
                });
            });
        }
    });

});
