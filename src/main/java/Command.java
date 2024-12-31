import java.util.Scanner;

/**
 * Base class for all commands
 */
public abstract class Command {
   protected TransactionManager transactionManager;
   private String menuText;

   public Command(String menuText, TransactionManager transactionManager) {
       this.menuText = menuText;
       this.transactionManager = transactionManager;
   }

    /**
     * Executes the command
     * @param scanner
     */
   public abstract void execute(Scanner scanner);

   public String getMenuText() {
       return menuText;
   }
}
