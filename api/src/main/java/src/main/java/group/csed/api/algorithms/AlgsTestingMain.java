package src.main.java.group.csed.api.algorithms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AlgsTestingMain {
	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);
		DateFormat df = new SimpleDateFormat("DD / MM / YYYY");
		boolean dateCorrect = false;
		while (!dateCorrect) { // keeps asking user until an acceptable date is added
			System.out.println("Please input the period start date in the format DD / MM / YYYY");
			String userDate = userInput.nextLine();
			try {
				Date pStart = df.parse(userDate); // period start date
				dateCorrect = true;
			}
			catch (ParseException e) {
				System.out.println("Format wasn't followed. Please try again");
			}
		}
		System.out.println("Please input the length in days of the period");
		int pLength = Integer.parseInt(userInput.nextLine()); // period length (in days)
		System.out.println("Please input the length in days of the cycle");
		int cLength = Integer.parseInt(userInput.nextLine()); // cycle length (in days)
		System.out.println("Data inputted!");
	}

}