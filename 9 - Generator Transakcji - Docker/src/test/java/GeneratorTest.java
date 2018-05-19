import Generator.Generator;
import Generator.OptionsFiller;
import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class GeneratorTest {

    @Test
    public void generatorHelperTest(){
        String args = "-customerIds 1:20 " +
                "-dateRange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100 " +
                "-itemsFile items.csv " +
                "-itemsCount 5:15 " +
                "-itemsQuantity 1:30 " +
                "-eventsCount 10 " +
                "-outDir ./output";

        CommandLine testCMD = Generator.OptionsFiller.commandLineExecutor(args.split(" "));

        Assert.assertTrue(testCMD.hasOption("customerIds"));
    }

    @Test
    public void testGeneratorHelperWithError(){
        String args = "-custsdomerIds 1:20 " +
                "-dateRsdange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100 " +
                "-itemwesFile items.csv " +
                "-itemqwesCount 5:15 " +
                "-itemsQsduantity 1:30 " +
                "-evedsntsCount 10 " +
                "-outDfir ./output";

        CommandLine testCMD = Generator.OptionsFiller.commandLineExecutor(args.split(" "));

        Assert.assertEquals(testCMD, null);
    }

    @Test
    public void testGeneratorOnCreate(){
        Generator.OptionsFiller generatorHelper = new OptionsFiller();
        Assert.assertTrue(generatorHelper instanceof OptionsFiller);
    }

    @Test
    public void testMain() throws IOException {
        String args = "-itemsFile items.csv ";
//        String [] args = {"-itemsFile", "items.csv"};
//
//        CommandLine testCMD = Generator.GeneratorHelper.commandLineExecutor(args.split(" "));

        Generator.main(args.split(" "));
        File testFile = new File("C:\\Users\\TKA\\IdeaProjects\\Java-Commercial\\4 - Model.Transaction-Generator.Generator\\build\\libs\\outFileuser.json");

        Assert.assertFalse(testFile.exists());
    }

    @Test
    public void testGenerator(){
        Generator generator = new Generator();
        Assert.assertTrue(generator instanceof Generator);
    }
}