import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn == null) return;

        createTables(conn);

        TransactionSaver transactionSaver = new DatabaseSaver(conn);
        TransactionManager transactionManager = new TransactionManager(transactionSaver);
        Scanner scanner = new Scanner(System.in);
        Menu mainMenu = new MainMenu(transactionManager);

        loadTransactions(transactionSaver, transactionManager);

        changeAccountBalance(transactionManager); //Only to use reflection

        System.out.println("Welcome.");

        while(true) {
            mainMenu.showMenu();
            mainMenu.selectInMenu(scanner);
        }
    }

    /**
     * get connection to database
     * @return the connection
     */
    private static Connection getConnection() {
        String connectionString = "jdbc:postgresql://localhost/transactionmanager?user=postgres&password=password";
        Connection conn;
        try{
            conn = DriverManager.getConnection(connectionString);
        }catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
        return conn;
    }

    /**
     * create new transaction table if not exists
     * @param conn the connection to database
     */
    private static void createTables(Connection conn) {
        try{
            Statement createTableStatement = conn.createStatement();
            createTableStatement.execute("CREATE TABLE IF NOT EXISTS transactions(\n" +
                    " id VARCHAR(100) PRIMARY KEY NOT NULL,\n" +
                    " amount DECIMAL NOT NULL,\n" +
                    " date DATE NOT NULL,\n" +
                    " description VARCHAR(100) NOT NULL,\n" +
                    " type VARCHAR(10) NOT NULL\n" +
                    ")\n");
        }catch (SQLException exception){
            exception.printStackTrace();
            return;
        }
    }

    /**
     * Load all saved transactions
     * @param transactionSaver The class to use for loading
     * @param transactionManager Keeps track of all transactions
     */
    private static void loadTransactions(TransactionSaver transactionSaver, TransactionManager transactionManager) {
        ArrayList<Transaction> transactions = transactionSaver.loadAllSavedTransactions();
        for(int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if(transaction.getType() == TransactionType.INCOMING) {
                transactionManager.increaseAccountBalance(transaction.getAmount());
            } else {
                transactionManager.decreaseAccountBalance(transaction.getAmount());
            }
        }
        transactionManager.addMultipleTransactions(transactions);
    }

    //Only to use reflection
    private static void changeAccountBalance(TransactionManager transactionManager) {
        try {
            Class<?> transactionManagerClass = transactionManager.getClass();

            Field accountBalanceField = transactionManagerClass.getDeclaredField("accountBalance");
            accountBalanceField.setAccessible(true);
        } catch (NoSuchFieldException exception) {
            System.out.println("Could not change account balance");
        }
    }
}