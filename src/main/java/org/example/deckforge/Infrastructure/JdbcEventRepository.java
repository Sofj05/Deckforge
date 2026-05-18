package org.example.deckforge.Infrastructure;
import org.example.deckforge.Domain.Enums.Decktype  ;
import org.example.deckforge.Domain.Enums.Status;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.Repository.IEventRepository;
import org.example.deckforge.Domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcEventRepository implements IEventRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcEventRepository(JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    @Override
    public void createEvent(Event event) {
        String sql = """
                INSERT INTO Event
                (name, organizer, date, time, ruleText, maxParticipants, format, status)
                Values(?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemp.update(sql,
                event.getName(),
                event.getOrganizer().getId(),
                event.getDate(),
                event.getTime(),
                event.getRules(),
                event.getMaxParticipants(),
                event.getFormat().name(),
                event.getStatus()
                );
    }

    @Override
    public Event readEvent(Event event) {
        String sql = """
                SELECT 
                    id, 
                    name, 
                    organizer, 
                    date, 
                    time, 
                    ruleText, 
                    maxParticipants, 
                    format, 
                    status
                FROM 
                    event
                WHERE 
                    id = ?
                """;

        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNum) -> {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));

                User organizer = new User();
                organizer.setId(rs.getInt("organizer"));
                e.setOrganizer(organizer);

                e.setDate(rs.getDate("date").toLocalDate());
                e.setTime(rs.getTime("time").toLocalTime());
                e.setRules(rs.getString("ruleText"));
                e.setMaxParticipants(rs.getInt("maxParticipants"));
                e.setFormat(Decktype.valueOf(rs.getString("format")));
                e.setStatus(Status.valueOf(rs.getString("status")));
                return e;
            }, event.getId());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Event> getEventsByStatus(Status status){
        String sql = """
                SELECT
                    event_id,
                    event_name,
                    organizer,
                    date,
                    time,
                    ruleText,
                    maxParticipants,
                    format
                FROM
                    Event
                WHERE
                    status = ?
                """;

        try {
            return jdbcTemp.query(sql, (rs, rowNum) -> {
                Event e = new Event();
                e.setId(rs.getInt("event_id"));
                e.setName(rs.getString("event_name"));

                User organizer = new User();
                organizer.setId(rs.getInt("organizer"));

                e.setOrganizer(organizer);

                e.setDate(rs.getDate("date").toLocalDate());
                e.setTime(rs.getTime("time").toLocalTime());
                e.setRules(rs.getString("ruleText"));
                e.setMaxParticipants(rs.getInt("maxParticipants"));
                e.setFormat(Decktype.valueOf(rs.getString("format")));
                e.setStatus(Status.valueOf(rs.getString("status")));

                return e;
            }, status.name());
        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    @Override
    public void updateEvent(int id, Event event) {
        String sql = """
                UPDATE event
                SET
                name = ?,
                organizer = ?,
                date = ?,
                tine = ?,
                rules = ?,
                maxParticipants = ?,
                format = ?,
                status = ?
                WHERE
                id = ?
                """;

        jdbcTemp.update(sql,
                event.getName(),
                event.getOrganizer(),
                event.getDate(),
                event.getTime(),
                event.getMaxParticipants(),
                event.getFormat().name(),
                event.getStatus().name(),
                id
                );
    }

    @Override
    public void deleteEvent(int id) {
        String sql = """
                DELETE FROM Event
                WHERE id = ?
                """;
        jdbcTemp.update(sql, id);
    }

    @Override
    public int getParticipationCount(Event event){
        String sql = """
                SELECT COUNT(event_id) 
                FROM Participants
                WHERE event_id = ?
        """;

        try {
            return jdbcTemp.queryForObject(sql, Integer.class, event.getId());
        } catch (DataAccessException e){
            return 0;
        }

    }
    public Event getEventById(int id){
        String sql = """
                SELECT 
                    id, 
                    name, 
                    organizer, 
                    date, 
                    time, 
                    ruleText, 
                    maxParticipants, 
                    format, 
                    status
                FROM 
                    event
                WHERE 
                    id = ?
                """;

        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNum) -> {
Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));

                User organizer = new User();
                organizer.setId(rs.getInt("organizer"));
                e.setOrganizer(organizer);

                e.setDate(rs.getDate("date").toLocalDate());
                e.setTime(rs.getTime("time").toLocalTime());
                e.setRules(rs.getString("ruleText"));
                e.setMaxParticipants(rs.getInt("maxParticipants"));
                e.setFormat(Decktype.valueOf(rs.getString("format")));
                e.setStatus(Status.valueOf(rs.getString("status")));
                return e;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
