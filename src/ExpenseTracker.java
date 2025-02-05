
import java.io.*;
import java.util.*;

public class ExpenseTracker {
    static List<Expense> expenses = new ArrayList<>();
    static String expenseFile = "expenses.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Register\n2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (choice == 1) {
            if (User.register(username, password)) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed!");
                return;
            }
        } else if (choice == 2) {
            if (!User.login(username, password)) {
                System.out.println("Invalid credentials!");
                return;
            }
            System.out.println("Login successful!");
        }

        loadExpenses();
        while (true) {
            System.out.println("1. Add Expense\n2. View Expenses\n3. Category-wise Total\n4. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter Amount: ");
                    double amount = scanner.nextDouble();
                    addExpense(new Expense(date, category, amount));
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    categoryWiseTotal();
                    break;
                case 4:
                    saveExpenses();
                    System.out.println("Exiting...");
                    return;
            }
        }
    }

    public static void addExpense(Expense expense) {
        expenses.add(expense);
        saveExpenses();
    }

    public static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public static void categoryWiseTotal() {
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Expense expense : expenses) {
            categoryTotals.put(expense.category, categoryTotals.getOrDefault(expense.category, 0.0) + expense.amount);
        }
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(expenseFile))) {
            for (Expense expense : expenses) {
                writer.write(expense.date + "," + expense.category + "," + expense.amount + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(expenseFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    expenses.add(new Expense(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) {
            System.out.println("No previous expense records found.");
        }
    }
}
