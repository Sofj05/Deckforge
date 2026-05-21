package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Enums.Decktype;
import org.example.deckforge.Domain.Interface.IDeckRepository;
import org.example.deckforge.Domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Repository
public class JdbcDeckRepository implements IDeckRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcDeckRepository(JdbcTemplate jdbcTemp){ this.jdbcTemp = jdbcTemp; }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void createDeck(Deck deck) {
        String sql = """
                INSERT INTO Deck (deck_name, format, user_id)
                VALUES (?, ?, ?)
                """;

        jdbcTemp.update(sql,
                deck.getName(),
                deck.getFormat() != null ? deck.getFormat().name() : null,
                deck.getUserId()
        );
    }

    @Override
    public Deck getDeckByUser(User user) {
        String sql = """
                SELECT
                d.deck_id,
                d.deck_name,
                d.format,
                d.user_id
            FROM Deck d
            JOIN User u
                ON d.user_id = u.user_id
            WHERE u.user_id = ?;
                """;
        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNm) -> {
            Deck d = new Deck();
            d.setId(rs.getInt("deck_id"));
            d.setName(rs.getString("deck_name"));
            d.setFormat(Decktype.valueOf(rs.getString("format")));
                d.setCards(objectMapper.readValue(rs.getString("user_id"), new TypeReference<List<Card>>() {}));
            return d;
            }, user.getId());
        }  catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateDeck(int id, Deck deck) {
        String sql = """
                UPDATE Deck
                SET
                name = ?,
                format = ?,
                cards = ?
                WHERE id = ?
                """;

        jdbcTemp.update(sql,
                deck.getName(),
                deck.getFormat(),
                deck.getCards(),
                id
        );
    }

    @Override
    public void deleteDeck(int id) {
        String sql = """
                DELETE FROM Deck
                WHERE id = ?
                """;
        jdbcTemp.update(sql, id);
    }



}
