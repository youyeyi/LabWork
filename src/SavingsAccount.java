public class SavingsAccount extends BankAccount { //класс улучшенных(накопительных) счётов, наследующий обычные
    private final double percent;
    private final double withdrawLimitPercent;

    public SavingsAccount(String accountNumber, String ownerName, double balance, double percent,
                          double withdrawLimitPercent) {
        super(ownerName, accountNumber, balance);
        this.percent = percent;
        this.withdrawLimitPercent = withdrawLimitPercent;
    }

    @Override // переопределяю снятие с ограничением на вывод
    public void withdraw(double value) {
        if (value > getBalance() * (withdrawLimitPercent/100.0)) {
            System.out.println("Слишком большая сумма для снятия с накопительного счёта. Максимум "
                    + withdrawLimitPercent + "% от баланса. Сейчас на балансе: " + getBalance());
            return;
        }
        super.withdraw(value);
    }

    public void applyPercent() {
        double income = getBalance() * (percent/100.0);
        securityDeposit(income);
    }

    @Override
    public String toString() {
        return super.toString() + ", Процент: " + percent + "%, Лимит снятия: " + withdrawLimitPercent + "%";
    }
}
