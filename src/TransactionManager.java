import java.util.ArrayList;
import java.util.Optional;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private float accountBalance;
    private TransactionSaver transactionSaver;

    public TransactionManager(TransactionSaver transactionSaver) {
        transactions = new ArrayList<>();
        accountBalance = 0f;
        this.transactionSaver = transactionSaver;
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
        transactionSaver.saveTransaction(newTransaction);
    }

    public void addMultipleTransactions(ArrayList<Transaction> transactionsToAdd) {
        this.transactions.addAll(transactionsToAdd);
    }

    public boolean hasNoTransactions() {
        return transactions.isEmpty();
    }

    public int numberOfTransactions() {
        return transactions.size();
    }

    public Optional<Transaction> getTransaction(int place) {
        Transaction transaction =  transactions.get(place);
        return Optional.ofNullable(transaction);
    }

    public void deleteTransaction(int place) {
        Transaction transaction = this.transactions.get(place);
        this.transactions.remove(place);
        transactionSaver.deleteTransaction(transaction.getId());
    }
}
