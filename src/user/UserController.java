package user;


import connectionDB.serviceDB;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private serviceDB warehouse;
    private User currentUser;

    public UserController(serviceDB warehouse,User currentUser) {
        this.warehouse = warehouse;
        this.currentUser = currentUser;
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
        //(TODO) remove user in warehouse
        warehouse.removeUser(userId);
    }


    public User getCurrentUser() {
        return warehouse.getCurrentUser();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
