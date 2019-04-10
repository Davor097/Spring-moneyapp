package tvz.labos.repositories;

import tvz.labos.models.Expense;
import tvz.labos.models.Wallet;

public interface ExpenseRepository {
    Iterable<Expense> findAll();
    Expense findOne(Long id);
    Expense save(Expense expense, Wallet wallet);
}
