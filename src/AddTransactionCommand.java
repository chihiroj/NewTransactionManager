import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddTransactionCommand extends Command{

    public AddTransactionCommand(TransactionManager transactionManager) {
        super("Add transaction", transactionManager);
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.println("1. Incoming transaction?");
        System.out.println("2. Outgoing transaction?");
        int selection = -1;

        TransactionType type = null;

        //Loop until user enter valid selection
        while (type == null) {
            while (selection == -1) {
                try {
                    selection = scanner.nextInt();
                } catch (InputMismatchException exception) {
                    System.out.println("Enter a valid number");
                }
                scanner.nextLine();
            }
            if (selection == 1) {
                type = TransactionType.INCOMING;
            } else if (selection == 2) {
                type = TransactionType.OUTGOING;
            } else {
                System.out.println("Enter 1 or 2");
                selection = -1;
            }
        }

        System.out.println("Enter date (YYYY-MM-DD)");
        LocalDate date = null;
        //Loop until user enter valid selection
        while (date == null) {
            String dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString);
            } catch (DateTimeException exception) {
                System.out.println("Enter valid date");
            }
        }

        System.out.println("Amount");
        float amount = 0f;
        //Loop until user enter valid selection
        while (amount == 0f) {
            try {
                amount = scanner.nextFloat();
            } catch (InputMismatchException exception) {
                System.out.println("Enter valid amount");
            }
            scanner.nextLine();
        }
        if (type == TransactionType.OUTGOING) {
            transactionManager.decreaseAccountBalance(amount);
        } else if (type == TransactionType.INCOMING) {
            transactionManager.increaseAccountBalance(amount);
        }

        System.out.println("Description");
        String description = scanner.nextLine();

        Transaction newTransaction = new Transaction(amount, description, date, type);
        transactionManager.addNewTransaction(newTransaction);
        System.out.println("Transaction was added");
    }
}
