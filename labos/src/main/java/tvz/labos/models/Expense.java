package tvz.labos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "expense")
@NoArgsConstructor
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "walletId")
    private Wallet wallet;

   // private long walletId;

    @Column(name = "date")
    private LocalDateTime date;

    @Valid
    @NotEmpty(message = "Niste unijeli naziv proizvoda")
    @Size(min = 3, max = 75, message = "Naziv treba imati između 3 i 75 znakova")
    @Column(name = "name")
    private String name;

    @Valid
    @NotNull(message = "Niste unijeli vrijednost transakcije")
    @DecimalMin(value = "1", message = "Minimalna vrijednost je 1")
    @Column(name = "amount")
    private int amount;

    @Valid
    @NotNull(message = "Niste odabrali tip")
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ExpenseType type;

    @PrePersist
    public void prePersist() {
        date = LocalDateTime.now();
    }

    @JsonBackReference
    public Wallet getWallet() {
        return wallet;
    }

}
