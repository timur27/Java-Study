package Reader;

import Model.Item;
import Model.SavedObject;
import Model.Transaction;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;



@Service
@Lazy
public class Parser {

    private static final Logger log = LoggerFactory.getLogger("sda");
    //    private final static Logger logger = LogManager.getLogger("Reader.Parser");
    private static LocalDateTime aTime;
    private static LocalDateTime bTime;

    public int[] splitString(String word){
        log.info("Begin splitting");
        String [] result = word.split(":");
        int[] res = new int[2];
        res[0] = Integer.valueOf(result[0]);
        res[1] = Integer.valueOf(result[1]);
        log.info("Splitting ends");
        return res;
    }

    public LocalDateTime randomDate(){
        log.info("Begin with finding random date");
        long days = aTime.until(bTime, ChronoUnit.DAYS);
        long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
        LocalDateTime randomDate = aTime.plusDays(randomDays);
        return randomDate;
    }

    public LocalDateTime[] formatDate(String a, String b){
        log.info("Date formatting...");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        aTime = LocalDateTime.parse(a, formatter);
        bTime = LocalDateTime.parse(b, formatter);
        LocalDateTime res[] = {aTime, bTime};
        return res;
    }

    public String[] splitDate(String dateRange){
        log.info("Date splitting...");
        String a = dateRange.substring(0, dateRange.length()/2);
        String b = dateRange.substring(dateRange.length()/2+1, dateRange.length());
        String[] ab = {a,b};
        return ab;
    }

    public LocalDateTime splitAndRandomDate(String dateRange){
        String []abc = splitDate(dateRange);
        LocalDateTime[] ab = formatDate(abc[0],abc[1]);
        aTime = ab[0];
        bTime = ab[1];

        LocalDateTime randomDate = randomDate();
        return randomDate;
    }

    public int randomize(int a, int b){
        log.info("Randomizer working...");
        Random random = new Random();
        int result = random.nextInt((b-a) + a);

        return result;
    }



    public int[] parseValues(Transaction transaction, SavedObject savedObject){
        int[] results;
        log.info("Now string would be splitted");
        results = splitString(transaction.getCustomerID());     //dla customerID
        savedObject.setCustomer_id(ThreadLocalRandom.current().nextInt(results[0], results[1] + 1));

        LocalDateTime dateTime = splitAndRandomDate(transaction.getDateRange()); // dla LocalDateTime (TimeStamp)
        savedObject.setTimeStamp(dateTime);

        String [] a = new String[2];
        results = splitString(transaction.getItemsCount());         // dla itemsCount
        return results;
    }


    public void parse(Transaction transaction, int id, File file) throws IOException {
        SavedObject savedObject = new SavedObject();
        log.info("All values would be parsed");
        int[] results = parseValues(transaction, savedObject);
        int itemsNumber = randomize(results[0], results[1]);        //itemsNumber nie dodaje się do Model.SavedObject
        results = splitString(transaction.getItemsQuantity());      // dla itemsQuantity później
        log.info("Looking for a file");
        Scanner input = new Scanner(new File(transaction.getItemsFile()));
        List<Item> itemList = new ArrayList<Item>();
        while (input.hasNextLine() && itemsNumber >= 0){
            String line = input.nextLine();
            Item item = new Item();
            String []splitter = line.split(",");
            if (splitter[0].equals("name")) continue;
            item.setName(splitter[0]);
            int q = randomize(results[0], results[1]);
            item.setQuantity(q);
            item.setPrice(Double.parseDouble(splitter[1]));
            itemList.add(item);
            itemsNumber--;
            log.info("Line successfully added to items");
        }

        log.info("Filling out object to save");
        savedObject.setId(id);
        savedObject.setItems(itemList);

        double priceForItems = 0;
        for (Item i : itemList){
            log.info("Summarizing price...");
            priceForItems += i.getPrice() * i.getQuantity();
        }

        savedObject.setSum(priceForItems);
        log.info("Begin with mapping to json");
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedObject);
//        mapper.writerWithDefaultPrettyPrinter().writeValue(file, savedObject);
        Files.write(file.toPath(), Arrays.asList(json), StandardOpenOption.APPEND);
        log.info("Successfully done!");
    }
}
