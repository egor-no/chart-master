<div id="chartNav" class="song-row">
    <div class="flex9">
        <input class="no-display" name="chart-id" type="text" value="${chart.id}"/>
        <input class="no-display" name="is-last" value="${isLastChart}" />
        <a href="#" title="GOTO Previous chart" name="prevChart"><</a>
        <b>CHART: ${chart.date} </b>
        <a name="edit-chart-link" href="#">&#10000;</a>
        <a href="#" title="GOTO Next chart" name="nextChart">></a>
        <a href="#" title="GOTO Latest chart" name="curChart">>></a>
    </div>

    <div class="flex1 flex-end">
        <a href="#" target="_blank" title="Export to WordPress" name="wp-export"><i class="fa fa-wordpress wp-icon" aria-hidden="true"></i></a>
    </div>
</div>

<script type = "text/javascript" >
    $(document).ready(function() {
        $('[name="edit-chart-link"]').attr("href", "/chartedit?chartNumber=" + (Number($('[name="chart-id"]').val())));

        if ($('[name="is-last"]').val() == 'true') {
            $('[name="nextChart"]').addClass('no-display');
            $('[name="curChart"]').addClass('no-display');
            $('[name="edit-chart-link"]').removeClass('no-display');
            $('[name="wp-export"]').removeClass('no-display');
            $('[name="wp-export"]').attr("href", "/wp-export?chartNumber=" + (Number($('[name="chart-id"]').val())));
        } else {
            $('[name="wp-export"]').addClass('no-display');
            $('[name="edit-chart-link"]').addClass('no-display');
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
