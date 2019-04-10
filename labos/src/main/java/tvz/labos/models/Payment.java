package tvz.labos.models;

import lombok.Data;

@Data
public class Payment {
    public String name;
    public PaymentType type;
    public int amount;
}
