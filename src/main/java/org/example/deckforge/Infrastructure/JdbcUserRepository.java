package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Mana;
import org.example.deckforge.Domain.Enums.Rarity;
import org.example.deckforge.Domain.Repository.IUserRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Repository
public class JdbcUserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcUserRepository(JdbcTemplate jdbcTemp){
        this.jdbcTemp = jdbcTemp;
    }

    private final RowMapper<Card> cardRowMapper = (rs, rowNum) -> {

        Card card = new Card();

        card.setName((rs.getString("card_name")));
        card.setCardtype((Cardtype.valueOf(rs.getString("cardType"))));
        card.setMana(Mana.valueOf(rs.getString("mana")));
        card.set

    }


    @Override
    public List<Card> getAllCards(){
        String sql = """
                Select *
                from card
                """;

        return jdbcTemp.query(sql, (rs, rowNum) -> new Card(
                rs.getString("card_name"),
                Cardtype.valueOf(rs.getString("cardType")),
                Mana.valueOf(rs.getString("mana")),
                rs.getString("SetName"),
                Rarity.valueOf(rs.getString("rarity")),
                rs.getString("ruleText"),
                rs.getString("imaget")
        ) );
    }




}
