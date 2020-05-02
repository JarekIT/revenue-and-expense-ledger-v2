package pl.jarekit.rael;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;

@SpringBootApplication
public class RevenueAndExpenseLedger2Application {

    public static void main(String[] args) {
        SpringApplication.run(RevenueAndExpenseLedger2Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startLogViewer() {
        // You can view : https://log-viewer-jarekit.netlify.app
        LogUtils.saveLogStatic("Revenue And Expense Ledger 2 Application just started", Level.INFO);
    }
}
