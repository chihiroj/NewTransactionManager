import java.util.ArrayList;

public interface TransactionSaver {
    /**
     * Saves a transaction
     * @param transaction The transaction to save
     */
    void saveTransaction(Transaction transaction);

    /**
     * Deletes a transaction
     * @param id The id of the transaction to delete
     */
    void deleteTransaction(String id);

    /**
     * Loads all transactions
      * @return All stored transactions
     */
    ArrayList<Transaction> loadAllSavedTransactions();
}
