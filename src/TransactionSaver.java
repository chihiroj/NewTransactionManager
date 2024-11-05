import java.util.ArrayList;

public interface TransactionSaver {
    void saveTransaction(Transaction transaction);
    void deleteTransaction(String id);
    ArrayList<Transaction> loadAllSavedTransactions();
}
