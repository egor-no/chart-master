<div id="chartNav" class="song-row">
    <input class="no-display" name="chart-id" type="text" value="${chart.id}"/>
    <input class="no-display" name="is-last" value="${isLastChart}" />
    <a href="#" name="prevChart"><</a>
    <b>CHART: ${chart.date} </b>
    <a href="#" name="nextChart">></a>
    <a href="#" name="curChart">>></a>
</div>

<script type = "text/javascript" >
    $(document).ready(function() {
        if ($('[name="is-last"]').val() == 'true') {
            $('[name="nextChart"]').addClass('no-display');
            $('[name="curChart"]').addClass('no-display');
        } else {
            $('[name="nextChart"]').attr("href", "/?chartNumber=" + (Number($('[name="chart-id"]').val()) + 1));
            $('[name="curChart"]').attr("href", "/");
        }
        if ($('[name="chart-id]').val() == 1) {
            $('[name="prevChart"]').addClass('no-display');
        } else {

            $('[name="prevChart"]').attr("href", "/?chartNumber=" + (Number($('[name="chart-id"]').val()) - 1));
        }
    });
</script>
