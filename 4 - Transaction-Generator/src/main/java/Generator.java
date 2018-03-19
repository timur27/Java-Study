import org.apache.commons.cli.*;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Random;


public class Generator {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException, ParseException {
            Options options = new Options();
            Option a = Option.builder().argName("customerIds").longOpt("customerIds").hasArg(true).desc("zakres").valueSeparator('=').build();
    //        System.out.println(a.getValueSeparator());
    //        options.addOption(a);
            options.addOption("customerIds", "customerIds", true, "Zakres wartosci do pola ID");
            options.addOption("dateRange", "dateRange", true, "Zakres czasowy");
            options.addOption("itemsFile", "itemsFile", true, "Plik ze spisem produktów");
            options.addOption("itemsCount", "itemsCount", true, "Zakres ilosci generowanych elementów");
            options.addOption("itemsQuantity", "itemsQuantity", true, "Zakres");
            options.addOption("eventsCount", "eventsCount", true, "Ilość transakcji");
            options.addOption("outDir", "outDir", true, "Katalog do przechowania pliku");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try{
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe){
            System.out.println("Unfortunately, arguments are not right");
            return;
        }

        Transaction transaction = new Transaction();

            if (cmd.hasOption("customerIds"))
                transaction.setCustomerID(cmd.getOptionValue("customerIds"));
            if (cmd.hasOption("dateRange"))
                transaction.setDateRange(cmd.getOptionValue("dateRange"));
            if (cmd.hasOption("itemsFile"))
                transaction.setItemsFile(cmd.getOptionValue("itemsFile"));
            else {
                System.out.println("You haven't passed itemsFile arg");
                return;
            }
            if (cmd.hasOption("itemsCount"))
                transaction.setItemsCount(cmd.getOptionValue("itemsCount"));
            if (cmd.hasOption("itemsQuantity"))
                transaction.setItemsQuantity(cmd.getOptionValue("itemsQuantity"));
            if (cmd.hasOption("eventsCount"))
                transaction.setEventsCount(cmd.getOptionValue("eventsCount"));
            if (cmd.hasOption("outDir"))
                transaction.setOutDir(cmd.getOptionValue("outDir"));

        System.out.println(transaction.getOutDir());
        Random random = new Random();
        int eventCount = Integer.valueOf(transaction.getEventsCount());
        for (int i = 0; i < eventCount; i++){
            Parser.parse(transaction, i);
        }
        System.out.println(transaction);

    }
}