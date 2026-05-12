package org.example.deckforge.Infrastructure;


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
}
