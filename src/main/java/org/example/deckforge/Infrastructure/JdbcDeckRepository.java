package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Repository.IDeckRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.text.Format;

@Repository
public class JdbcDeckRepository implements IDeckRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcDeckRepository(JdbcTemplate jdbcTemp){ this.jdbcTemp = jdbcTemp; }

    @Override
    public void createDeck(Deck deck) {}

    @Override
    public void readDeck(Deck deck) {}

    @Override
    public void updateDeck(int id, Deck deck) {}

    @Override
    public void deleteDeck(int id) {
        String sql = """
                DELETE FROM Deck
                WHERE id = ?
                """;
        jdbcTemp.update(sql, id);
    }

}
