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
    }

    private String getLoginInput(String message) {
        String input;
        while(true) {
            System.out.println(message);
            input = consoleReader.readLine();
            if(input != null && !input.equals("")) {
                return input;
            }
        }
    }

    public static void main(String[] args) {
        new Frontend();
    }
}