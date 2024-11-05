import java.util.Scanner;

/**
 * Command only to use generics in application
 */
public class UseGenericsCommand extends Command {

    public UseGenericsCommand(TransactionManager transactionManager) {
        super("Using generics", transactionManager);
    }

    @Override
    public void execute(Scanner scanner) {
        System.out.println("Using generics");
        Integer[] intArray = {1, 2, 3, 4, 5};
        String[] strArray = {"Using", "Generics"};

        printArray(intArray);
        printArray(strArray);
    }

    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
