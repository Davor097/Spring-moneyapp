package tvz.labos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tvz.labos.models.Expense;
import tvz.labos.models.Wallet;
import tvz.labos.repositories.ExpenseRepository;
import tvz.labos.repositories.WalletRepository;
import tvz.labos.utils.SecurityUtils;

@RestController
@RequestMapping(path = "/api/expense", produces = "application/json")
@CrossOrigin
public class ExpenseRestController {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public ExpenseRestController(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Expense> findAll() {
        return expenseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findOne(@PathVariable Long id) {
        Expense expense = expenseRepository.findOne(id);
        if (expense != null) {
            return new ResponseEntity<>(expense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public Expense save(@RequestBody Expense expense) {
        Wallet userWallet = walletRepository.findOneByOwner(SecurityUtils.getUsername());
        if (userWallet != null) {
            return expenseRepository.save(expense, userWallet);
        } else {
            return null;
        }
    }

    @PutMapping("/{name}")
    public Expense update(@RequestBody Expense expense) {
        return save(expense);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Expense expense = expenseRepository.findOne(id);
        expenseRepository.delete(expense);
    }
}
