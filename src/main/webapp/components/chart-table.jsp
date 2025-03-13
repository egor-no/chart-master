<div id="chart-table" style="width:800px; display: flex;  flex-flow: column;">
    <div style="display: none;" name="chart-number">${chart.id}</div>
    <div style="display: flex; flex-flow: row nowrap; margin-bottom: 5px;">
        <b>CHART: ${chart.date} </b>
    </div>
    <div style="display: flex; flex-flow: row nowrap;">
        <div style="display: flex; flex: 1;">
            <b>Mov</b>
        </div>
        <div style="display: flex; flex: 1;">
            <b>Pos</b>
        </div>
        <div style="display: none;">
            <b>LW</b>
        </div>
        <div style="display: flex; flex: 4;">
            <b>Artists</b>
        </div>
        <div style="display: flex; flex: 5;">
            <b>Title</b>
        </div>
        <div style="display: flex; flex: 1;">
            <b>Peak</b>
        </div>
        <div style="display: flex; flex: 1;">
            <b>WoC</b>
        </div>
        <div style="display: flex; flex: 1;">
        </div>
    </div>
    <c:forEach items="${chart.positions}" var="position" varStatus="status">
        <div name="song" style="display: flex; flex-flow: column; border-bottom: 1px solid grey;">
            <div style="display: flex; flex-flow: row nowrap;">
                <input name="song-id" style="display:none;" type="text" value="${position.pk.song.id}" />

                <div style="display: flex; flex: 1;">
                    <p name="mov"></p>
                </div>
                <div style="display: flex; flex: 1;">
                    <p name="pos" >${position.position}</p>
                </div>
                <div style="display: none;">
                    <p  name="lw">${position.lastWeek}</p>
                </div>
                <div style="display: flex; flex: 4;">
                    <p>${position.pk.song.artists}</p>
                </div>
                <div style="display: flex; flex: 5;">
                    <p>${position.pk.song.name}</p>
                </div>
                <div name="peak" style="display: flex; flex: 1;">
                    <p>${chart.peaks[status.index]}</p>
                </div>
                <div style="display: flex; flex: 1;">
                    <p name="woc" >${chart.woc[status.index]}</p>
                </div>
                <div style="display: flex; flex: 1;">
                    <a name="history-link" href="#">?</a>
                </div>
            </div>
            <div style="display:none;" name="song-history">

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
