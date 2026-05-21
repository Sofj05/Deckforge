package org.example.deckforge.Infrastructure;
import org.example.deckforge.Domain.Enums.Decktype  ;
import org.example.deckforge.Domain.Enums.Status;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.Interface.IEventRepository;
import org.example.deckforge.Domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcEventRepository implements IEventRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcEventRepository(JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    private final RowMapper<Event> eventRowMapper = (rs, rowNum) -> {

        Event event = new Event();

        event.setId(rs.getInt("event_id"));
        event.setName(rs.getString("event_name"));
        User organizer = new User();
        organizer.setId(rs.getInt("organizer"));
        event.setOrganizer(organizer);
        event.setDate(rs.getDate("date").toLocalDate());
        event.setTime(rs.getTime("time").toLocalTime());
        event.setRules(rs.getString("ruleText"));
        event.setMaxParticipants(rs.getInt("maxParticipants"));
        event.setFormat(Decktype.valueOf(rs.getString("format")));
        event.setStatus(Status.valueOf(rs.getString("status")));

        return event;
    };

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
            return jdbcTemp.queryForObject(sql, eventRowMapper, event.getId());
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
            return jdbcTemp.query(sql, eventRowMapper, status.name());
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
            return jdbcTemp.queryForObject(sql, eventRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    public List<Integer> getParticipantsForEvent(Event event){
        String sql = """
                SELECT 
                    user_id
                FROM 
                    Participants
                WHERE 
                    event_id = ?
        """;
        try {
            return jdbcTemp.queryForList(sql, Integer.class, event.getId());
        } catch (DataAccessException e){
            return null;
        }
    }


    public void addParticipant(Event event, User user){
        String sql = """
                INSERT INTO Participants
                (event_id, user_id)
                VALUES(?, ?)
        """;

        try {
            jdbcTemp.update(sql,
                event.getId(),
                user.getId()
                );
        } catch (EmptyResultDataAccessException e){

     }
    }



}

