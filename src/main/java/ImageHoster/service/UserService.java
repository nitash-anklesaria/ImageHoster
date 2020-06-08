package ImageHoster.service;

import ImageHoster.model.User;
import ImageHoster.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Call the registerUser() method in the UserRepository class to persist the user record in the database
    public void registerUser(User newUser) {
        userRepository.registerUser(newUser);
    }

    //Since we did not have any user in the database, therefore the user with username 'upgrad' and password 'password' was hard-coded
    //This method returned true if the username was 'upgrad' and password is 'password'
    //But now let us change the implementation of this method
    //This method receives the User type object
    //Calls the checkUser() method in the Repository passing the username and password which checks the username and password in the database
    //The Repository returns User type object if user with entered username and password exists in the database
    //Else returns null
    public User login(User user) {
        User existingUser = userRepository.checkUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return existingUser;
        } else {
            return null;
        }
    }

/*  This method checks the password entered by the user while registration, if it is compliant with the password policy of at-least 1 alphabet,
    1 number and 1 special character. We make use of a regular expression and an inbuilt java function to match regular expressions.
    If the password matches the criteria then it returns true else, it returns false*/
    public boolean checkPasswordStrength(String password) {

        String regEx = "((?=.*[A-Z])|(?=.*[a-z]))(?=.*[0-9])(?=.*\\W+).*$";

        if(Pattern.matches(regEx,password)){
            return true;
        }
        else {
            return false;
        }
    }
}
