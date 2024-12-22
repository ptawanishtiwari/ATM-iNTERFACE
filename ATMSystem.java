import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String userId, String userPin) {
        return this.userId.equals(userId) && this.userPin.equals(userPin);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount);
            System.out.println("Successfully deposited Rs." + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount);
            System.out.println("Successfully withdrew Rs." + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void transfer(Account recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to " + recipient.getUserId());
            System.out.println("Successfully transferred Rs." + amount + " to " + recipient.getUserId());
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void showTransactionHistory() {
        System.out.println("--- Transaction History ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getUserId() {
        return userId;
    }
}

public class ATMSystem {

    private static Scanner scanner = new Scanner(System.in);
    private static Account currentAccount;
    private static Account demoAccount = new Account("user123", "1234", 10000);

    public static void main(String[] args) {
        System.out.println("--- Welcome to the ATM System ---");
        if (authenticateUser()) {
            showMenu();
        } else {
            System.out.println("Authentication failed. Exiting system.");
        }
    }

    private static boolean authenticateUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter User PIN: ");
        String userPin = scanner.nextLine();

        if (demoAccount.authenticate(userId, userPin)) {
            currentAccount = demoAccount;
            System.out.println("Authentication successful.");
            return true;
        } else {
            return false;
        }
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    currentAccount.showTransactionHistory();
                    break;
                case 2:
                    handleWithdraw();
                    break;
                case 3:
                    handleDeposit();
                    break;
                case 4:
                    handleTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleWithdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        currentAccount.withdraw(amount);
    }

    private static void handleDeposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        currentAccount.deposit(amount);
    }

    private static void handleTransfer() {
        System.out.print("Enter recipient User ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        if (recipientId.equals(demoAccount.getUserId())) {
            currentAccount.transfer(demoAccount, amount);
        } else {
            System.out.println("Recipient account not found.");
        }
    }
}
