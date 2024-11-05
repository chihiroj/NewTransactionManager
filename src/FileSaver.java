import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileSaver implements TransactionSaver {

    @Override
    public void saveTransaction(Transaction transaction) {
        File saveFile = createNewFile("transactions.txt");
        if(saveFile == null) {
            return;
        }

        addNewTransactionToFile(transaction, "transactions.txt");
    }

    @Override
    public void deleteTransaction(String id) {
        ArrayList<Transaction> transactionsInFile = loadAllSavedTransactions();

        //Using lambda and streams
        List<Transaction> transactionsAfterDeletion =
                transactionsInFile.stream()
                .filter(transaction -> !transaction.getId().equals(id))
                .toList();

        resetFile("transactions.txt");

        for(int i = 0; i < transactionsAfterDeletion.size(); i++) {
            addNewTransactionToFile(transactionsAfterDeletion.get(i), "transactions.txt");
        }
    }

    @Override
    public ArrayList<Transaction> loadAllSavedTransactions() {
        ArrayList<Transaction> transactionsFromFile = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader("transactions.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while(line != null) {
                line = bufferedReader.readLine();

                if(line != null && line.equals("Transaction")) {
                    Transaction transaction = readTransactionFromFile(bufferedReader);
                    transactionsFromFile.add(transaction);
                }
            }

        } catch (IOException e) {
            System.out.println("Could not load transactions from file.");
        }

        return transactionsFromFile;
    }

    private Transaction readTransactionFromFile(BufferedReader bufferedReader) throws IOException {
        UUID id = UUID.fromString(bufferedReader.readLine());
        float amount = Float.parseFloat(bufferedReader.readLine());
        LocalDate date = LocalDate.parse(bufferedReader.readLine());
        String description = bufferedReader.readLine();
        TransactionType type = TransactionType.valueOf(bufferedReader.readLine());

        Transaction transaction = new Transaction(id, amount, description, date, type);

        return transaction;
    }

    private void resetFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, false);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println("Could not reset file.");
        }
    }

    private void addNewTransactionToFile(Transaction transaction, String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write("\n");
            fileWriter.write("Transaction\n");
            fileWriter.write(transaction.getId() + "\n");
            fileWriter.write(String.valueOf(transaction.getAmount()) + "\n");
            fileWriter.write(transaction.getDate().toString() + "\n");
            fileWriter.write(transaction.getDescription() + "\n");
            fileWriter.write(transaction.getType().toString() + "\n");
            fileWriter.close();
        } catch(IOException exception) {
            System.out.println("Could not save transactions to file.");
        }
    }

    private File createNewFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("No save file existed yet. Creating new one.");
            }
            return file;
        } catch (IOException exception) {
            System.out.println("Could not save transactions to file.");
            return null;
        }
    }
}