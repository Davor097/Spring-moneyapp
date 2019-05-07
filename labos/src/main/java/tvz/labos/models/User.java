package tvz.labos.models;

import lombok.Data;

@Data
public class User {
    public String name;
    public String password;
    public int enabled;
}
