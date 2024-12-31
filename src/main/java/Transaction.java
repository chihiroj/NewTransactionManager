import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private float amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    private UUID id;

    public Transaction(float amount, String description, LocalDate date, TransactionType type){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        id = UUID.randomUUID();
    }

    public Transaction(UUID id, float amount, String description, LocalDate date, TransactionType type){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
        this.id = id;
    }

    public void printTransaction(int location){
        System.out.println(location + "." + description + "," + amount + "," + type);

    }

    public String getId() {
        return id.toString();
    }

    public float getAmount(){return amount;}

    public LocalDate getDate(){return  date;}

    public String getDescription(){return description;}

    public TransactionType getType(){return type;}
}
