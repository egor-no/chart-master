<div id="chart-table">
    <div class="no-display" name="chart-number">${chart.issueNumber}</div>
    <%@include file="chart-navigation.jsp"%>
    <div id="chart-status" class="collapsed">
        <div class="status-title" tabindex="0">
            <span class="status-icon">+</span>
            <span>Status</span>
        </div>

        <div class="status-body">
            <div class="status-message">
                TOP40 database loaded successfully.
            </div>

            <div class="status-row">
                <span>Songs</span>
                <span>${fn:length(chart.positions)}</span>
            </div>

            <div class="status-row">
                <span>Highest climb</span>
                <span>+${chart.stats.highestClimb}</span>
            </div>

            <div class="status-row">
                <span>Biggest fall</span>
                <span>-${chart.stats.biggestFall}</span>
            </div>

            <div class="status-row">
                <span>New entries</span>
                <span>${chart.stats.newEntries}</span>
            </div>

            <div class="status-row">
                <span>Re-entries</span>
                <span>${chart.stats.reEntries}</span>
            </div>
        </div>
    </div>
    <c:forEach items="${chart.positions}" var="position" varStatus="status">
        <div name="song">
            <div class="song-row">
                <input name="song-id" style="display:none;" type="text" value="${position.pk.song.id}" />

                <div name="mov-info" class="flex-mov data-start">
                    <div name="mov"></div>
                    <div name="mov-val"></div>
                </div>
                <div class="flex1 i-counter" style="justify-content: left;">
                    <p name="pos">${position.position}</p>
                </div>
                <div class="flex1 no-display">
                    <p name="lw">${position.lastWeek}</p>
                </div>
                <div class="flex4">
                    <p>
                        <span class="artist-link"
                              data-artists="${position.pk.song.artists}"
                              title="Open artist page"
                              tabindex="0">
                                ${position.pk.song.artists}
                        </span>
                    </p>
                </div>
                <div class="flex5">
                    <p>${position.pk.song.name}</p>
                </div>
                <div name="peak" class="flex1" title="peak">
                    <p>${chart.peaks[status.index]}</p>
                </div>
                <div class="flex1" title="weeks on chart">
                    <p name="woc" >${chart.woc[status.index]}</p>
                </div>
            </div>
            <%@include file="song-history.jsp"%>
        </div>
    </c:forEach>

    <c:if test="${not empty chart.outsiders}">
        <div class="chart-outsiders-title">
            <h3>Outsiders</h3>
        </div>

        <c:forEach items="${chart.outsiders}" var="outsider">
            <div name="song">
                <div class="song-row outsider-row">
                    <input name="song-id" style="display:none;" type="text" value="${outsider.position.pk.song.id}" />
                    <input name="outsider" style="display:none;" type="text" value="true" />

                    <div name="mov-info" class="flex-mov data-start out">
                        <div name="mov"></div>
                        <div name="mov-val"></div>
                    </div>

                    <div class="flex1 i-counter" style="justify-content: left;">
                        <p>${outsider.position.position}</p>
                    </div>

                    <div class="flex1 no-display">
                        <p name="lw">${outsider.position.position}</p>
                    </div>

                    <div class="flex4">
                        <p>
                        <span class="artist-link"
                              data-artists="${outsider.position.pk.song.artists}"
                              title="Open artist page"
                              tabindex="0">
                                ${outsider.position.pk.song.artists}
                        </span>
                        </p>
                    </div>

                    <div class="flex5">
                        <p>${outsider.position.pk.song.name}</p>
                    </div>

                    <div name="peak" class="flex1" title="peak">
                        <p>${outsider.peak}</p>
                    </div>

                    <div class="flex1" title="weeks on chart">
                        <p name="woc">${outsider.woc}</p>
                    </div>
                </div>

                <%@include file="song-history.jsp"%>
            </div>
        </c:forEach>
    </c:if>
    </tbody>
</div>

<%@include file="chart-run-template.jsp"%>
<%@ include file="../components/artist-picker-modal.jsp" %>

<script type = "text/javascript" >
    $(document).ready(function() {
        $('#chart-status .status-title').on('click', function () {
            var box = $('#chart-status');
            box.toggleClass('collapsed');

            if (box.hasClass('collapsed')) {
                box.find('.status-icon').text('+');
            } else {
                box.find('.status-icon').text('-');
            }
        });

        $('#chart-status .status-title').on('keydown', function (e) {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                $(this).click();
            }
        });
        
        $('[name="song"]').each(function () {
            if ($(this).find('[name="outsider"]').val() === 'true') {
                return;
            }

            var lw = $(this).find('[name="lw"]').text();
            var pos = $(this).find('[name="pos"]').text();
            var woc = $(this).find('[name="woc"]').text();
            var mov;
            if (lw != '' && lw != '0') {
                mov = parseInt(lw) - parseInt(pos);
                if (mov == 0) {
                    // $(this).find('#img').attr("src", "/icons/equal.png");
                    $(this).find('[name="mov-info"]').addClass('equal').attr('title', 'no change');
                    $(this).find('[name="mov"]').html("&#9632;");
                } else if (mov > 0) {
                    // $(this).find('#img').attr("src", "/icons/up.png");
                    $(this).find('[name="mov-info"]').addClass('up').attr('title', 'up ' + mov);
                    $(this).find('[name="mov"]').html("&#9650;");
                    $(this).find('[name="mov-val"]').html(mov);
                } else {
                    mov = mov * -1;
                    // $(this).find('#img').attr("src", "/icons/down.png");
                    $(this).find('[name="mov-info"]').addClass('down').attr('title', 'down ' + mov);
                    $(this).find('[name="mov"]').html("&#9660;");
                    $(this).find('[name="mov-val"]').html(mov);
                }
            } else {
                if (parseInt(woc) > 1) {
                    $(this).find('[name="mov-info"]').addClass('re').attr('title', 're-entry');
                    $(this).find('[name="mov"]').html("&#9670;");
                    // $(this).find('#img').attr("src", "/icons/re.png");
                } else {
                    $(this).find('[name="mov-info"]').addClass('new').attr('title', 'new');
                    $(this).find('[name="mov"]').html("&#9733;");
                    // $(this).find('#img').attr("src", "/icons/re.png");
                }
            }
        });

        ArtistPicker.init();
    });
</script>
