public class MainMenu extends Menu{

    public MainMenu(TransactionManager transactionManager){
        super();
        Command addTransactionCommand = new AddTransactionCommand(transactionManager);
        Command deleteTransactionCommand = new DeleteTransactionCommand(transactionManager);
        Command accountBalance = new AccountBalanceCommand(transactionManager);
        Command searchOutgoingTransaction = new SearchOutgoingTransactionCommand(transactionManager);
        Command searchIncomingTransaction = new SearchIncomingTransactionCommand(transactionManager);

        commands.put(1, addTransactionCommand);
        commands.put(2, deleteTransactionCommand);
        commands.put(3, accountBalance);
        commands.put(4, searchOutgoingTransaction);
        commands.put(5, searchIncomingTransaction);
    }
}
