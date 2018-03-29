
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@Data
public class Transaction {
    private String customerID;
    private String dateRange;
    private String itemsFile;
    private String itemsCount;
    private String itemsQuantity;
    private String eventsCount;
    private String outDir;

    Transaction(){
        customerID = "1:20";
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Warsaw"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tommorowMidnight = todayMidnight.plusDays(1);
        dateRange =  todayMidnight.toString() + ":" + tommorowMidnight.toString();
        itemsFile = null;
        itemsCount = "1:5";
        itemsQuantity = "1:30";
        eventsCount = "100";
        outDir = "outFile";
    }
}