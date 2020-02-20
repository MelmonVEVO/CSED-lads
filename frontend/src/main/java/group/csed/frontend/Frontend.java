package group.csed.frontend;

import group.csed.frontend.http.ApiRequest;
import group.csed.frontend.http.Status;

public class Frontend {

    public Frontend() {
        // TESTS LOGIN FUNCTION
        ApiRequest.login("test@test2.com", "hello", status -> {
            // Successful login
            if(status == Status.OK) {
                System.out.println("Logged in!");
            } else {
                System.out.println("Incorrect email or password");
            }
        });
    }

    public static void main(String[] args) {
        new Frontend();
    }
}