package tvz.labos.repositories;

import tvz.labos.models.Expense;
import tvz.labos.models.Wallet;

public interface ExpenseRepository {
    Iterable<Expense> findAll();
    Iterable<Expense> findAllByWalletId(Long id);
    Expense findOne(Long id);
    Expense save(Expense expense, Wallet wallet);
    void delete(Expense expense);
}
