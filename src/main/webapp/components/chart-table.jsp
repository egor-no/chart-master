<div id="chart-table">
    <div class="no-display" name="chart-number">${chart.id}</div>
    <div class="song-row">
        <b>CHART: ${chart.date} </b>
    </div>
    <div class="song-row">
        <div class="flex1 title">
            Mov
        </div>
        <div class="flex1 title">
            Pos
        </div>
        <div class="no-display">
            LW
        </div>
        <div class="flex4 title">
            Artists
        </div>
        <div class="flex5 title">
            Title
        </div>
        <div class="flex1 title">
            Peak
        </div>
        <div class="flex1 title">
            WoC
        </div>
        <div class="flex1">
        </div>
    </div>
    <c:forEach items="${chart.positions}" var="position" varStatus="status">
        <div name="song">
            <div class="song-row">
                <input name="song-id" style="display:none;" type="text" value="${position.pk.song.id}" />

                <div class="flex1 data-start">
                    <p name="mov"></p>
                </div>
                <div class="flex1">
                    <p name="pos" >${position.position}</p>
                </div>
                <div class="flex1 no-display">
                    <p name="lw">${position.lastWeek}</p>
                </div>
                <div class="flex4">
                    <p>${position.pk.song.artists}</p>
                </div>
                <div class="flex5">
                    <p>${position.pk.song.name}</p>
                </div>
                <div name="peak" class="flex1">
                    <p>${chart.peaks[status.index]}</p>
                </div>
                <div class="flex1">
                    <p name="woc" >${chart.woc[status.index]}</p>
                </div>
                <div class="flex1">
                    <a name="history-link" href="#">?</a>
                </div>
            </div>
            <div class="no-display" name="song-history">

            </div>
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
                    $(this).find('[name="mov"]').append("<img src='/icons/equal.png'/>");
                } else if (mov > 0) {
                    $(this).find('[name="mov"]').append("<img src='/icons/up.png'/>");
                    $(this).find('[name="mov"]').append(mov);
                } else {
                    mov = mov * -1;
                    $(this).find('[name="mov"]').append("<img src='/icons/down.png'/>");
                    $(this).find('[name="mov"]').append(mov);
                }
            } else {
                if (parseInt(woc) > 1) {
                    $(this).find('[name="mov"]').append("<img src='/icons/re.png'/>");
                } else {
                    $(this).find('[name="mov"]').append("<img src='/icons/new.png'/>");
                }
            }
        });
    });
</script>
