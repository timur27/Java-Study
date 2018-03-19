import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Parser {


    public static void parse(Transaction transaction, int howMany) throws IOException {
        SavedObject savedObject = new SavedObject();
        String [] a = transaction.getCustomerID().split(":");
        savedObject.setCustomer_id(ThreadLocalRandom.current().nextInt(Integer.valueOf(a[0]), Integer.valueOf(a[1])+1));
        a[0] = transaction.getDateRange().substring(0, transaction.getDateRange().length()/2);
        a[1] = transaction.getDateRange().substring(transaction.getDateRange().length()/2+1, transaction.getDateRange().length());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(a[0], formatter);
        LocalDateTime localDateTime1 = LocalDateTime.parse(a[1], formatter);

        Random random = new Random();

        LocalDateTime time = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(random.nextInt(24), random.nextInt(60),
                        random.nextInt(60), random.nextInt(999999999 + 1)));

        savedObject.setTimeStamp(time);
        a = transaction.getItemsCount().split(":");
        int itemsNumber = random.nextInt((Integer.valueOf(a[1]) - Integer.valueOf(a[0])) + 1) + Integer.valueOf(a[1]);
        a = transaction.getItemsQuantity().split(":");
        int quantityNumber = random.nextInt(Integer.valueOf(a[1]) - Integer.valueOf(a[0]) + 1) + Integer.valueOf(a[1]);
        Scanner input = new Scanner(new File(transaction.getItemsFile()));
        List<Item> itemList = new ArrayList<Item>();
        int j = 0;
        while (input.hasNextLine() && itemsNumber >= 0){
            String line = input.nextLine();
            Item item = new Item();
            String []splitter = line.split(",");
            if (splitter[0].equals("name")) continue;
            item.setName(splitter[0]);
            item.setQuantity(quantityNumber);
            item.setPrice(Double.parseDouble(splitter[1]));
            itemList.add(item);
            itemsNumber--;
        }
        savedObject.setId(howMany);
        savedObject.setItems(itemList);
        double priceForItems = 0;
        for (Item i : itemList){
            priceForItems += i.getPrice() * i.getQuantity();
        }
        savedObject.setSum(priceForItems);
        ObjectMapper mapper = new ObjectMapper();
        mapper.defaultPrettyPrintingWriter().writeValue(new File(transaction.getOutDir() + "user.json"), savedObject);
    }
}
