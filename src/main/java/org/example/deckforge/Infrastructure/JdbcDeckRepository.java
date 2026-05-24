package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Decktype;
import org.example.deckforge.Domain.Enums.Rarity;
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
    public List<Deck> getDecksByUser(User user) {
        String sql = """
                SELECT
                deck_id,
                deck_name,
                format,
                user_id
            FROM Deck
            WHERE user_id = ?;
                """;
        try {
            return jdbcTemp.query(sql, (rs, rowNm) -> {
                Deck d = new Deck();
                d.setId(rs.getInt("deck_id"));
                d.setName(rs.getString("deck_name"));
                d.setFormat(Decktype.valueOf(rs.getString("format")));
                d.setUserId(rs.getInt("user_id"));
                return d;
            }, user.getId());
        }  catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Deck getDeckById(int id) {
        String sql = """
                SELECT
                d.deck_id,
                d.deck_name,
                d.format,
                d.user_id
            FROM Deck d
            WHERE d.deck_id = ?;
                """;
        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNm) -> {
                Deck d = new Deck();
                d.setId(rs.getInt("deck_id"));
                d.setName(rs.getString("deck_name"));
                d.setFormat(Decktype.valueOf(rs.getString("format")));
                d.setUserId(rs.getInt("user_id"));
                return d;
            }, id);
        }  catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Card> getCardsInDeck(int deckId) {
        String sql = """
                SELECT c.card_id, c.card_name, c.cardType, c.mana, c.nameOfSet, c.rarity, 
                       c.ruleText, c.image, c.ability, dc.quantity
                FROM Card c
                JOIN DeckCard dc ON c.card_id = dc.card_id
                WHERE dc.deck_id = ?
                """;
        return jdbcTemp.query(sql, (rs, rowNum) -> {
            Card card = new Card();
            card.setId(rs.getInt("card_id"));
            card.setName(rs.getString("card_name"));
            card.setCardtype(Cardtype.valueOf(rs.getString("cardType")));
            card.setMana(rs.getString("mana"));
            card.setNameOfSet(rs.getString("nameOfSet"));
            card.setRarity(Rarity.valueOf(rs.getString("rarity")));
            card.setRuleText(rs.getString("ruleText"));
            card.setImage(rs.getString("image"));
            card.setAbility(rs.getString("ability"));
            card.setQuantity(rs.getInt("quantity"));
            return card;
        }, deckId);
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
