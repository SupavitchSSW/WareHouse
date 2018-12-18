package user;

import javax.persistence.Entity;

@Entity
public class Manager extends User {
    public Manager(String username, String password, String firstname, String surname, String phoneNumber) {
        super(username, password, firstname, surname, phoneNumber,"Manager");
    }
}
