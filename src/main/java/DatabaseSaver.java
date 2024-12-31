import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.List.of;

/**
 * used to save, delete and load transactions from database
 */
public class DatabaseSaver implements TransactionSaver {

    private Connection conn;

    public DatabaseSaver(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        try{
            PreparedStatement insertTransactionStatement = conn.prepareStatement("INSERT INTO transactions (id, amount, date, description, type) VALUES (?,?,?,?,?)");
            insertTransactionStatement.setString(1, transaction.getId());
            insertTransactionStatement.setDouble(2,transaction.getAmount());
            insertTransactionStatement.setDate(3, Date.valueOf(transaction.getDate()));
            insertTransactionStatement.setString(4,transaction.getDescription());
            insertTransactionStatement.setString(5,transaction.getType().toString());

            insertTransactionStatement.execute();
        }catch (SQLException exception){
            exception.printStackTrace();
            return;
        }

    }

    @Override
    public void deleteTransaction(String id) {
        try{
            PreparedStatement deleteTransactionStatement = conn.prepareStatement("DELETE FROM transactions WHERE id=?");
            deleteTransactionStatement.setString(1, id);

            deleteTransactionStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }


    }

    @Override
    public ArrayList<Transaction> loadAllSavedTransactions() {
        ArrayList<Transaction> loadedTransactions = new ArrayList<>();
        try {
            Statement selectAllTransactionStatement = conn.createStatement();

            ResultSet resultSet = selectAllTransactionStatement.executeQuery("SELECT * FROM transactions");

            while (resultSet.next()){
                String  id = resultSet.getString("id");
                double amount = resultSet.getDouble("amount");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                String description = resultSet.getString("description");
                TransactionType type = TransactionType.valueOf(resultSet.getString("type"));
                Transaction newTransaction = new Transaction(UUID.fromString(id), (float) amount, description, date, type);
                loadedTransactions.add(newTransaction);
            }

            return loadedTransactions;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
