import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class BankMenu { //основной класс программы - консольное меню

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, BankAccount> accounts = new HashMap<>(); // храним все счета
        PercentApplier schedule = new PercentApplier(accounts); //запускаем таймер графика начисления процентов

        while (true) {
            menu();
            int menuChoice = scanner.nextInt();
            scanner.nextLine();
            switch (menuChoice) {
                case 1 -> createAccount(accounts, scanner);
                case 2 -> createSavingsAccount(accounts, scanner);
                case 3 -> depositAccount(accounts, scanner);
                case 4 -> withdrawAccount(accounts, scanner);
                case 5 -> showBalance(accounts, scanner);
                case 6 -> showRequisite(accounts, scanner);
                case 7 -> showTransactions(accounts, scanner);
                case 8 -> searchByOwner(accounts, scanner);
                case 0 -> {
                    System.out.println("Выход из программы");
                    schedule.stop();
                    return;
                }
                default -> {
                    System.out.println("Неверный выбор.");
                    pause();
                }
            }
        }
    }

    private static void createAccount(Map<String, BankAccount> accounts, Scanner scanner) {
        String accountNum = BankAccount.generateAccountNumber();
        System.out.print("Введите имя владельца: ");
        String name = scanner.nextLine();
        accounts.put(accountNum, new BankAccount(name, accountNum, 0));
        System.out.println("Создан обычный счёт. Номер счёта: " + accountNum);
        pause();
    }

    private static void createSavingsAccount(Map<String, BankAccount> accounts, Scanner scanner) {
        String accountNum = BankAccount.generateAccountNumber();
        System.out.print("Введите имя владельца: ");
        String name = scanner.nextLine();
        System.out.println("Выберите тариф накопительного счета:");
        System.out.println("1. 0.5% каждые 10 секунд, можно снять 100%");
        System.out.println("2. 1.5% каждые 10 секунд, можно снять 75%");
        System.out.println("3. 2.5% каждые 10 секунд, можно снять 50%");
        System.out.print("Выбор: ");
        int plan = scanner.nextInt();
        scanner.nextLine();
        double rate;
        double limit;
        switch(plan) {
            case 1 -> {
                rate = 0.5;
                limit = 100;
            }
            case 2 -> {
                rate = 1.5;
                limit = 75;
            }
            case 3 -> {
                rate = 2.5;
                limit = 50;
            }
            default -> {
                System.out.println("Неверный тариф.");
                pause();
                return;
            }
        }
        accounts.put(accountNum, new SavingsAccount(accountNum, name, 0, rate, limit));
        System.out.println("Накопительный счёт создан: номер " + accountNum + ", ставка " + rate + "%, лимит снятия "
                + limit + "%");
        pause();
    }

    private static void depositAccount(Map<String, BankAccount> accounts, Scanner scanner) {
        System.out.print("Введите номер счёта: ");
        String accountNum = scanner.nextLine();
        BankAccount account = accounts.get(accountNum);
        if (account == null) {
            System.out.println("Счёт не найден");
            pause();
            return;
        }
        System.out.print("Введите сумму: ");
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            account.deposit(value);
        }
        catch (NumberFormatException e) {
            System.out.println("Неверная сумма.");
        }
        pause();
    }

    private static void withdrawAccount(Map<String, BankAccount> accounts, Scanner scanner) {
        System.out.print("Введите номер счёта: ");
        String accountNum = scanner.nextLine();
        BankAccount account = accounts.get(accountNum);
        if (account == null) {
            System.out.println("Счёт не найден.");
            pause();
            return;
        }
        System.out.print("Введите сумму: ");
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            account.withdraw(value);
        }
        catch ( NumberFormatException e) {
            System.out.println("Неверная сумма.");
        }
        pause();
    }

    private static void showBalance(Map<String, BankAccount> accounts, Scanner scanner) {
        System.out.print("Введите номер счёта: ");
        String accountNum = scanner.nextLine();
        BankAccount account = accounts.get(accountNum);
        if (account != null) {
            System.out.println(account);
        }
        else {
            System.out.println("Счёт не найден.");
        }
        pause();
    }

    private static void showRequisite(Map <String, BankAccount> accounts, Scanner scanner) {
        System.out.print("Введите номер счёта: ");
        String accountNum = scanner.nextLine();
        BankAccount account = accounts.get(accountNum);
        if (account != null) {
            System.out.println("Реквизиты:");
            account.getRequisite();
        }
        else {
            System.out.println("Счёт не найден.");
        }
    }

    private static void showTransactions(Map<String, BankAccount> accounts, Scanner scanner) {
        System.out.print("Номер счёта: ");
        String accountNum = scanner.nextLine();
        BankAccount account = accounts.get(accountNum);
        if (account != null) {
            account.printTransactions();
        }
        else {
            System.out.println("Счёт не найден.");
        }
        pause();
    }

    private static void searchByOwner(Map<String, BankAccount> accounts, Scanner scanner) {
        System.out.print("Имя владельца: ");
        String searchName = scanner.nextLine();
        boolean isFound = false;
        for (BankAccount a : accounts.values()) {
            if (a.getOwner().equalsIgnoreCase(searchName)) {
                System.out.println(a);
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("Пользователь не найден.");
        }
        pause();
    }

    private static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void menu() {
        System.out.println("\n \\--- Банковская система ---/");
        System.out.println("1. Открыть счет");
        System.out.println("2. Открыть накопительный счёт");
        System.out.println("3. Положить деньги на счёт");
        System.out.println("4. Снять деньги со счёта");
        System.out.println("5. Показать  баланс счёта");
        System.out.println("6. Показать реквизиты счёта");
        System.out.println("7. Поиск транзакции на счёте");
        System.out.println("8. Поиск по владельцу");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }
}
