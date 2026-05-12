package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Mana;
import org.example.deckforge.Domain.Enums.Rarity;
import org.example.deckforge.Domain.Repository.ICardRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class JdbcCardRepository implements ICardRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcCardRepository(JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    private final RowMapper<Card> cardRowMapper = (rs, rowNum) -> {

        Card card = new Card();

        card.setName((rs.getString("card_name")));
        card.setCardtype((Cardtype.valueOf(rs.getString("cardType"))));
        card.setMana(Mana.valueOf(rs.getString("mana")));
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
                """;

        try {
            return jdbcTemp.query(sql, cardRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Card> getCardByUser(){
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
            JOIN Card c
                ON uc.card_id = c.card_id
            WHERE u.user_id = ?;
            """;
        try {
            return jdbcTemp.query(sql, cardRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    };

    @Override
    public void createCard(Card card){};
    @Override
    public void readCard(Card card){};
    @Override
    public void deleteCard(int id){};




}
