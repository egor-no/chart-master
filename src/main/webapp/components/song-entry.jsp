<div name="song" style="display: flex; flex-flow: column; border-bottom: 1px solid grey;">
    <div style="display: flex; flex-flow: row nowrap;">
        <input name="song-id" style="display:none;" type="text" value="${song.id}" />

        <div style="display: flex; flex: 1;">
            <p>${song.peak}</p>
        </div>
        <div style="display: flex; flex: 4;">
            <p>${song.artists}</p>
        </div>
        <div style="display: flex; flex: 5;">
            <p>${song.name}</p>
        </div>
        <div style="display: flex; flex: 1; justify-content: end;">
            <p> ${song.weeks}</p>
        </div>
        <div style="display: flex; flex: 1;">
            <a name="history-link" href="#">?</a>
            <a name="edit-link" href="#">+</a>
        </div>
    </div>
    <div style="display:none;" name="song-history">

    </div>
</div>