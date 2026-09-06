package com.chart.TopChart.service;

import com.chart.TopChart.data.dao.ChartDAOImpl;
import com.chart.TopChart.data.dao.ChartInfoDAOImpl;
import com.chart.TopChart.data.dao.PositionDAOImpl;
import com.chart.TopChart.data.dao.SongDAOImpl;
import com.chart.TopChart.data.model.Chart;
import com.chart.TopChart.data.model.ChartInfo;
import com.chart.TopChart.data.model.Position;
import com.chart.TopChart.data.model.Position_PK;
import com.chart.TopChart.data.model.Song;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class ChartServiceEditTest {
    // Song and Chart use assigned IDs, not database-generated IDs.
    private static final AtomicLong IDS = new AtomicLong(1000);
    private ChartInfo info;
    private Song filler;

    @BeforeEach
    void setUp() {
        clearDatabase();
        info = new ChartInfo();
        info.setTitle("Edit regression chart");
        info.setSize(40);
        info.setCreatedAt(LocalDateTime.now());
        ChartInfoDAOImpl.save(info);
        filler = createSong("Other song", 0, 41);
    }

    @AfterEach
    void clearDatabase() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Never run fixture cleanup against a production connection.
            session.doWork(connection -> assertTrue(connection.getMetaData().getURL()
                    .startsWith("jdbc:h2:mem:chart-edit-tests")));
            session.beginTransaction();
            session.createQuery("delete from Position").executeUpdate();
            session.createQuery("delete from Chart").executeUpdate();
            session.createQuery("delete from Song").executeUpdate();
            session.createQuery("delete from ChartInfo").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void shouldKeepDebutSongIdAndOneWeekWhenRetainedInEditedChart() {
        Song song = createHistory(1);
        int issue = ChartDAOImpl.getLastIssueNumber(info.getId());
        String[] updatedIds = ids(filler, song);

        deleteLatest(issue, updatedIds);
        assertSongStats(song, 0, 41);

        form(updatedIds);

        assertSongStats(song, 1, 2);
        assertCurrentPosition(song, issue, 2);
    }

    @Test
    void shouldDeleteDebutSongEntirelyWhenRemovedFromEditedChart() {
        Song song = createHistory(1);
        int issue = ChartDAOImpl.getLastIssueNumber(info.getId());
        String[] updatedIds = ids(filler);

        deleteLatest(issue, updatedIds);
        assertTrue(positions(song).isEmpty());
        assertNull(SongDAOImpl.getById(song.getId()));

        form(updatedIds);

        assertNull(SongDAOImpl.getById(song.getId()));
        assertTrue(positions(song).isEmpty());
        assertAbsentFromCurrentChart(song, issue);
    }

    @Test
    void shouldKeepHistoryAndRecalculateStatsWhenSongRemovedFromLatestChart() {
        Song song = createHistory(8, 5, 1);
        int issue = ChartDAOImpl.getLastIssueNumber(info.getId());
        String[] updatedIds = ids(filler);

        deleteLatest(issue, updatedIds);
        assertSongStats(song, 2, 5);
        assertHistoricalPositions(song);

        form(updatedIds);

        assertSongStats(song, 2, 5);
        assertHistoricalPositions(song);
        assertAbsentFromCurrentChart(song, issue);
    }

    @Test
    void shouldKeepSongIdAndRestoreWeeksWithNewPeakWhenHistoricalSongRetained() {
        Song song = createHistory(8, 5, 1);
        int originalWeeks = SongDAOImpl.getById(song.getId()).getWeeks();
        int issue = ChartDAOImpl.getLastIssueNumber(info.getId());
        String[] updatedIds = ids(filler, song);

        deleteLatest(issue, updatedIds);
        assertSongStats(song, originalWeeks - 1, 5);
        assertHistoricalPositions(song);

        form(updatedIds);

        assertSongStats(song, originalWeeks, 2);
        assertCurrentPosition(song, issue, 2);
        assertEquals(Integer.valueOf(5), PositionDAOImpl
                .getPositionForSong(info.getId(), song.getId(), issue).getLastWeek());
    }

    private Song createHistory(int... ranks) {
        Song song = createSong("Subject song", ranks.length, Arrays.stream(ranks).min().getAsInt());
        for (int i = 0; i < ranks.length; i++) {
            Chart chart = new Chart();
            chart.setId(IDS.incrementAndGet());
            chart.setInfo(info);
            chart.setIssueNumber(i + 1);
            chart.setDate(LocalDate.of(2026, 1, 1).plusWeeks(i).toString());
            ChartDAOImpl.save(chart);
            PositionDAOImpl.save(new Position(new Position_PK(chart, song), ranks[i],
                    i == 0 ? null : ranks[i - 1]));
        }
        assertSongStats(song, ranks.length, Arrays.stream(ranks).min().getAsInt());
        return song;
    }

    private Song createSong(String name, int weeks, int peak) {
        Song song = new Song();
        song.setId(IDS.incrementAndGet());
        song.setName(name);
        song.setArtists("Test artist");
        song.setWeeks(weeks);
        song.setPeak(peak);
        SongDAOImpl.save(song);
        return song;
    }

    private String[] ids(Song... songs) {
        return Arrays.stream(songs).map(song -> Long.toString(song.getId())).toArray(String[]::new);
    }

    private void deleteLatest(int issue, String[] updatedIds) {
        assertTrue(ChartService.deleteChart(issue, info.getId(), updatedIds));
        assertNull(ChartDAOImpl.getByIssueNumber(info.getId(), issue));
        assertTrue(PositionDAOImpl.getSongIdsForIssue(info.getId(), issue).isEmpty());
    }

    private void form(String[] updatedIds) {
        String[] names = new String[updatedIds.length];
        String[] artists = new String[updatedIds.length];
        Arrays.fill(names, "Unexpected replacement");
        Arrays.fill(artists, "Test artist");
        ChartService.formChart(info.getId(), updatedIds, names, artists);
    }

    private List<Position> positions(Song song) {
        return PositionDAOImpl.getPositionsForSong(info.getId(), song.getId());
    }

    private void assertSongStats(Song original, int weeks, int peak) {
        Song stored = SongDAOImpl.getById(original.getId());
        assertNotNull(stored);
        assertEquals(original.getId(), stored.getId());
        assertEquals(original.getName(), stored.getName());
        assertEquals(weeks, stored.getWeeks());
        assertEquals(positions(original).size(), stored.getWeeks());
        assertEquals(peak, stored.getPeak());
    }

    private void assertHistoricalPositions(Song song) {
        assertEquals(Arrays.asList(8, 5), Arrays.asList(
                PositionDAOImpl.getPositionForSong(info.getId(), song.getId(), 1).getPosition(),
                PositionDAOImpl.getPositionForSong(info.getId(), song.getId(), 2).getPosition()));
    }

    private void assertCurrentPosition(Song song, int issue, int rank) {
        Chart current = ChartDAOImpl.getByIssueNumber(info.getId(), issue);
        assertNotNull(current);
        Position position = PositionDAOImpl.getPositionForSong(info.getId(), song.getId(), issue);
        assertNotNull(position);
        assertEquals(current.getId(), position.getPk().getChart().getId());
        assertEquals(song.getId(), position.getPk().getSong().getId());
        assertEquals(rank, position.getPosition());
    }

    private void assertAbsentFromCurrentChart(Song song, int issue) {
        assertNotNull(ChartDAOImpl.getByIssueNumber(info.getId(), issue));
        assertNull(PositionDAOImpl.getPositionForSong(info.getId(), song.getId(), issue));
        assertEquals(Arrays.asList(filler.getId()),
                PositionDAOImpl.getSongIdsForIssue(info.getId(), issue));
    }
}
