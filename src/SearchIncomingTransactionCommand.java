import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class SearchIncomingTransactionCommand extends Command {
    public SearchIncomingTransactionCommand(TransactionManager transactionManager) {
        super("Search incoming transaction", transactionManager);

    }

    //Using annotation
    @Override
    public void execute(Scanner scanner) {
        System.out.println("Do you want to search by year, month, week or day?");
        System.out.println("1. Year");
        System.out.println("2. Month");
        System.out.println("3. Week");
        System.out.println("4. Day");

        int selection = -1;
        float total = -1;
        //Loop until correct users selection
        while (total == -1) {
            while (selection == -1) {
                try {
                    selection = scanner.nextInt();
                } catch (InputMismatchException exception) {
                    System.out.println("Enter a valid number");
                }
                scanner.nextLine();
            }
            if (selection == 1) {
                total = searchByYear(scanner, TransactionType.INCOMING);
                System.out.println("Total is " + total + "kr.");
            } else if (selection == 2) {
                total = searchByMonth(scanner, TransactionType.INCOMING);
                System.out.println("Total is " + total + "kr.");
            } else if (selection == 4) {
                total = searchByDay(scanner, TransactionType.INCOMING);
                System.out.println("Total is " + total + "kr.");
            } else {
                System.out.println("Enter a valid number");
                selection = -1;
            }
        }

    }

    private float searchByYear(Scanner scanner, TransactionType type) {
        System.out.println("Enter the year");
        int year = 0;
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        //Loop until correct user selection
        while (year == 0) {
            try {
                year = scanner.nextInt();
                startDate = LocalDate.of(year, 1, 1);
                endDate = LocalDate.of(year, 12, 31);
            } catch (InputMismatchException exception) {
                System.out.println("Enter a valid year");
            } catch (DateTimeException exception) {
                System.out.println("Enter a valid year");
            }
            scanner.nextLine();
        }
        float total = calculateTotal(startDate, endDate, type);
        return total;
    }

    /**
     * Search transaction by month
     *      * @param scanner Read users selection
     *      * @param type Incoming or outgoing transaction
     *      * @return Total for the user entered year and month
     */
    private float searchByMonth(Scanner scanner, TransactionType type) {
        int year = 0;
        int month = 0;
        LocalDate startdDate;
        LocalDate endDate;
        //Loop until correct users selection
        while (true) {
            try {
                System.out.println("Enter year:");
                year = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter month:");
                month = scanner.nextInt();
                scanner.nextLine();

                startdDate = LocalDate.of(year, month, 1);
                endDate = LocalDate.of(year, month, startdDate.lengthOfMonth());
                break;
            } catch (InputMismatchException exception) {
                System.out.println("Please enter numbers only.");
                scanner.nextLine();
            } catch (DateTimeException exception) {
                System.out.println("Month or year is invalid.Try again.");
            }
        }
        return calculateTotal(startdDate, endDate, type);
    }

    /**
     Search transaction by week
     * @param scanner Read users selection
     * @param type Incoming or outgoing transaction
     * @return Total for the user entered week
     */
    private float searchByWeek(Scanner scanner, TransactionType type) {
        System.out.println("Enter the date (YYYY-MM-DD)");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = null;
        //Loop until correct users selection
        while (endDate == null) {
            String dateString = scanner.nextLine();
            try {
                LocalDate date = LocalDate.parse(dateString);
                startDate = date.with(DayOfWeek.MONDAY);
                endDate = startDate.plusDays(6);
            } catch (DateTimeParseException exception) {
                System.out.println("Invalid date. Try again");
            } catch (DateTimeException exception) {
                System.out.println("Something went wrong.Try again");
            }
        }
        float total = calculateTotal(startDate, endDate, type);
        return total;
    }

    /**
     Search transaction by day
     * @param scanner Read users selection
     * @param type Incoming or outgoing transaction
     * @return Total for the user entered date
     */
    private float searchByDay(Scanner scanner, TransactionType type) {
        System.out.println("Enter the date (YYYY-MM-DD)");
        LocalDate date = null;
        //Loop until correct users selection
        while (date == null) {
            String dateString = scanner.nextLine();
            try {
                date = LocalDate.parse(dateString);
            } catch (DateTimeException exception) {
                System.out.println("Enter valid date");
            }
        }
        float total = calculateTotal(date, date, type);
        return total;
    }

    /**
     * calculate total between a start date and an end date
     * @param startDate user entered start date
     * @param endDate user entered end date
     * @return total for the user entered period
     */
    private float calculateTotal(LocalDate startDate, LocalDate endDate, TransactionType type) {
        float total = 0f;

        for (int i = 0; i < transactionManager.numberOfTransactions(); i++) {
            Optional<Transaction> transactionOptional = transactionManager.getTransaction(i);

            if(transactionOptional.isEmpty()) {
                System.out.println("Something went wrong.");
                break;
            }

            Transaction transaction = transactionOptional.get();

            if (transaction.getType() == type && !transaction.getDate().isBefore(startDate) && !transaction.getDate().isAfter(endDate)) {
                //Add transaction amount to total
                total += transaction.getAmount();
            }
        }
        return total;
    }
}
