import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class SavedObject {
    private int id;
    private LocalDateTime timeStamp;
    private int customer_id;
    private List<Item> items;
    private double sum;

    public SavedObject(int id, LocalDateTime timeStamp, int customer_id, List<Item> items, double sum) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.customer_id = customer_id;
        this.items = items;
        this.sum = sum;
    }

    public SavedObject(){};
}
