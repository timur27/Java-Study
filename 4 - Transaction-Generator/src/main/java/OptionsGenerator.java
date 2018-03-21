import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;


@Data
public class OptionsGenerator {
    public final static String value = "testValue";

    public static Options fillOptoins(Options options){
        options.addOption("customerIds", "customerIds", true, "Zakres wartosci do pola ID");
        options.addOption("dateRange", "dateRange", true, "Zakres czasowy");
        options.addOption("itemsFile", "itemsFile", true, "Plik ze spisem produktów");
        options.addOption("itemsCount", "itemsCount", true, "Zakres ilosci generowanych elementów");
        options.addOption("itemsQuantity", "itemsQuantity", true, "Zakres");
        options.addOption("eventsCount", "eventsCount", true, "Ilość transakcji");
        options.addOption("outDir", "outDir", true, "Katalog do przechowania pliku");

        return options;
    }

    public static Transaction fillTransaction(Transaction transaction, CommandLine cmd){
        if (cmd.hasOption("customerIds"))
            transaction.setCustomerID(cmd.getOptionValue("customerIds"));
        if (cmd.hasOption("dateRange"))
            transaction.setDateRange(cmd.getOptionValue("dateRange"));
        if (cmd.hasOption("itemsFile"))
            transaction.setItemsFile(cmd.getOptionValue("itemsFile"));
        else {
            System.out.println("You haven't passed itemsFile arg");
            return null;
        }
        if (cmd.hasOption("itemsCount"))
            transaction.setItemsCount(cmd.getOptionValue("itemsCount"));
        if (cmd.hasOption("itemsQuantity"))
            transaction.setItemsQuantity(cmd.getOptionValue("itemsQuantity"));
        if (cmd.hasOption("eventsCount"))
            transaction.setEventsCount(cmd.getOptionValue("eventsCount"));
        if (cmd.hasOption("outDir"))
            transaction.setOutDir(cmd.getOptionValue("outDir"));
        return transaction;
    }
}
