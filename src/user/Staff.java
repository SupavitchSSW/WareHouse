package user;

public class Staff extends User {
    public Staff(String username, String password,String firstname, String surname, String phoneNumber) {
        super(username,  password,"Staff", firstname,  surname,  phoneNumber);
    }
}
