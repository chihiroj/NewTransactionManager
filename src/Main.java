import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TransactionSaver transactionSaver = new FileSaver();
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