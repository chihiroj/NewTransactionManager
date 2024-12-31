import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Base class for all menus
 */
public abstract class Menu {
    protected HashMap<Integer, Command> commands;

    public Menu(){
        commands = new HashMap<Integer, Command>();
    }

    /**
     * Prints all menu commands
     */
    public void showMenu() {
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            Integer key = entry.getKey();
            Command command = entry.getValue();
            System.out.println(key + ". " + command.getMenuText());
        }
    }

    /**
     * Lets user select from menu
     * @param scanner Scanner object to use for reading input from user
     */
    public void selectInMenu(Scanner scanner) {
        int selection;

        try {
            selection = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException exception) {
            System.out.println("Enter a number");
            return;
        }

        Command command = commands.get(selection);
        command.execute(scanner);
    }
}
