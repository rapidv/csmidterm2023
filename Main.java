import java.util.Scanner;

class Main {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        
        // Starting variables
        float basePrice = 250f;

        // Welcome message and ASCII art for stock bot
        System.out.println("\n\nWelcome to");
        System.out.println("\n" + //
                "  _________ __                 __   __________        __   \n" + //
                " /   _____//  |_  ____   ____ |  | _\\______   \\ _____/  |_ \n" + //
                " \\_____  \\\\   __\\/  _ \\_/ ___\\|  |/ /|    |  _//  _ \\   __\\\n" + //
                " /        \\|  | (  <_> )  \\___|    < |    |   (  <_> )  |  \n" + //
                "/_______  /|__|  \\____/ \\___  >__|_ \\|______  /\\____/|__|  \n" + //
                "        \\/                  \\/     \\/       \\/             \n" + //
                "");



        System.out.printf("The current price of RobInc. is %s.\n", StockBot.formatString(basePrice));
        System.out.println("Your current salary per day is $80\n");

        // Ask user for inputs

        float Wallet = AskForFloat.Ask("How much money do you have available for this bot?");
        if (Wallet <= 0) {
            System.out.println("\nYou need to have at least $0 to start! Restarting program...\n\n");
            main(null);
            return;
        }

        float BuyPrice = AskForFloat.Ask("\nAt what price would you want to auto-buy stock?");
        float SellPrice = AskForFloat.Ask("\nAt what price would you want to auto-sell stock?");

        if (BuyPrice <= 0 || SellPrice <= 0) {
            System.out.println("\nBuy and sell price thresholds must be over $0.00! Restarting program...\n\n");
            main(null);
            return;
        }

        if (SellPrice <= BuyPrice) {
            System.out.println("\nSell price must be higher than the buy price. We are TRYING to make you money! Restarting program...\n\n");
            main(null);
            return;
        }

        float Quantity = AskForFloat.Ask("\nHow much stock would you like to buy? The current price of each stock is " + StockBot.formatString(basePrice));

        if (Quantity < 0) {
            System.out.println("\nYou can not have a negative quantity of shares! Restarting program...\n\n");
            main(null);
            return;
        }
        
        if (Quantity * basePrice > Wallet) {
            System.out.println("\nYou have bought too many share for your wallet to handle! Restarting program...\n\n");
            main(null); // restart program
            return;
        }

        new StockBot(basePrice, Wallet, BuyPrice, SellPrice, Quantity);        
    }
}