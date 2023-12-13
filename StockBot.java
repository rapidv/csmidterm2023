import java.text.NumberFormat;
import java.util.Scanner;

public class StockBot {
    float startingBasePrice, startingPrice, investedMoney, startingBuyPrice, startingSellPrice, startingQuantity; 
    // for future reference after bot termination

    float basePrice, currentPrice, moneyAvailable, buyPrice, sellPrice, Quantity;
    
    boolean running = true;
    int Day = 0;
    int Skips = 0;

    int salaryPerDay = 80;

    static NumberFormat formatter = NumberFormat.getCurrencyInstance();
    Scanner scanner = new Scanner(System.in);
    
    // Constructor method
    public StockBot(float basePrice, float moneyAvailable, float buyPrice, float sellPrice, float Quantity) {
        this.basePrice = basePrice;
        currentPrice = basePrice;
        
        this.moneyAvailable = moneyAvailable;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.Quantity = Quantity;

        storeStartingValues();

        mainLoop(); // start automation
    }

    public static String formatString(float Number) {
        return formatter.format(Number); // formats float into a $X.XX format
    }

    private void storeStartingValues() { // storing all starting values into their own variables
        this.startingBasePrice = basePrice;
        this.startingPrice = currentPrice;
        this.investedMoney = moneyAvailable;
        this.startingBuyPrice = buyPrice;
        this.startingSellPrice = sellPrice;
        this.startingQuantity = Quantity;
    }
    
    private void changeStockPrice() { // changes/adjusts the stock price daily
        double rand = Math.random();

        float newPrice;

        float percent = (float) Math.clamp(Math.random()*.1, 0, 0.1); 
        System.out.println(percent);

        if (0 <= rand && rand < 0.48) { // regular increase
            newPrice = currentPrice * (1 + percent);
        } else if (0.48 <= rand && rand < 0.9) { // regular decrease
            newPrice = currentPrice * (1 - percent);
        } else if (0.96 <= rand && rand < 0.985) {
            newPrice = currentPrice * (1 + percent*10); // major increase 
        } else {
            newPrice = currentPrice * (1 - percent*2); // major decrease
        }

        currentPrice = newPrice;
    }

    private void sellAllStock() {
        if (Quantity > 0) {
            float profit = (Quantity * currentPrice);
            moneyAvailable = currentPrice + profit; // sell all stock when the price is >= sellPrice

            Quantity = 0;

            System.out.printf("You have sold all of your stock for %s.\n", formatString(profit));    
        } else {
            System.out.println("You do not have any stocks to sell.\n");
        }
    }

    private void botAutomation() {
        if (currentPrice >= sellPrice) { // sell
           sellAllStock();
        } else { // buy
            int purchaseQuantity = 0;

            for (int i = 1; i < 10; i++) { // at max, the bot can buy 10 shares automatically, if wallet allows
                // the 'i' variable in this case is the AMOUNT of shares we can afford
                float possibleAmount = currentPrice * i;
                
                if (possibleAmount <= moneyAvailable) {
                    purchaseQuantity = i; 
                } else {
                    break; 
                    // break loop if the quantity * currentPrice exceeds wallet amount, we can't buy shares with money we don't have!
                }
            }

            if (purchaseQuantity == 0 ) {
                System.out.println("You did not purchase any stock today.");
                return;
            } else {
                float priceTotal = (currentPrice * purchaseQuantity); // find price of the shares we want to buy

                moneyAvailable = moneyAvailable - priceTotal; // deduct the share price from the user's wallet
                Quantity += purchaseQuantity; // add the shares into the user's account
                
                System.out.printf("You have purchased %s shares for a total price of %s.\n", purchaseQuantity, formatString(priceTotal));
                System.out.printf("You have %s remaining in your wallet.\n", formatString(moneyAvailable));
            }
        }
    }

    private void displayStats(String StatType, String type) { // displays stats of the user (starting before bot or current stats)
        switch (StatType) {
            case "Current":
                System.out.printf("\n-------- %s STATS --------\n", type);
                System.out.println("STOCK PRICE: " + formatString(currentPrice));
                System.out.println("WALLET: " + formatString(moneyAvailable));
                System.out.println("Shares: " + Quantity);
                System.out.println("-----------------------\n");
                break;
            case "Starting":
                System.out.printf("\n-------- %s STATS --------\n", type);
                System.out.println("WALLET: " + formatString(investedMoney));
                System.out.println("Shares: " + startingQuantity);
                System.out.println("Starting Share Prices " + startingPrice);
                System.out.println("Shares: " + startingQuantity);
                System.out.println("-----------------------\n");
                break;
        }
    }

    private static boolean isNumeric(String str) { 
        try {  
          Integer.parseInt(str);  
          return true;
        } catch(NumberFormatException e){  
          return false;  
        }  
      }

    private void awaitNext() { // await for user input to proceed to the next day
        System.out.println("Press ENTER to continue to the next day, enter an integer to forward that many days, or enter EXIT & confirm to stop the automation.");
        
        String Input = scanner.nextLine();
        
        if (Input.equals("EXIT")) { // user wants to exit the program and stop the bot automation.
            running = false; // turn the loop condition false, so the while loop breaks
        } else if (isNumeric(Input)) {
            Skips = Integer.parseInt(Input);
        }
    }

    private void printDay() { // prints the day
        System.out.println("\n----------------------");
        System.out.printf("|    Day: %s           |\n", Day);
        System.out.println("----------------------\n");
    }

    private void mainLoop() {
        while (running) {
            
            moneyAvailable += salaryPerDay; // daily salary
            investedMoney += salaryPerDay;

            Day += 1;

            // prints day
            printDay();

            // display stats before the stock adjustments

            displayStats("Current", "START");

            // Call methods for the stock's activity
            changeStockPrice();

            // Call methods for the bot's activity
            botAutomation();
            
            // display stats after the bot automations
            displayStats("Current", "END");

            // await for user confirmation to progress to the next day
            if (Skips == 0) {
                 awaitNext();
            } else {
                Skips -= 1;
            }
        }
  
        System.out.println("You have terminated the StockBot automation.\n");
        System.out.println("Here are your before & after stats:");

        sellAllStock(); 
        // sell all stocks after bot automation, no point in owning stocks now since the bot did it for you and you don't know how to do stocks!

        displayStats("Starting", "BEFORE BOT"); // show the stats before they started the bot automaton
        displayStats("Current", "AFTER BOT"); // show the stats after the bot automation

        float Profit = moneyAvailable - investedMoney; // get the profit
        String formattedProfit = formatString(Profit); 

        if (Profit >= 0) {
            System.out.printf("You have made %s in profit!", formattedProfit);
        } else {
            System.out.printf("You have lost %s overall!", formattedProfit);
        }

        // close scanners, for resource purposes
        scanner.close(); 
        AskForFloat.scanner.close();
    }
}