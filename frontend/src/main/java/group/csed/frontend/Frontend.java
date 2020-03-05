package group.csed.frontend;

import group.csed.frontend.console.ConsoleReader;
import group.csed.frontend.http.ApiRequest;
import group.csed.frontend.http.Status;
import group.csed.frontend.http.models.PeriodData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Frontend {

    private final ConsoleReader consoleReader;

    public Frontend() {
        this.consoleReader = new ConsoleReader();
        presentLoginPage();
    }

    private void presentLoginPage() {
        String email, password;
        email = getLoginInput("Please enter your email");
        password = getLoginInput("Please enter your password");

        ApiRequest.login(email, password, (status, id) -> {
            if(status == Status.OK) {
                System.out.println("Successfully logged in");
            } else {
                System.out.println("Incorrect email or password");
            }
        });
        /* 
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
        });*/
        

    }

    public static void main(String[] args) {
        new Frontend();
    }
}