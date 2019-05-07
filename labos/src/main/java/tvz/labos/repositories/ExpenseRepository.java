package tvz.labos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tvz.labos.models.Expense;
import tvz.labos.models.Wallet;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAll();
    List<Expense> findAllByWallet(Wallet wallet);
    Expense findFirstById(Long id);
    List<Expense> findAllByNameLike(String name);

   /* Iterable<Expense> findAll();
    Iterable<Expense> findAllByWalletId(Long id);
    Expense findOne(Long id);
    Expense save(Expense expense, Wallet wallet);
    void delete(Expense expense);*/
}
