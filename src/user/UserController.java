package user;


import connectionDB.serviceDB;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class UserController {
    private serviceDB warehouse;
    private User currentUser;

    public UserController(serviceDB warehouse,User currentUser) {
        this.warehouse = warehouse;
        this.currentUser = currentUser;
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

        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        TextField searchBox = (TextField) scene.lookup("#searchBox");




}
