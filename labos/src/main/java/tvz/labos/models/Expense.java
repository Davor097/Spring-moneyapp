package tvz.labos.models;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Expense {
    private long id;
    private long walletId;
    private LocalDateTime date;
    @Valid
    @NotEmpty(message = "Niste unijeli naziv proizvoda")
    @Size(min = 3, max = 75, message = "Naziv treba imati između 3 i 75 znakova")
    private String name;

    @Valid
    @NotNull(message = "Niste unijeli vrijednost transakcije")
    @DecimalMin(value = "1", message = "Minimalna vrijednost je 1")
    private int amount;

    @Valid
    @NotNull(message = "Niste odabrali tip")
    private ExpenseType type;

}
