import java.util.Scanner;

public class AccountBalanceCommand extends Command {
    public AccountBalanceCommand(TransactionManager transactionManager) {
        super("Account Balance", transactionManager);
    }


    @Override
    public void execute(Scanner scanner) {
        System.out.println("Account balance: " + transactionManager.getAccountBalance() + "kr");
    }
}