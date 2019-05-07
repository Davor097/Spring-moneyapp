package tvz.labos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tvz.labos.models.User;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcUserRepository {

    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert userInserter;

    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.userInserter = new SimpleJdbcInsert(this.jdbc).withTableName("user");
    }

    public Iterable<User> findAll() {
        return jdbc.query("select username, password, enabled from user", this::mapRowToUser);
    }

    public User findOne(String name) {
        return jdbc.queryForObject("select username, password, enabled from user where username = ?", this::mapRowToUser, name);
    }

    public User save(User user) {
        saveUserDetails(user);
        return user;
    }

    private void saveUserDetails(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("username", user.getName());
        values.put("password", user.getPassword());
        values.put("enabled", user.getEnabled());
        userInserter.execute(values);
    }

    public User update(User user) {
        jdbc.update("update user set username=?, password=? where username=? ", user.getName(), user.getPassword(), user.getName());
        return user;
    }

    public void delete(String name) {
        jdbc.update("delete from user where username=?", name);
    }

    private User mapRowToUser(ResultSet rs, int rowNum) {
        User user = new User();
        try {
            user.setName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getInt("enabled"));
        } catch (Exception ex) {

        }
        return user;
    }
}
