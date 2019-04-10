package tvz.labos.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tvz.labos.models.Expense;
import tvz.labos.models.ExpenseType;
import tvz.labos.models.Wallet;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcExpenseRepository implements ExpenseRepository {

    public final Logger logger = LoggerFactory.getLogger(JdbcExpenseRepository.class);


    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert expenseInserter;

    public JdbcExpenseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.expenseInserter = new SimpleJdbcInsert(this.jdbc).withTableName("expenses").usingGeneratedKeyColumns("id");
    }

    @Override
    public Iterable<Expense> findAll() {
        return jdbc.query("select id, name, amount, type, date, walletId from expenses", this::mapRowToExpense);
    }

    @Override
    public Expense findOne(Long id) {
        return jdbc.queryForObject("select id, name, amount, type, date, walletId from expenses where id = ?", this::mapRowToExpense, id);
    }

    public Iterable<Expense> findAllByWalletId(Long id) {
        return jdbc.query("select id, name, amount, type, date, walletId from expenses where walletId = ?", this::mapRowToExpense, id);
    }

    @Override
    public Expense save(Expense expense, Wallet wallet) {
        expense.setDate(LocalDateTime.now());
        expense.setId(saveExpenseDetails(expense, wallet));
        return expense;
    }

    private Expense mapRowToExpense(ResultSet rs, int rowNum) {
        Expense expense = new Expense();
        try {
            expense.setId(rs.getLong("id"));
            expense.setName(rs.getString("name"));
            expense.setAmount(rs.getInt("amount"));
            expense.setType(ExpenseType.valueOf(rs.getString("type")));
            expense.setDate(rs.getTimestamp("date").toLocalDateTime());
            expense.setWalletId(rs.getLong("walletId"));
        } catch (Exception ex) {
            logger.error("Error while mapping Expenses");
        }
        return expense;
    }

    private long saveExpenseDetails(Expense expense, Wallet wallet) {
        Map<String, Object> values = new HashMap<>();
        values.put("name", expense.getName());
        values.put("amount", expense.getAmount());
        values.put("type", expense.getType());
        values.put("walletId", wallet.getId());
        values.put("date", Timestamp.valueOf(expense.getDate()));
        return expenseInserter.executeAndReturnKey(values).longValue();
    }
}
