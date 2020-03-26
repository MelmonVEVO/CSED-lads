package group.csed.frontend;

import group.csed.frontend.console.ConsoleReader;
import group.csed.frontend.http.ApiRequest;
import group.csed.frontend.http.Status;
import group.csed.frontend.http.models.PeriodData;
import group.csed.frontend.predictions.PeriodPrediction;

import java.util.Date;

public class Frontend {

    private int accountID;
    private boolean loggedIn = false;

    private PeriodData periodData;

    private final ConsoleReader consoleReader;

    public Frontend() {
        this.consoleReader = new ConsoleReader();
        getMainMenuInput();
    }

    private void presentMenu(boolean loggedIn) {
        System.out.println();
        System.out.println("------- Main menu -------");
        if(loggedIn) {
            System.out.println("1. Update period data");
            System.out.println("2. View period data");
            System.out.println("3. See when your next period will be");
        } else {
            System.out.println("1. Login");
            System.out.println("2. Create account");
        }
        System.out.println();
    }

    private void getMainMenuInput() {
        String input;
        while(true) {
            presentMenu(loggedIn);
            input = consoleReader.readLine();
            if(!loggedIn) {
                if(input.equals("1")) {
                    presentLoginPage();
                } else if(input.equals("2")) {
                    presentCreateAccountPage();
                }
            } else {
                if(input.equals("2")) {
                    printPeriodData();
                } else if(input.equals("3")) {
                    printNextPeriodDate();
                }
            }
        }
    }

    private void printNextPeriodDate() {
        if(periodData != null) {
            final Date date = PeriodPrediction.getNextPeriodDate(periodData.getStarted(), periodData.getCycleLength());
            System.out.println(date.toString());
        }
    }

    private void printPeriodData() {
        if(periodData != null) {
            System.out.println("------------");
            System.out.println("Started: " + periodData.getStarted());
            System.out.println("Lasted: " + periodData.getLasted());
            System.out.println("Cycle length: " + periodData.getCycleLength());
            System.out.println("------------");
        } else {
            System.out.println("No data exists");
        }
    }

    private void presentCreateAccountPage() {
        String email, firstName, lastName, password, dob;
        email = getInput("Enter your email");
        firstName = getInput("Enter your first name");
        lastName = getInput("Enter your last name");
        password = getInput("Enter your password");
        dob = getInput("Enter your date-of-birth");

        ApiRequest.createAccount(email, firstName, lastName, password, dob, status -> {
            if(status == Status.OK) {
                System.out.println("Successfully created account!");
            } else {
                System.out.println("An account with that email already exists");
            }
        });
    }

    private void presentLoginPage() {
        String email, password;
        email = getInput("Please enter your email");
        password = getInput("Please enter your password");

        ApiRequest.login(email, password, (s1, id) -> {
            if(s1 == Status.OK) {
                System.out.println("Successfully logged in");
                this.loggedIn = true;
                this.accountID = id;
                ApiRequest.getPeriodData(accountID, (s2, periodData) -> {
                    if(s2 == Status.OK) {
                        this.periodData = periodData;
                    } else {
                        System.out.println("No period data was loaded because no data exists");
                    }
                });
            } else {
                System.out.println("Incorrect email or password");
            }
        });
    }

    private String getInput(String message) {
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