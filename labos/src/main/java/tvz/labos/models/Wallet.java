package tvz.labos.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;

    @Column(name = "name")
    public String name;

    @OneToMany(mappedBy = "wallet", fetch = FetchType.EAGER)
    public List<Expense> expenses;

    @Column(name = "currentAmount")
    public int currentAmount;

    @Column(name = "owner")
    public String owner;

    @JsonManagedReference
    public List<Expense> getExpenses() {
        return expenses;
    }

  //  public WalletType type;

  //  public List<Payment> payments;

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

   /* public void addPayment (Payment payment) {
        payments.add(payment);
    }*/

    public void initializeWallet() {
        expenses = new ArrayList<>();
        currentAmount = 0;
    }

    public Wallet() {
       // payments = new ArrayList<>();
        expenses = new ArrayList<>();
        currentAmount = 0;
    }
}
