
$(document).ready(function() {
    var currentShareSongData = null;

    $('[name="edit-link"]').on('click', function (event) {
        var idSong = $(this)
            .closest('[name="song"]')
            .find('[name="song-id"]')
            .val();

        $(this).attr(
            'href',
            '/songedit?id=' + idSong + '&search=' + '${search}'
        );

        event.stopPropagation();
    });

    $(document).on('click', '[name="share-song"]', function (event) {
        event.preventDefault();
        event.stopPropagation();

        var songDiv = $(this).closest('[name="song"]');

        currentShareSongData = getShareSongData(songDiv);

        var selectedTemplate =
            $('[name="share-song-template"]:checked').val();

        updateSongTemplatePreview(selectedTemplate);

        $('#share-song-modal').removeClass('no-display');
    });

    $('[name="share-song-close"]').on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();

        $('#share-song-modal').addClass('no-display');
        currentShareSongData = null;
    });

    $('[name="share-song-download"]').on('click', function (event) {
        event.preventDefault();
        event.stopPropagation();

        if (!currentShareSongData) {
            return;
        }

        var template =
            $('[name="share-song-template"]:checked').val();

        buildSongShareCard(template, currentShareSongData);
        downloadSongShareCard(template, currentShareSongData);

        $('#share-song-modal').addClass('no-display');
        currentShareSongData = null;
    });

    $('[name="song"]').on('click', function(event) {
        if ($(event.target).closest(
            '[name="share-song"], ' +
            '[name="edit-link"], ' +
            '[name="chartLink"], ' +
            '.artist-link'
        ).length) {
            return;
        }

        event.preventDefault();

        var songDiv =  $(this).closest('[name="song"]')

        if (songDiv.data('history-loading') === true) {
            return;
        }

        if (songDiv.data('history-loaded') === true) {
            toggleSongHistory(songDiv);
            return;
        }

        loadSongHistory(songDiv);
    });

    function loadSongHistory(songDiv) {
        var idSong = songDiv.find('[name="song-id"]').val();

        var chartNumber = '';

        if (songDiv.find('[name="chart-number"]').length && songDiv.find('[name="chart-number"]').val() !== '') {
            chartNumber = songDiv.find('[name="chart-number"]').val();
        } else if ($(document).find('[name="chart-number"]').length) {
            chartNumber = $(document)
                .find('[name="chart-number"]')
                .first().text().trim();
        }

        var dateSearch = false;
        var date1 = '';
        var date2 = '';

        if ($(document).find('[name="date1-data"]').length && $(document).find('[name="date1-data"]').val() !== '') {
            dateSearch = true;
            date1 = $(document).find('[name="date1-data"]').val();

            if ($(document).find('[name="date2-data"]').length) {
                date2 = $(document).find('[name="date2-data"]').val();
            }
        }

        songDiv.data('history-loading', true);
        songDiv.addClass('history-loading');

        $.get('songhistory', {
            idSong: idSong,
            chartNumber: chartNumber,
            dateSearch: dateSearch,
            date1: date1,
            date2: date2
        })
            .done(function(songhistory) {
                renderSongHistory(songDiv, songhistory);

                songDiv.data('history-loaded', true);
                openSongHistory(songDiv);
            })
            .fail(function(xhr) {
                console.error(
                    'Could not load song history',
                    xhr.status,
                    xhr.responseText
                );
            })
            .always(function() {
                songDiv.data('history-loading', false);
                songDiv.removeClass('history-loading');
            });
    }

    function renderSongHistory(songDiv, songhistory) {
        var no1 = 0;
        var top10 = 0;
        var top20 = 0;

        var peak = songhistory.peak;
        var currentChart = songhistory.currentIssue;
        var historyDiv = songDiv.find('[name="song-history"]');

        historyDiv.find('[name="chart-run"]').remove();

        var firstEntryDate = '';

        if (songhistory.chartRuns.length > 0) {
            firstEntryDate =
                songhistory.chartRuns[0].firstChart.date;
        }

        songDiv.data('share-first-entry', firstEntryDate);

        $.each(songhistory.chartRuns, function(index, chartRun) {
            var chartRunSize = chartRun.positions.length;

            var chartRunDiv = $(document)
                .find('[name="chart-run-template"]')
                .first()
                .clone();

            chartRunDiv.attr('name', 'chart-run');

            if (chartRun.firstChart.date !== chartRun.lastChart.date) {
                chartRunDiv
                    .find('[name="chart-run-header"]')
                    .html(
                        chartRunSize +
                        ' weeks: ' +
                        chartRun.firstChart.date +
                        ' - ' +
                        chartRun.lastChart.date
                    )
                    .addClass('chartRunTwo');
            } else {
                chartRunDiv
                    .find('[name="chart-run-header"]')
                    .html(
                        chartRunSize +
                        ' week: ' +
                        chartRun.firstChart.date
                    )
                    .addClass('chartRunOne');
            }

            $.each(chartRun.positions, function(i, item) {
                var position = item.position;
                var issueNumber = item.issueNumber;
                var chartDate = new Date(item.date);

                var positionTemplate =
                    chartRunDiv.find('[name="position"]:last');

                positionTemplate
                    .find('[name="chartLink"]')
                    .html(position);

                positionTemplate.toggleClass(
                    'peak',
                    position === peak
                );

                positionTemplate.toggleClass(
                    'current',
                    issueNumber === currentChart
                );

                if (position === 1) {
                    no1++;
                }

                if (position <= 10) {
                    top10++;
                }

                if (position <= 20) {
                    top20++;
                }

                positionTemplate
                    .find('[name="chartLink"]')
                    .attr(
                        'href',
                        '/chart?chartNumber=' + issueNumber
                    )
                    .attr(
                        'title',
                        'GOTO: Chart N' +
                        issueNumber +
                        ' | ' +
                        formatDate(chartDate)
                    );

                var positionDiv = positionTemplate.clone();

                chartRunDiv
                    .find('[name="positions"]')
                    .append(positionDiv);
            });

            chartRunDiv.find('[name="position"]:last').remove();

            historyDiv.append(chartRunDiv);
            chartRunDiv.css('display', 'block');
        });

        historyDiv.find('[name="chartLink"]').on('click', function(event) {
            event.stopPropagation();
        });

        songDiv.find('[name="stats-weeks-no1s"]').text(no1);
        songDiv.find('[name="stats-weeks-top10s"]').text(top10);
        songDiv.find('[name="stats-weeks-top20s"]').text(top20);
    }

    function toggleSongHistory(songDiv) {
        var historyDiv = songDiv.find('[name="song-history"]');

        if (historyDiv.is(':visible')) {
            closeSongHistory(songDiv);
        } else {
            openSongHistory(songDiv);
        }
    }

    function openSongHistory(songDiv) {
        songDiv
            .find('[name="song-history"]')
            .stop(true, true)
            .slideDown(500);

        songDiv
            .find('[name="mov-info"]')
            .addClass('history-mov');

        songDiv
            .find('[name="i-report"]')
            .addClass('i-highlight');

        songDiv.addClass('history-open');

        if (songDiv.find('[name="outsider"]').val() === 'true') {
            songDiv.addClass('history-open-outsider');
        }
    }

    function closeSongHistory(songDiv) {
        songDiv
            .find('[name="song-history"]')
            .stop(true, true)
            .slideUp(500, function() {
                songDiv.removeClass(
                    'history-open history-open-outsider'
                );

                songDiv
                    .find('[name="mov-info"]')
                    .removeClass('history-mov');

                songDiv
                    .find('[name="i-report"]')
                    .removeClass('i-highlight');
            });
    }

    $(document).on(
        'change',
        '[name="share-song-template"]',
        function () {
            updateSongTemplatePreview($(this).val());
        }
    );

});

function updateSongTemplatePreview(template) {
    var preview = $('[name="share-song-template-preview"]');
    var title = preview.find('[name="share-preview-title"]');
    var movement = preview.find('[name="share-preview-movement"]');

    preview.removeClass(
        'preview-classic95 ' +
        'preview-magazine ' +
        'preview-winamp ' +
        'preview-vinyl'
    );

    preview.addClass('preview-' + template);

    switch (template) {
        case 'classic95':
            title.text('SONGINFO.EXE');
            movement.text('+12');
            break;

        case 'magazine':
            title.text('SONG SPOTLIGHT');
            movement.text('+12');
            break;

        case 'winamp':
            title.text('TOP40 SONG PLAYER');
            movement.text('+12');
            break;

        case 'vinyl':
            title.text('VINYL SINGLE');
            movement.text('+12');
            break;

        default:
            title.text('');
            movement.text('');
    }
}

function formatDate(date) {
    let year = date.getFullYear();
    let month = (date.getMonth() + 1).toString().padStart(2, '0'); // Month is 0-indexed
    let day = date.getDate().toString().padStart(2, '0');

    return year + "-" + month + "-" + day;
}

function getShareSongData(songDiv) {
    var row = songDiv.find('.song-row').first();
    var movInfo = row.find('[name="mov-info"]');

    var movement = getShareSongMovement(movInfo);

    var position = row.find('[name="pos"]').text().trim();
    var issueNumber = $('[name="chart-number"]').first().text().trim();

    return {
        songId: row.find('[name="song-id"]').val() || '',

        artist: row.find('[name="song-artist"]').text().trim(),
        title: row.find('[name="song-title"]').text().trim(),

        position: position,
        movement: movement,

        peak: row.find('[name="peak"] p').text().trim(),
        weeksTop40: row.find('[name="woc"]').text().trim(),
        weeksNo1: songDiv.find('[name="stats-weeks-no1s"]').text().trim(),
        weeksTop10: songDiv.find('[name="stats-weeks-top10s"]').text().trim(),
        weeksTop20: songDiv.find('[name="stats-weeks-top20s"]').text().trim(),

        chartTitle: '${sessionScope.chartTitle}',
        chartAuthor: '${sessionScope.chartAuthorName}',

        chartDate: $('[name="share-chart-date"]').first().text().trim(),
        issueNumber: issueNumber,
        firstEntryDate: songDiv.data('share-first-entry') || '',

        achievements: getShareSongAchievements(songDiv, position, issueNumber, movInfo)
    };
}

function getShareSongAchievements(songDiv, currentPositionValue, currentIssueValue, movInfo) {
    var currentPosition = parseInt(currentPositionValue, 10);
    var currentIssue = parseInt(currentIssueValue, 10);

    var movementValue = parseInt(
        movInfo.find('[name="mov-val"]').text().trim(),
        10
    );

    if (isNaN(currentPosition) || isNaN(currentIssue)) {
        return [];
    }

    var historyEntries = getShareSongHistoryEntries(songDiv);

    var previousEntries = historyEntries
        .filter(function (entry) {
            return entry.issue < currentIssue;
        })
        .sort(function (a, b) {
            return a.issue - b.issue;
        });

    if (currentPosition <= 20 && isShareSongRebound(previousEntries, currentIssue)) {
        return [{
            code: 'rebound',
            label: 'REBOUND'
        }];
    }

    if (movInfo.hasClass('up') && !isNaN(movementValue) && movementValue > 20) {
        return [{
            code: 'high-climber',
            label: 'HIGH CLIMBER'
        }];
    }

    if (movInfo.hasClass('down') && !isNaN(movementValue) && movementValue > 20) {
        return [{
            code: 'free-falling',
            label: 'FREE FALLING'
        }];
    }

    if (!previousEntries.length) {
        return [];
    }

    var previousPeak = Math.min.apply(
        null,
        previousEntries.map(function (entry) {
            return entry.position;
        })
    );

    if (currentPosition < previousPeak) {
        return [{
            code: 'new-peak',
            label: 'NEW PEAK'
        }];
    }

    if (currentPosition === previousPeak && (movInfo.hasClass('up') || movInfo.hasClass('re'))) {
        return [{
            code: 're-peak',
            label: 'RE-PEAK'
        }];
    }

    return [];
}

function getShareSongHistoryEntries(songDiv) {
    var entries = [];

    songDiv
        .find('[name="song-history"] [name="chartLink"]')
        .each(function () {
            var link = $(this);

            var position = parseInt(
                link.text().trim(),
                10
            );

            var issue = getChartIssueFromLink(link);

            if (isNaN(position) || isNaN(issue)) {
                return;
            }

            entries.push({
                issue: issue,
                position: position
            });
        });

    return entries;
}

function isShareSongRebound(previousEntries, currentIssue) {
    var previousTop20Entries = previousEntries.filter(function (entry) {
        return entry.position <= 20;
    });

    if (!previousTop20Entries.length) {
        return false;
    }

    var lastTop20Entry = previousTop20Entries.reduce(
        function (latestEntry, entry) {
            if (
                !latestEntry ||
                entry.issue > latestEntry.issue
            ) {
                return entry;
            }

            return latestEntry;
        },
        null
    );

    var fullIssuesOutsideTop20 =
        currentIssue - lastTop20Entry.issue - 1;

    if (fullIssuesOutsideTop20 < 8) {
        return false;
    }

    var returnedEarlier = previousEntries.some(function (entry) {
        return (
            entry.issue > lastTop20Entry.issue &&
            entry.issue < currentIssue &&
            entry.position <= 20
        );
    });

    return !returnedEarlier;
}

function getChartIssueFromLink(link) {
    var href = link.attr('href') || '';

    var match = href.match(/[?&]chartNumber=(\d+)/);

    if (!match) {
        return NaN;
    }

    return parseInt(match[1], 10);
}
function getShareSongMovement(movInfo) {
    if (!movInfo || !movInfo.length) {
        return {
            classic: '',
            text: '',
            label: '',
            cssClass: ''
        };
    }

    var value = movInfo.find('[name="mov-val"]').text().trim();

    if (movInfo.hasClass('out')) {
        return {
            classic: '',
            text: 'OUT',
            label: 'OUT OF CHART',
            cssClass: 'out'
        };
    }

    if (movInfo.hasClass('up')) {
        return {
            classic: '&#9650;' + value,
            text: '+' + value,
            label: 'UP ' + value,
            cssClass: 'up'
        };
    }

    if (movInfo.hasClass('down')) {
        return {
            classic: '&#9660;' + value,
            text: '-' + value,
            label: 'DOWN ' + value,
            cssClass: 'down'
        };
    }

    if (movInfo.hasClass('new')) {
        return {
            classic: '&#9733;',
            text: 'NEW',
            label: 'NEW ENTRY',
            cssClass: 'new'
        };
    }

    if (movInfo.hasClass('re')) {
        return {
            classic: '&#9670;',
            text: 'RE',
            label: 'RE-ENTRY',
            cssClass: 're'
        };
    }

    return {
        classic: '&#9632;',
        text: '=',
        label: 'NO CHANGE',
        cssClass: 'equal'
    };
}

function fillSongCard(card, data, template) {
    card.find('[name="share-song-artist"]').text(data.artist);
    card.find('[name="share-song-title"]').text(data.title);

    var hasCurrentPosition = Boolean(data.position);
    var displayedPosition = hasCurrentPosition
        ? data.position
        : data.peak;

    card.find('[name="share-song-position"]').text(
        displayedPosition ? '#' + displayedPosition : ''
    );

    card.find('[name="share-song-position-label"]').text(
        hasCurrentPosition ? '' : 'PEAK'
    );

    var movementElement =
        card.find('[name="share-song-movement"]');

    movementElement
        .removeClass('up down equal new re out')
        .addClass(data.movement.cssClass);

    if (template === 'classic95') {
        movementElement.html(data.movement.classic);
    } else {
        movementElement.text(data.movement.text);
    }

    card.find('[name="share-song-issue"]').text(
        data.issueNumber ? 'Issue #' + data.issueNumber : ''
    );

    card.find('[name="share-song-no1"]').text(data.weeksNo1);
    card.find('[name="share-song-top10"]').text(data.weeksTop10);
    card.find('[name="share-song-top20"]').text(data.weeksTop20);
    card.find('[name="share-song-top40"]').text(data.weeksTop40);

    card.find('[name="share-song-author"]').text(data.chartAuthor);
    card.find('[name="share-song-chart-title"]').text(data.chartTitle);

    if (data.chartDate) {
        card.find('[name="share-song-date-label"]').text('Chart date:');
        card.find('[name="share-song-date"]').text(data.chartDate);
    } else {
        card.find('[name="share-song-date-label"]').text('First entry:');
        card.find('[name="share-song-date"]').text(data.firstEntryDate);
    }

    var metaText = '';
    if (data.chartDate && data.issueNumber) {
        metaText = data.chartDate + ' / Issue #' + data.issueNumber;
    } else if (data.chartDate) {
        metaText = data.chartDate;
    } else if (data.firstEntryDate) {
        metaText = 'First entry: ' + data.firstEntryDate;
    }
    card.find('[name="share-song-meta-text"]').text(metaText);

    fillSongAchievements(card, data.achievements || []);

    var hasCurrentChartContext = Boolean(data.chartDate || data.issueNumber || data.position);
    var hasAnyDate = Boolean(data.chartDate || data.firstEntryDate);

    card.toggleClass(
        'without-chart-context',
        !hasCurrentChartContext
    );

    card.toggleClass(
        'without-date',
        !hasAnyDate
    );
}

function fillSongAchievements(card, achievements) {
    var container = card.find(
        '[name="share-song-achievements"]'
    );

    container.empty();

    if (!achievements || !achievements.length) {
        container.addClass('no-display');
        return;
    }

    $.each(achievements, function (index, achievement) {
        $('<span>')
            .addClass('song-achievement')
            .addClass('song-achievement-' + achievement.code)
            .text(achievement.label)
            .appendTo(container);
    });

    container.removeClass('no-display');
}

function buildSongShareCard(template, data) {
    var card = $('#share-song-card-' + template);

    if (!card.length) {
        console.error('Song share card not found: ' + template);
        return false;
    }

    fillSongCard(card, data, template);
    return true;
}

function downloadSongShareCard(template, data) {
    var element = document.querySelector(
        '#share-song-card-' + template
    );

    if (!element) {
        console.error('Song share card not found: ' + template);
        return;
    }

    html2canvas(element, {
        width: 1080,
        height: 1080,
        scale: 1,
        backgroundColor: null
    }).then(function (canvas) {
        var link = document.createElement('a');

        link.download = createSongFileName(
            data.artist,
            data.title,
            template
        );

        link.href = canvas.toDataURL('image/png');
        link.click();
    });
}

function createSongFileName(artist, title, template) {
    var fileName = artist + '-' + title;

    fileName = fileName
        .toLowerCase()
        .replace(/[^a-zа-яё0-9]+/gi, '-')
        .replace(/^-+|-+$/g, '');

    return fileName + '-' + template + '.png';
}