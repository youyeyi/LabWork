import java.util.ArrayList;

public class BankAccount { //класс описывающий обычный банковский счёт
    private static int accountCounter = 1;
    // условное описание реквизитов банка
    public static final String BANK_NAME = "CSTBank";
    public static final String BIC = "04220";
    public static final String CORR_NUMBER = "543789";
    public static final String TIN = "770708";
    public static final String RRC = "52600";

    private final String ownerName;
    private final String accountNumber;
    private double balance;
    private final ArrayList<AccountTransaction> transactions = new ArrayList<>(); //логи транзакций

    public BankAccount(String ownerName, String accountNumber, double balance){
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getOwner() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public void getRequisite(){
        System.out.println("Название банка: " + BANK_NAME);
        System.out.println("Номер счёта: " + accountNumber);
        System.out.println("БИК: " + BIC);
        System.out.println("Корр. счёт: " + CORR_NUMBER);
        System.out.println("ИНН: " + TIN);
        System.out.println("КПП: " + RRC);
    }

    public static String generateAccountNumber() {
        return String.format("%04d", accountCounter++);
    }

    public void deposit(double value) {
        if (value>0) {
            securityDeposit(value);
        }
        else {
            System.out.println("Сумма депозита должна быть положительной.");
        }
    }

    //отдельная функция депозита, не выводящая сообщений, для счётов, на которые пассивно начисляются проценты + доп. защита
    protected void securityDeposit(double value) {
        balance+=value;
        transactions.add(new AccountTransaction(value, "Deposit", accountNumber));
    }

    public void withdraw(double value) {
        if (value<=0) {
            System.out.println("Сумма снятия должна быть положительной.");
            return;
        }
        if (balance-value<0) {
            System.out.println("Недостаточно средств на счёте, доступно на счёте: " + balance);
        }
        else {
            securityWithdraw(value);
        }
    }

    //метод не имеет какой-то конкретной цели в данной реализации, но в случае необходимости реализации пассивного снятия
    //(без вывода сообщений) может понадобиться, а так просто существует для симметричности логики с securityDeposit
    protected void securityWithdraw(double value) {
        balance-=value;
        transactions.add(new AccountTransaction(value, "Withdraw", accountNumber));
    }

    public void printTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("Не сделано ни одной транзакции");
        }
        else{
            for (AccountTransaction t: transactions) {
                System.out.println(t);
            }
        }
    }

    @Override
    public String toString() {
        return "Владелец: " + ownerName + ", Номер счёта: " + accountNumber + ", Баланс счёта: " + balance;
    }
}
