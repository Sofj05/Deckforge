package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Rarity;
import org.example.deckforge.Domain.Interface.ICardRepository;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class JdbcCardRepository implements ICardRepository {

    private final JdbcTemplate jdbcTemp;
    @Autowired
    public JdbcCardRepository(JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    private final RowMapper<Card> cardRowMapper = (rs, rowNum) -> {

        Card card = new Card();

        card.setId(rs.getInt("card_id"));
        card.setName((rs.getString("card_name")));
        card.setCardtype((Cardtype.valueOf(rs.getString("cardType"))));
        card.setMana(rs.getString("mana"));
        card.setNameOfSet((rs.getString("nameOfSet")));
        card.setRarity(Rarity.valueOf(rs.getString("rarity")));
        card.setRuleText(rs.getString("ruleText"));
        card.setImage(rs.getString("image"));
        card.setAbility(rs.getString("ability"));
        card.setQuantity(rs.getInt("quantity"));

        return  card;
    };
    private final RowMapper<Card> cardRowMapperWOQuantity = (rs, rowNum) -> {

        Card card = new Card();

        card.setId(rs.getInt("card_id"));
        card.setName((rs.getString("card_name")));
        card.setCardtype((Cardtype.valueOf(rs.getString("cardType"))));
        card.setMana(rs.getString("mana"));
        card.setNameOfSet((rs.getString("nameOfSet")));
        card.setRarity(Rarity.valueOf(rs.getString("rarity")));
        card.setRuleText(rs.getString("ruleText"));
        card.setImage(rs.getString("image"));
        card.setAbility(rs.getString("ability"));

        return  card;
    };

    @Override
    public List<Card> getAllCards(){
        String sql = """
                Select *
                from card
                ORDER BY card_id ASC
                """;
            return jdbcTemp.query(sql, cardRowMapperWOQuantity);

    }
    @Override
    public List<Card> getCardsByUser(User user){
        String sql = """
            SELECT
                c.card_id,
                c.card_name,
                c.cardType,
                c.mana,
                c.nameOfSet,
                c.rarity,
                c.ruleText,
                c.image,
                c.ability,
                uc.quantity
            FROM UserCollection uc
            JOIN User u
                ON uc.user_id = u.user_id
            JOIN card c
                ON uc.card_id = c.card_id
            WHERE u.user_id = ?;
            """;

            return jdbcTemp.query(sql, cardRowMapper,user.getId());

    };


    @Override
    public void addNewCard(Card card){
        String sql = """
                INSERT INTO card
                (card_name, cardType, mana, nameOfSet, rarity, ruleText, image, ability)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?) 
                """;

        jdbcTemp.update(sql,
                card.getName(),
                card.getCardtype(),
                card.getMana(),
                card.getNameOfSet(),
                card.getRarity().name(),
                card.getRuleText(),
                card.getImage(),
                card.getAbility()
        );
    }


    @Override
    public void addCardToUserCollection(int userId, int cardId, int quantity) {

        String sql = """
        INSERT INTO UserCollection (user_id, card_id, quantity)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE quantity = quantity + ?
        """;

        jdbcTemp.update(sql, userId, cardId, quantity, quantity);
    }

    @Override
    public Card readCard(Card card){
        String sql = """
                SELECT
                id,
                card_name,
                cardType,
                mana,
                nameOfSet,
                rarity,
                ruleText,
                image,
                ability
                FROM
                card
                WHERE
                id = ?
        """;

        try {
            return jdbcTemp.queryForObject(sql, cardRowMapper,card.getId());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
        }

    @Override
    public void deleteCard(int id){
        String sql = """
                DELETE FROM card
                WHERE id = ?
                """;
        jdbcTemp.update(sql, id);
    }

    @Override
    public List<Card> getFirstThreeCards() {
        String sql = """
                SELECT *
                FROM card
                ORDER BY card_id ASC
                LIMIT 3
                """;

            return jdbcTemp.query(sql, cardRowMapperWOQuantity);

    }



}
