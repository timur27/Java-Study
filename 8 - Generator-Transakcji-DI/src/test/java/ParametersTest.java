

import org.apache.commons.cli.*;
import org.junit.Assert;
import org.junit.Test;

public class ParametersTest {

    @Test
    public void testParameters() throws ParseException {
        String args = "-customerIds 1:20 " +
                "-dateRange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100 " +
                "-itemsFile items.csv " +
                "-itemsCount 5:15 " +
                "-itemsQuantity 1:30 " +
                "-eventsCount 10 " +
                "-outDir ./output";
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
        String[] finalArgs = args.split(" ");
        cmd = parser.parse(options, finalArgs);
        Assert.assertTrue(cmd.getOptions().length == 7);
    }

    @Test
    public void testWithoutParameters() {
        String args = "-itemsCount 5:15 ";
        String[] finalArgs = args.split(" ");
        Assert.assertTrue(finalArgs.length == 2);
    }
}
