package Logic;


import Storage.User;
import Storage.WarehouseSystem;

import java.util.List;

public class UserController {
    private WarehouseSystem warehouse;

    public UserController(WarehouseSystem warehouse) {
        this.warehouse = warehouse;
    }

    // login --------------------------------

    public boolean login(String username,String password){
        User u = warehouse.authen(username,password);
        if (u != null) {
              warehouse.setCurrentUser(u);

            System.out.println("CCC"+u.toString());
            return true;
        }
        else {

            return false;
        }
    }


    // signup --------------------------------

    public void signup(String username,String password,String firstname,String surname,String phoneNumber){
//        (TODO) create staff in database
        warehouse.createUserStaff(username,password,firstname,surname,phoneNumber);

    }

    // profile --------------------------------

    public void changePassword(String password){
        warehouse.setPassword(warehouse.getCurrentUser().getId(),password);

    }

    public void changeUserInfo(String name,String surname,String phonenumber){
        warehouse.changeUserInfo(warehouse.getCurrentUser().getId(),name,surname,phonenumber);

    }


    // user search --------------------------------
    public void createManager(String username,String password,String firstname,String surname,String phoneNumber){
        warehouse.createUserManager(username, password, firstname, surname, phoneNumber);
    }

    public List<User> getAllUser(){
        return warehouse.getAllUser();
    }

    public void removeUser(int userId){
        warehouse.removeUser(userId);
    }

    public User getCurrentUser() {
        return warehouse.getCurrentUser();
    }
}
