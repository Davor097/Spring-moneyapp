package tvz.labos.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Wallet {
    public long id;
    public String name;
    public ArrayList<Expense> expenses;
    public ArrayList<Payment> payments;
    public int currentAmount;
    public WalletType type;
    public String owner;

    public void updateAmount() {
        resetWalletAmount();/*
        for (Payment p : payments) {
            currentAmount += p.amount;
        }*/

        for (Expense e: expenses) {
            currentAmount -= e.getAmount();
        }
    }
    public void resetWalletAmount() {
        currentAmount = 0;
    }

    public void addExpense (Expense expense) {
        expenses.add(expense);
    }

    public void addPayment (Payment payment) {
        payments.add(payment);
    }

    public void initializeWallet() {
        expenses = new ArrayList<>();
        currentAmount = 0;
    }

    public Wallet() {
        payments = new ArrayList<>();
        expenses = new ArrayList<>();
        currentAmount = 0;
    }
}
