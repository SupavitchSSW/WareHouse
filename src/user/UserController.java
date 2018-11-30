package user;


import connectionDB.serviceDB;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.List;

public class UserController {
    private serviceDB warehouse;
    private User currentUser;

    public UserController(serviceDB warehouse,User currentUser) {
        this.warehouse = warehouse;
        this.currentUser = currentUser;
    }

    public List<User> getAllUser() {
        return warehouse.getAllUser();
    }

    public boolean login(String username,String password){
        User u = warehouse.authen(username,password);
        if (u != null) {
//            (TODO) move this set  current user to warehouse system class
//            currentUser = database.authen(checkUser,checkPw);
//            currentUser.setFirstname(u.getFirstname());
//            currentUser.setPhoneNumber(u.getPhoneNumber());
//            currentUser.setSurname(u.getSurname());
//            currentUser.setRole(u.getRole());
//            currentUser.setUsername(u.getUsername());
//            currentUser.setPassword(u.getPassword());
//            currentUser.setId(u.getId());
            System.out.println("CCC"+u.toString());
            return true;
        }
        else {

            return false;
        }
    }

    public void signup(String username,String password,String rolesel,String firstname,String surname,String phonenum){
//        (TODO) create staff in database

    }

    public void changePassword(String password){

    }

    public void changeUserInfo(String name,String surname,String phonenumber){

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
