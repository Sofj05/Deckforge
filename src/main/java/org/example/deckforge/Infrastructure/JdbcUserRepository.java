package org.example.deckforge.Infrastructure;

import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Repository.IUserRepository;
import org.example.deckforge.Domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserRepository implements IUserRepository {

    private final JdbcTemplate jdbcTemp;

    public JdbcUserRepository(JdbcTemplate jdbcTemp) {
        this.jdbcTemp = jdbcTemp;
    }


    @Override
    public void createUser(User user) {
        String sql = """
                INSERT INTO user (username, email, passwordhash, role)
                VALUES (?,?,?,?)
        """;

        jdbcTemp.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole().toString()
        );
    }

    // Har lavet en version af readUser, er dog usikker på om du vil have nogle ændringer eller ej - Sofie
    @Override
    public User readUser(User user) {
        String sql = """
                SELECT user_id, username, email, passwordhash, role 
                FROM users
                WHERE user_id = ?
        """;

        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNUM) -> {
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("passwordhash"));
                u.setRole(Role.valueOf(rs.getString("role")));
                return u;
            }, user.getId());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getUserByUsername(String username){
        String sql = """
                SELECT
                    user_id, 
                    username, 
                    email, 
                    passwordhash, 
                    role
                FROM user
                WHERE username = ?
                """;

        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("passwordhash"));
                u.setRole(Role.valueOf(rs.getString("role")));

                return u;
            },  username);
        } catch (EmptyResultDataAccessException e){
            return null;
        }

    }


    @Override
    public void updateUser(int id, User user){
        String sql = """
                UPDATE user
                SET 
                    username = ?,
                    email = ?,
                    passwordhash = ?,
                    role = ?
                WHERE     
                    user_id = ?
                """;

        jdbcTemp.update(sql,
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getRole().name(),
                id
        );

    }

    @Override
    public void deleteUser(int id){
        String sql = """
                DELETE FROM User
                WHERE id = ?
                """;
        jdbcTemp.update(sql, id);
    }

    @Override
    public User getUserById(int id){
        String sql = """
                SELECT user_id,username,email,passwordhash,role
                FROM user
                WHERE user_id = ?
        """;

        try {
            return jdbcTemp.queryForObject(sql, (rs, rowNUM) -> {
                User u = new User();
                u.setId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPasswordHash(rs.getString("passwordhash"));
                u.setRole(Role.valueOf(rs.getString("role")));
                return u;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }
}

