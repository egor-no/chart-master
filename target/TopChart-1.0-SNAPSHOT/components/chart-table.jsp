<div id="chart-table">
    <div class="no-display" name="chart-number">${chart.issueNumber}</div>
    <%@include file="chart-navigation.jsp"%>
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
                    <p><a href="/artist?artist=${position.pk.song.artists}" target="_blank">${position.pk.song.artists}</a></p>
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
    </tbody>
</div>

<%@include file="chart-run-template.jsp"%>

<script type = "text/javascript" >
    $(document).ready(function() {
        $('[name="song"]').each(function () {
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

        $('[name="song"] a').on('click', function () {
            event.stopPropagation();
        });
    });
</script>
