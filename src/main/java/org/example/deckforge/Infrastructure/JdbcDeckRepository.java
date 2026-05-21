package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Enums.Decktype;
import org.example.deckforge.Domain.Interface.IDeckRepository;
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
                INSERT INTO Deck
                (name, format, cards)
                Values(?, ?, ?)
                """;

        jdbcTemp.update(sql,
                deck.getName(),
                deck.getFormat(),
                deck.getCards()
                );
    }

    @Override
    public Deck readDeck(Deck deck) {
        String sql = """
                SELECT
                id,
                name,
                format,
                cards
                FROM
                deck
                WHERE
                id = ?
                """;
        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNm) -> {
            Deck d = new Deck();
            d.setId(rs.getInt("id"));
            d.setName(rs.getString("name"));
            d.setFormat(Decktype.valueOf(rs.getString("format")));
                d.setCards(objectMapper.readValue(rs.getString("cards"), new TypeReference<List<Card>>() {}));
            return d;
            }, deck.getId());
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
