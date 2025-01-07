package com.example.demo.respository.clickhouse;


import com.example.demo.entity.clickhouse.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS user (
                    id UInt64,
                    name String,
                    age UInt8,
                    created_at DateTime
                ) ENGINE = MergeTree()
                ORDER BY id
                """;
        jdbcTemplate.execute(sql);
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO user (id, name, age, created_at) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getAge(), user.getCreatedAt());
    }

    public List<User> getAllUsers() {
        String sql = "SELECT id, name, age, created_at FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
            user.setCreatedAt(rs.getString("created_at"));
            return user;
        });
    }
}
