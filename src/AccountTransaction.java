import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AccountTransaction {//класс для фиксирования информации о каждой транзакции отдельно
    private final LocalDateTime dateTime;
    private final double value;
    private final String type;
    private final String accountNumber;

    public AccountTransaction(double value, String type, String accountNumber){
        this.dateTime = LocalDateTime.now();
        this.value = value;
        this.type = type;
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Datetime: |" + fmt.format(dateTime) + "| " + type +
                "Счёт: " + accountNumber + ", Сумма: " + value;
    }
}
