package group.csed.frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Frontend {

    private final BufferedReader consoleReader;

    public Frontend() {
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private void login() {
        System.out.println("Please enter email:");

    }

    private String readConsole() {
        try {
            return consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        new Frontend();
    }
}