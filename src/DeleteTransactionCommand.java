import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DeleteTransactionCommand extends Command {

    public DeleteTransactionCommand(TransactionManager transactionManager) {
        super("Delete transaction", transactionManager);
    }


    @Override
    public void execute(Scanner scanner) {
        if (transactionManager.hasNoTransactions()) {
            System.out.println("No transactions yet.");
            return;
        }

        System.out.println("Enter the date of the transaction (YYYY-MM-DD)");

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

        boolean foundTransaction = false;
        //Loop through all transactions
        for (int i = 0; i < transactionManager.numberOfTransactions(); i++) {
            Transaction transaction = transactionManager.getTransaction(i);

            //If transactions date is same as user entered date
            if (transaction.getDate().equals(date)) {
                foundTransaction = true;
                transaction.printTransaction(i);
            }
        }

        //If at least one transaction exists for this date
        if(foundTransaction) {

            System.out.println("Choose the transaction number");
            int selection = -1;
            Transaction transaction = null;
            //Loop until correct users selection
            while (selection == -1) {
                try {
                    selection = scanner.nextInt();
                    transaction = transactionManager.getTransaction(selection);
                } catch (InputMismatchException exception) {
                    System.out.println("Enter a valid number");
                } catch (IndexOutOfBoundsException exception) {
                    System.out.println("Enter a valid number");
                    selection = -1;
                }
                scanner.nextLine();
            }

            if (transaction.getType() == TransactionType.INCOMING) {
                transactionManager.decreaseAccountBalance(transaction.getAmount());
            } else if (transaction.getType() == TransactionType.OUTGOING) {
                transactionManager.increaseAccountBalance(transaction.getAmount());
            }

            transactionManager.deleteTransaction(selection);
            System.out.println("The transaction was deleted");
        } else {
            System.out.println("No transactions at this date.");
        }
    }
}
