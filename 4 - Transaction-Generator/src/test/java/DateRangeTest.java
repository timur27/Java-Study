import org.apache.commons.cli.*;
import org.junit.Assert;
import org.junit.Test;

public class DateRangeTest {

    @Test
    public void testDateRange() throws ParseException {
        String args = "-dateRange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100";
        String finalArgs[] = args.split(" ");

        Options options = new Options();
        options.addOption("dateRange", "dateRange", true, "Zakres czasowy");
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        cmd = parser.parse(options, finalArgs);
        Assert.assertTrue(cmd.getOptions().length == 1);
    }
}
