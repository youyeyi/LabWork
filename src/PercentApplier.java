import java.util.Map;
import java.util.Timer;
import java.util.TimerTask; // с помощью TimerTask автоматизирую начисление процентов

public class PercentApplier { //класс запускающий таймер для работы периодического начисления процентов
    private static final int INTERVAL = 10000;

    private final Timer timer;

    public PercentApplier(Map<String, BankAccount> accounts) {
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(BankAccount account: accounts.values()) {
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount)account).applyPercent();
                    }
                }
            }
        },0, INTERVAL); //Начисление процентов каждые 10 сек. (для наглядности)
    }

    public void stop() {
        timer.cancel();
    }
}
