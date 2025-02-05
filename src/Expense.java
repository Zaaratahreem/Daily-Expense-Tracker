public class Expense {
    String date, category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | " + amount;
    }
}

