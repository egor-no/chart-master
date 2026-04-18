
<div id="song-list">
    <div class="song-row song-header-row">
        <div class="flex-date sortable-header active-sort asc" data-sort="date">
            <p style="margin-bottom:0;"><b>Entered</b> <span class="sort-indicator">&#8593;</span></p>
        </div>

        <div class="flex1 sortable-header" data-sort="peak">
            <p style="margin-bottom:0;"><b>Peak</b></p>
        </div>

        <div class="flex9">
        </div>

        <div class="flex-end sortable-header" data-sort="weeks">
            <p style="margin-bottom:0;"><b>WoC</b></p>
        </div>

        <div class="flex1">
        </div>
    </div>

    <c:forEach items="${songRows}" var="row">
        <c:set var="song" value="${row.song}" />
        <c:set var="firstEntryDate" value="${row.firstEntryDate}" />
        <c:set var="firstEntryDateSortable" value="${row.firstEntryDateSortable}" />
        <%@include file="song-entry.jsp"%>
    </c:forEach>
</div>

<script>
    $(document).ready(function () {
        $('.sortable-header').on('click', function () {
            const $clicked = $(this);
            const sortField = $clicked.data('sort');

            let direction = 'asc';

            if ($clicked.hasClass('active-sort')) {
                direction = $clicked.hasClass('asc') ? 'desc' : 'asc';
            }

            $('.sortable-header')
                .removeClass('active-sort asc desc')
                .find('.sort-indicator').remove();

            $clicked.addClass('active-sort').addClass(direction);
            $clicked.find('p').append(
                ' <span class="sort-indicator">' +  (direction === 'asc' ? '&#8593;' : '&#8595;') + '</span>'
            );

            const $container = $('#song-list');
            const rows = $container.children('[name="song"]').get();

            rows.sort(function (a, b) {
                const $a = $(a).find('.sortable-song-row').first();
                const $b = $(b).find('.sortable-song-row').first();

                let valA, valB;

                if (sortField === 'date') {
                    valA = ($a.data('first-entry') || '').toString();
                    valB = ($b.data('first-entry') || '').toString();
                    return direction === 'asc'
                        ? valA.localeCompare(valB)
                        : valB.localeCompare(valA);
                }

                if (sortField === 'peak') {
                    valA = parseInt($a.data('peak'), 10) || 0;
                    valB = parseInt($b.data('peak'), 10) || 0;
                    return direction === 'asc' ? valA - valB : valB - valA;
                }

                if (sortField === 'weeks') {
                    valA = parseInt($a.data('weeks'), 10) || 0;
                    valB = parseInt($b.data('weeks'), 10) || 0;
                    return direction === 'asc' ? valA - valB : valB - valA;
                }

                return 0;
            });

            rows.forEach(function (row) {
                $container.append(row);
            });
        });
    });
</script>

<%@include file="chart-run-template.jsp"%>
