package tvz.labos.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tvz.labos.models.Wallet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcWalletRepository implements WalletRepository{
     public final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);

     private final JdbcTemplate jdbc;
     private final SimpleJdbcInsert walletInserter;

     public JdbcWalletRepository(JdbcTemplate jdbc) {
         this.jdbc = jdbc;
         this.walletInserter = new SimpleJdbcInsert(this.jdbc).withTableName("wallet").usingGeneratedKeyColumns("id");
     }

     @Override
     public Iterable<Wallet> findAll() {
         try {
             return jdbc.query("select id, currentAmount, name, owner from wallet", this::mapRowToWallet);
         } catch (EmptyResultDataAccessException ex) {
             logger.info("Finding by id returned 0 results, returning null instead");
             return null;
         }
     }

     @Override
     public Wallet findOne(Long id) {
         try {
             return jdbc.queryForObject("select id, currentAmount, name, owner from wallet where id = ?", this::mapRowToWallet, id);
         } catch (EmptyResultDataAccessException ex) {
             logger.info("Finding by id returned 0 results, returning null instead");
             return null;
         }
     }

     @Override
     public Wallet findOneByOwner(String owner) {
         try {
             return jdbc.queryForObject("select id, currentAmount, name, owner from wallet where owner = ?", this::mapRowToWallet, owner);
         } catch (EmptyResultDataAccessException ex) {
             logger.info("Finding by owner returned 0 results, returning null instead");
             return null;
         }
     }

     @Override
     public Wallet save(Wallet wallet) {
         wallet.setId(saveWalletDetails(wallet));
         return wallet;
     }
     private Wallet mapRowToWallet(ResultSet rs, int rowNum) {
         Wallet wallet = new Wallet();
         try {
             wallet.setId(rs.getLong("id"));
             wallet.setCurrentAmount(rs.getInt("currentAmount"));
             wallet.setName(rs.getString("name"));
             wallet.setOwner(rs.getString("owner"));
         } catch (SQLException ex) {
             logger.error("Mapping row to wallet failed, bad data");
         }
         return wallet;
     }

    @Override
    public void delete(Wallet wallet) {

    }

    private long saveWalletDetails(Wallet wallet) {
         Map<String, Object> values = new HashMap<>();
         values.put("currentAmount", wallet.getCurrentAmount());
         values.put("name", wallet.getName());
         values.put("owner", wallet.getOwner());
         return walletInserter.executeAndReturnKey(values).longValue();
     }
}
