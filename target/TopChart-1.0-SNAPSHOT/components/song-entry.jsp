<div name="song">
    <div class="song-row sortable-song-row"
         data-peak="${song.peak}"
         data-weeks="${song.weeks}"
         data-first-entry="${firstEntryDateSortable}">

        <input name="song-id" style="display:none;" type="text" value="${song.id}" />
        <div class="flex-date data-start">
            <c:if test="${not empty firstEntryDate}">
                <div class="first-date">
                        ${firstEntryDate.substring(0,6)}
                    <br>
                    <span class="year">
                            ${firstEntryDate.substring(7)}
                    </span>
                </div>
            </c:if>
        </div>
        <div name="peak" class="flex1">
            <p>${song.peak}</p>
        </div>
        <div class="flex4">
            <p>
                <c:choose>
                    <c:when test="${artistPickerEnabled}">
                        <span class="artist-link"
                              name="song-artist"
                              data-artists="${song.artists}"
                              title="Open artist page"
                              tabindex="0">
                                ${song.artists}
                        </span>
                    </c:when>

                    <c:otherwise>
                        <span name="song-artist">
                                ${song.artists}
                        </span>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
        <div class="flex5">
            <p>
                <span name="song-title">${song.name}</span>
                <c:if test="${row.currentlyCharting}">
                    <span class="currently-charting">CHARTING</span>
                </c:if>
            </p>
        </div>
        <div class="flex1 flex-end data-end">
            <p name="woc">${song.weeks}</p>
        </div>
        <div class="flex1">
            <c:if test="${loggedIn and isOwner}">
                <p><a name="edit-link" href="#">&#10000;</a></p>
            </c:if>
        </div>
    </div>
    <%@include file="song-history.jsp"%>
</div>