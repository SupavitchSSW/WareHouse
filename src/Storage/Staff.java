package Storage;

import javax.persistence.Entity;

@Entity
public class Staff extends User {
    public Staff(String username, String password,String firstname, String surname, String phoneNumber) {
        super(username,  password, firstname,  surname,  phoneNumber,"Staff");
    }
}
