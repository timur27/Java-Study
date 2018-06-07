import org.apache.commons.cli.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

public class FileNamesTests {
    @Test
    public void rightPath() throws ParseException {
        String args = "-outDir ./out";
        String [] finalArgs = args.split(" ");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        Options options = new Options();
        options.addOption("outDir", "outDir", true, "Katalog do przechowania pliku");
        cmd = parser.parse(options, finalArgs);
        Assert.assertTrue(cmd.getOptions().length == 1);
    }

    @Test
    public void fileDoesntExistTest(){
        String args = "-outDir ./asljdhahsldiggaewflalfkjhskjdhfkjsadfkaslfgajdfoh;sadh";

        String []finalArgs = args.split(" ");
        File file = new File(finalArgs[1]);
        Assert.assertFalse(file.exists());
    }
}
