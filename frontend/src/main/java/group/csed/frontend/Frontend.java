package group.csed.frontend;

import group.csed.frontend.console.ConsoleReader;
import group.csed.frontend.http.ApiRequest;
import group.csed.frontend.http.Status;

public class Frontend {

    private final ConsoleReader consoleReader;

    public Frontend() {
        this.consoleReader = new ConsoleReader();
        presentLoginPage();
    }

    private void presentMenu() {
        System.out.println("------- Main menu -------");
        System.out.println("1. Update period data");
    }

    private void getMainMenuInput() {
        String input;
        while(true) {
            presentMenu();
            input = consoleReader.readLine();
        }
    }

    private void presentLoginPage() {
        String email, password;
        while(true) {
            email = getLoginInput("Please enter your email");
            password = getLoginInput("Please enter your password");

            ApiRequest.login(email, password, (status, id) -> {
                if(status == Status.OK) {
                    System.out.println("Successfully logged in");
                    getMainMenuInput();
                    return;
                } else {
                    System.out.println("Incorrect email or password");
                }
            });
        }
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