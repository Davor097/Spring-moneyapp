package tvz.labos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import tvz.labos.models.*;
import tvz.labos.repositories.ExpenseRepository;
import tvz.labos.repositories.JdbcAuthoritiesRepository;
import tvz.labos.repositories.JdbcUserRepository;
import tvz.labos.repositories.WalletRepository;
import tvz.labos.utils.SecurityUtils;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    JdbcAuthoritiesRepository jdbcAuthoritiesRepository;

    @Autowired
    JdbcUserRepository jdbcUserRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    WalletRepository walletRepository;

    @ModelAttribute("expenseTypes")
    public ExpenseType[] getDropdownList() {
        //logger.debug("Preparing types dropdown list.");
        return ExpenseType.values();
    }

    @GetMapping("/home")
    public String home(){
        return "homePage";
    }

    @GetMapping("/deleteExpense")
    public String deleteExpense(@RequestParam("expenseId") Long expenseId) {
        Expense expense = expenseRepository.findOne(expenseId);
        expenseRepository.delete(expense);
        return "redirect:/home";
    }

    @GetMapping("/editExpense")
    public String editExpense(@RequestParam("expenseId") Long expenseId, Model model) {
        Expense expense = expenseRepository.findOne(expenseId);
        model.addAttribute("expense", expense);

        return "formPage";
    }

    /*
    @GetMapping(value = "/addAdmin", params = "name")
    public String addAdmin(@RequestParam(value = "name") String username) {
        Authority authority = jdbcAuthoritiesRepository.findOne(username);
        jdbcAuthoritiesRepository.save(authority);
        return "redirect:adminPage";
    }*/
/*
    @GetMapping(value = "/removeAdmin", params = "name")
    public String removeAdmin(@RequestParam(value = "name") String username) {
        Authority authority = jdbcAuthoritiesRepository.findOne(username);
        jdbcAuthoritiesRepository.delete(authority);
        return "redirect:adminPage";
    }*/
/*
    @GetMapping("/adminPage")
    public String adminPage(Model model) {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Authority> authorities = new ArrayList<>();
        jdbcUserRepository.findAll().forEach(users::add);
        jdbcAuthoritiesRepository.findAll().forEach(authorities::add);
        String currentUser = SecurityUtils.getUsername();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        model.addAttribute("authorities", authorities);

        return "adminPage";
    }*/
}
