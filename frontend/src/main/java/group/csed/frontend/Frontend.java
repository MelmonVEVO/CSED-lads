package group.csed.frontend;

import group.csed.frontend.http.ApiRequest;
import group.csed.frontend.http.Status;

public class Frontend {

    public Frontend() {
        // LOGIN EXAMPLE
        ApiRequest.login("test@test2.com", "hello", status -> {
            // Successful login
            if(status == Status.OK) {
                System.out.println("Logged in!");
            } else {
                System.out.println("Incorrect email or password");
            }
        });
        // ACCOUNT CREATE EXAMPLE
        ApiRequest.createAccount("test@test.com", "test3", "test3", "test", "2020-02-20", status -> {
            if(status == Status.OK) {
                System.out.println("Successfully created account");
            } else {
                System.out.println("Account with that email already exists");
            }
        });
        // MOOD TRACKING EXAMPLE
        ApiRequest.trackMood(1, "test mood", status -> {
            if(status == Status.OK) {
                System.out.println("Added entry");
            } else {
                System.out.println("Mood already entered for today");
            }
        });
    }

    public static void main(String[] args) {
        new Frontend();
    }
}