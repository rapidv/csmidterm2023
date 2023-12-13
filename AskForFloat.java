import java.util.Scanner;

public class AskForFloat {

    static Scanner scanner = new Scanner(System.in);

    public static float Ask(String Question) {
        float Value;

        while (true) { // loops until valid input is entered
            System.out.println(Question);
            if (scanner.hasNextFloat()) {
                Value = scanner.nextFloat();
                return Value;
            } else {
                System.out.println("ERROR: Invalid data type. Please try again.");
                scanner.next(); // "clears" scanner
            }
        }
    }
}
