package tvz.labos.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tvz.labos.models.*;
import tvz.labos.repositories.JdbcExpenseRepository;
import tvz.labos.repositories.JdbcWalletRepository;
import tvz.labos.utils.SecurityUtils;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
@RequestMapping("/expenses")
@SessionAttributes({"wallet", "expenseTypes", "paymentTypes"})
public class ExpenseController {
    final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    @Autowired
    private JdbcExpenseRepository expenseRepository;

    @Autowired
    private JdbcWalletRepository walletRepository;

    @ModelAttribute("wallet")
    public Wallet getWallet() {
        String owner = SecurityUtils.getUsername();
        if (owner.isEmpty()) {
            logger.warn("No user is logged in. Wallet creation aborted.");
            return null;
        }

        Wallet wallet = walletRepository.findOneByOwner(owner);

        if (wallet == null) {
            wallet = new Wallet();
            wallet.setOwner(owner);
            wallet.setName(owner + "'s wallet");
            wallet.setCurrentAmount(0);
            wallet = walletRepository.save(wallet);
        }

        logger.info("New wallet created.");
        return wallet;
    }

    public void updateWallet(Wallet wallet) {
        ArrayList<Expense> expenses = new ArrayList<>();
        wallet.setExpenses(new ArrayList<>());
        expenseRepository.findAllByWalletId(wallet.getId()).forEach(expenses::add);
        expenses.forEach(wallet::addExpense);
        wallet.updateAmount();
    }

    public void addExpenseToWallet(Expense expense, Wallet wallet) {
        wallet.addExpense(expense);
        wallet.updateAmount();
    }

    @ModelAttribute("expenseTypes")
    public ExpenseType[] getDropdownList() {
        logger.debug("Preparing types dropdown list.");
        return ExpenseType.values();
    }

    @GetMapping("/resetWallet")
    public String resetWallet(SessionStatus status) {
        status.setComplete();
        return "redirect:/expenses/new";
    }

    @GetMapping("/new")
    public String getExpenseForm(Model model) {
        logger.info("Preparing object to fill.");
        model.addAttribute("expense", new Expense());
        logger.info("Form ready for use.");

        return "formPage";
    }

    @PostMapping("/new")
    public String processExpense(@Validated Expense expense,  Errors errors, Model model, @SessionAttribute("wallet") Wallet wallet) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YY"));

        if (errors.hasErrors()) {
            logger.info("Validation of object failed.");
            return "formPage";
        }

        model.addAttribute("expense", expense);
        model.addAttribute("date", date);
        logger.info("Expense object filled with data");

        expenseRepository.save(expense, wallet);
        logger.info("New expense saved.");
        updateWallet(wallet);
        logger.info("Wallet updated.");

        return "resultPage";
    }



//************************************************************
//********* LABOS 2 ******************************************
//************************************************************

    @ModelAttribute("paymentTypes")
    public PaymentType[] getDropdownListPaymentTypes() {
        logger.debug("Preparing types dropdown list.");
        return PaymentType.values();
    }

    public void addPaymentToWallet(Payment payment, Wallet wallet) {
        if (wallet == null) {
            wallet.initializeWallet();
        }
        wallet.addPayment(payment);
        wallet.updateAmount();
    }

    @GetMapping("/newPay")
    public String getPaymentForm(Model model) {
        logger.info("Preparing object to fill.");
        model.addAttribute("payment", new Payment());

        return "paymentPage";
    }

    @PostMapping("/newPay")
    public String processPayment(Payment payment,  Errors errors, Model model, @SessionAttribute("wallet") Wallet wallet) {
        // String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/YY"));

        logger.info("Processing object..." + payment);
        model.addAttribute("payment", payment);
        addPaymentToWallet(payment, wallet);
        logger.info("Wallet updated.");

        return "payResultPage";
    }
}
