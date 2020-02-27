package group.csed.frontend;

import group.csed.frontend.http.ApiRequest;
import group.csed.frontend.http.Status;
import group.csed.frontend.http.models.PeriodData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Frontend {

    private final BufferedReader consoleReader;

    public Frontend() {
        this.consoleReader = new BufferedReader(new InputStreamReader(System.in));

        // GET DATA EXAMPLE
        ApiRequest.getPeriodData(1, (status, response) -> {
            if(status == Status.OK) {
                final PeriodData data = response;
                System.out.println(data.getCycleLength() + " : " + data.getLasted() + " : " + data.getStarted());
            }
        });
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