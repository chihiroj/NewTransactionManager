import java.util.Scanner;

public abstract class Command {
   protected TransactionManager transactionManager;
   private String menuText;

   public Command(String menuText, TransactionManager transactionManager) {
       this.menuText = menuText;
       this.transactionManager = transactionManager;
   }

   public abstract void execute(Scanner scanner);

   public String getMenuText() {
       return menuText;
   }
}
