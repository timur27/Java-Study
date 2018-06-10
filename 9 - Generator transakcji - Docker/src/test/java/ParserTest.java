//import Model.SavedObject;
//import Model.Transaction;
//import Reader.Parser;
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class ParserTest {
//
//    @Mock
//    Parser parser;
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Test
//    public void testSplitting(){
//        //given
//        Parser parser = new Parser();
//        String startReg = "456:238";
//        int [] result;
//
//        //when
//        result = parser.splitString(startReg);
//
//        //then
//        Assert.assertEquals(456, result[0]);
//        Assert.assertEquals(238, result[1]);
//    }
//
//    @Test
//    public void testSplitDate(){
//        //given
//        String dateString = "2018-03-21T00:00:2018-03-22T00:00";
//
//        //when
//        String [] results = Parser.splitDate(dateString);
//
//        //then
//        Assert.assertEquals(results[0], "2018-03-21T00:00");
//        Assert.assertEquals(results[1], "2018-03-22T00:00");
//    }
//
//
//    @Test
//    public void testFormatDate(){
//        //given
//        String a = "2018-03-21T00:00";
//        String b = "2018-03-22T00:00";
//
//        //when
//        LocalDateTime[] result = Parser.formatDate(a,b);
//
//        //then
//        Assert.assertEquals(result[0].toString(), "2018-03-21T00:00");
//        Assert.assertEquals(result[1].toString(), "2018-03-22T00:00");
//    }
//
//    @Test
//    public void testSplitAndRandomDate(){
//        //given
//        String dateString = "2018-03-21T00:00:2018-03-22T00:00";
//
//        //when
//        String [] result = Parser.splitDate(dateString);
//        LocalDateTime[] localDateResult = Parser.formatDate(result[0], result[1]);
//        LocalDateTime tmp = localDateResult[0].plusDays(2);
//        LocalDateTime testedResult = parser.randomDate();
//
//        //then
//        Assert.assertEquals(result[0], "2018-03-21T00:00");
//        Assert.assertEquals(result[1], "2018-03-22T00:00");
//        Assert.assertEquals(localDateResult[0].toString(), "2018-03-21T00:00");
//        Assert.assertEquals(localDateResult[1].toString(), "2018-03-22T00:00");
////        Assert.assertEquals(testedResult, tmp);
//    }
//
//
//    @Test
//    public void testRandomize(){
//        int result = Parser.randomize(5, 10);
//
//        Assert.assertTrue(result <= 10);
//    }
//
//
//    @Test
//    public void testParse(){
//        SavedObject savedObject = new SavedObject();
//        String customerIds = "1:5";
//        int results[] = parser.splitString(customerIds);
//        savedObject.setCustomer_id(ThreadLocalRandom.current().nextInt(results[0], results[1] + 1));
//
//
//        Assert.assertEquals(results[0], 1);
//        Assert.assertEquals(results[1], 5);
////        Assert.assertTrue(savedObject.getCustomer_id() < 5);
//
//    }
//
//    @Test
//    public void testParseValues(){
//        //given
//        SavedObject savedObject = new SavedObject();
//        int [] results;
//        LocalDateTime resultTime = parser.splitAndRandomDate("2018-03-21T00:00:2018-03-22T00:00");
//
//        //when
//        results = parser.splitString("1:5");
//        savedObject.setCustomer_id(results[0]);
//        savedObject.setTimeStamp(resultTime);
//
//        //then
//        Assert.assertEquals(results[0], 1);
//        Assert.assertEquals(results[1], 5);
//        Assert.assertEquals(savedObject.getCustomer_id(), 1);
//        Assert.assertEquals(savedObject.getTimeStamp(), resultTime);
//    }
//
//    @Test
//    public void testParserOfValues(){
//        //given
//        int[] results;
//        Transaction transaction = new Transaction();
//        SavedObject savedObject = new SavedObject();
//
//        results = parser.parseValues(transaction, savedObject);
//        Assert.assertEquals(results[0], 1);
//        Assert.assertEquals(results[1], 5);
//    }
//
//    @Test
//    public void testParserAll() throws IOException {
//        Transaction transaction = new Transaction();
//        transaction.setItemsFile("items.csv");
//        int id = 1;
//        File file = new File("D:\\items.csv");
//
//        parser.parse(transaction, id, file);
//
//        Assert.assertEquals(transaction.getOutDir(), "outFile");
//    }
////    @Test
////    public void testSplitAndRandomForRandom(){
////        Mockito.when(parser.randomDate()).thenReturn(LocalDateTime.of(2018, 05, 27, 13, 42));
////        LocalDateTime result = parser.randomDate();
////
////        Assert.assertEquals(result, parser.randomDate());
////    }
//
////    @Test
////    public void testSplitAndRandomDate(){
////        //given
////        String dateString = "2018-03-21T00:00:2018-03-22T00:00";
////        LocalDateTime randomedTime;
////
////        //when
////        Mockito.when(Reader.Parser.randomDate());
////        Reader.Parser.splitAndRandomDate(dateString);
////    }
//
//}
