import java.time.LocalDate;

public class Transaction {
    private float amount;
    private LocalDate date;
    private String description;
    private TransactionType type;

    public Transaction(float amount, String description, LocalDate date, TransactionType type){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }
    public void printTransaction(int location){
        System.out.println(location + "." + description + "," + amount + "," + type);

    }
    public float getAmount(){return amount;}

    public LocalDate getDate(){return  date;}

    public String getDescription(){return description;}

    public TransactionType getType(){return type;}
}
