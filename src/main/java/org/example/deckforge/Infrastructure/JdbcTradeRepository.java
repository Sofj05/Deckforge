package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Interface.ITradeRepository;
import org.example.deckforge.Domain.Trade;
import org.example.deckforge.Domain.Enums.TradeStatus;
import org.example.deckforge.Domain.User;
import org.example.deckforge.Domain.Card;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTradeRepository implements ITradeRepository {

    private final JdbcTemplate jdbc;

    public JdbcTradeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void createTrade(Trade trade) {
        String sql = """
            INSERT INTO Trade (from_user_id, to_user_id, offered_card_id, requested_card_id, status)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbc.update(sql,
                trade.getFromUser().getId(),
                trade.getToUser().getId(),
                trade.getOfferedCard().getId(),
                trade.getRequestedCard().getId(),
                trade.getStatus().name()
        );
    }

    @Override
    public void updateTradeStatus(int tradeId, TradeStatus status) {
        String sql = "UPDATE Trade SET status = ? WHERE trade_id = ?";
        jdbc.update(sql, status.name(), tradeId);
    }

    @Override
    public List<Trade> getIncomingTrades(int userId) {
        String sql = "SELECT * FROM Trade WHERE to_user_id = ?";
        return jdbc.query(sql, tradeMapper, userId);
    }

    @Override
    public List<Trade> getOutgoingTrades(int userId) {
        String sql = "SELECT * FROM Trade WHERE from_user_id = ?";
        return jdbc.query(sql, tradeMapper, userId);
    }

    @Override
    public Trade getTradeById(int id) {
        String sql = "SELECT * FROM Trade WHERE trade_id = ?";
        return jdbc.queryForObject(sql, tradeMapper, id);
    }

    private final RowMapper<Trade> tradeMapper = (rs, rowNum) -> {
        Trade t = new Trade();

        User from = new User();
        from.setId(rs.getInt("from_user_id"));

        User to = new User();
        to.setId(rs.getInt("to_user_id"));

        Card offered = new Card();
        offered.setId(rs.getInt("offered_card_id"));

        Card requested = new Card();
        requested.setId(rs.getInt("requested_card_id"));

        t.setTradeId(rs.getInt("trade_id"));
        t.setFromUser(from);
        t.setToUser(to);
        t.setOfferedCard(offered);
        t.setRequestedCard(requested);
        t.setStatus(TradeStatus.valueOf(rs.getString("status")));

        return t;
    };
}
