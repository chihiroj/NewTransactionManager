import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();
        Scanner scanner = new Scanner(System.in);
        Menu mainMenu = new MainMenu(transactionManager);

        System.out.println("Welcome.");

        while(true) {
            mainMenu.showMenu();
            mainMenu.selectInMenu(scanner);
        }
    }
}