//import Logic.OptionsGenerator;
//import Model.Transaction;
//import org.apache.commons.cli.*;
//import org.junit.Assert;
//import org.junit.Test;
//
//public class OptionsGeneratorTest {
//
//    @Test
//    public void testFill(){
//        Options options = new Options();
////        options.addOption("customerIds", "customerIds", true, "Zakres wartosci do pola ID");
////        options.addOption("dateRange", "dateRange", true, "Zakres czasowy");
////        options.addOption("itemsFile", "itemsFile", true, "Plik ze spisem produktów");
////        options.addOption("itemsCount", "itemsCount", true, "Zakres ilosci generowanych elementów");
////        options.addOption("itemsQuantity", "itemsQuantity", true, "Zakres");
////        options.addOption("eventsCount", "eventsCount", true, "Ilość transakcji");
////        options.addOption("outDir", "outDir", true, "Katalog do przechowania pliku");
//
//        OptionsGenerator.fillOptoins(options);
//
//        Assert.assertTrue(options.hasOption("customerIds"));
//        Assert.assertTrue(options.hasOption("dateRange"));
//        Assert.assertTrue(options.hasOption("itemsFile"));
//        Assert.assertTrue(options.hasOption("itemsCount"));
//        Assert.assertTrue(options.hasOption("itemsQuantity"));
//        Assert.assertTrue(options.hasOption("eventsCount"));
//        Assert.assertTrue(options.hasOption("outDir"));
//    }
//
//
//    @Test
//    public void testTransactionFill(){
//        Transaction transaction = new Transaction();
//        String args = "-customerIds 1:20 " +
//                "-dateRange 2018-03-08T00:00:00.000-0100:2018-03-08T23:59:59.999-0100 " +
//                "-itemsFile items.csv " +
//                "-itemsCount 5:15 " +
//                "-itemsQuantity 1:30 " +
//                "-eventsCount 10 " +
//                "-outDir ./output";
//        Options options = new Options();
//        OptionsGenerator.fillOptoins(options);
//
//        CommandLineParser parser = new BasicParser();
//        CommandLine cmd = null;
//
//        try{
//            cmd = parser.parse(options, args.split(" "));
//        }
//        catch (ParseException pe){
//            System.out.println("Unfortunately, arguments are not right");
//            return;
//        }
//
//        transaction = OptionsGenerator.fillTransaction(transaction, cmd);
//
//        Assert.assertEquals(transaction.getOutDir(), "./output");
//        Assert.assertEquals(transaction.getEventsCount(), "10");
//    }
//
//    @Test
//    public void testNoItemsFileArg(){
//        String args = "";
//        Transaction transaction = new Transaction();
//        Options options = new Options();
//        OptionsGenerator.fillOptoins(options);
//
//        CommandLineParser parser = new BasicParser();
//        CommandLine cmd = null;
//
//        try{
//            cmd = parser.parse(options, args.split(" "));
//        }
//        catch (ParseException pe){
//            System.out.println("Unfortunately, arguments are not right");
//            return;
//        }
//
//        transaction = OptionsGenerator.fillTransaction(transaction, cmd);
//        Assert.assertEquals(transaction, null);
//
//    }
//
//    @Test
//    public void testOptionsGeneratorEmpty(){
//        OptionsGenerator optionsGenerator = new OptionsGenerator();
//
//        Assert.assertTrue(optionsGenerator instanceof OptionsGenerator);
//    }
//}
