package tvz.labos.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tvz.labos.models.Wallet;
import tvz.labos.repositories.ExpenseRepository;
import tvz.labos.repositories.WalletRepository;

@RestController
@RequestMapping(path = "/api/wallet", produces = "application/json")
@CrossOrigin
public class WalletRestController {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    public WalletRestController(ExpenseRepository expenseRepository, WalletRepository walletRepository) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
    }

    @GetMapping
    public Iterable<Wallet> findAll() {
        return walletRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Wallet> findOne(@PathVariable Long id) {
        Wallet wallet = walletRepository.findOne(id);
        if (wallet != null) {
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public Wallet save(@RequestBody Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @PutMapping("/{id}")
    public Wallet update(@RequestBody Wallet wallet) {
        return save(wallet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Wallet wallet = walletRepository.findOne(id);
        walletRepository.delete(wallet);
    }
}
