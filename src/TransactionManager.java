import java.util.ArrayList;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private float accountBalance;

    public TransactionManager() {
        transactions = new ArrayList<>();
        accountBalance = 0f;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void decreaseAccountBalance(float amountToDecrease) {
        this.accountBalance -= amountToDecrease;
    }

    public void increaseAccountBalance(float amountToIncrease) {
        this.accountBalance += amountToIncrease;
    }

    public void addNewTransaction(Transaction newTransaction) {
        this.transactions.add(newTransaction);
    }

    public boolean hasNoTransactions() {
        return transactions.isEmpty();
    }

    public int numberOfTransactions() {
        return transactions.size();
    }

    public Transaction getTransaction(int place) {
        return transactions.get(place);
    }

    public void deleteTransaction(int place) {
        this.transactions.remove(place);
    }
}
