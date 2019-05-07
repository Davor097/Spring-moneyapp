package tvz.labos.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tvz.labos.models.Authority;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcAuthoritiesRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert autorityInserter;

    public JdbcAuthoritiesRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.autorityInserter = new SimpleJdbcInsert(this.jdbc).withTableName("authorities");
    }

    public Iterable<Authority> findAll() {
        return jdbc.query("select username, authority from authorities", this::mapRowToAuthority);
    }

    public Authority findOne(String name) {
        return jdbc.queryForObject("select username, authority from authorities where username = ?", this::mapRowToAuthority, name);
    }

    public Authority save(Authority authority) {
        saveUserDetails(authority);
        return authority;
    }

    public void delete(Authority authority) {
       // return jdbc.query("delete from authorities where username = ? and authority = ?", authority.getUsername());
    }

    private void saveUserDetails(Authority authority) {
        Map<String, Object> values = new HashMap<>();
        values.put("username", authority.getUsername());
        values.put("authority", authority.getAuthority());
        autorityInserter.execute(values);
    }

    private Authority mapRowToAuthority(ResultSet rs, int rowNum) {
        Authority authority = new Authority();
        try {
            authority.setUsername(rs.getString("username"));
            authority.setAuthority(rs.getString("authority"));
        } catch (Exception ex) {

        }
        return authority;
    }
}
