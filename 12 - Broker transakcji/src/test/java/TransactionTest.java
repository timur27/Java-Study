import Model.Transaction;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;


public class TransactionTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();


    @Test
    public void testNewTransaction(){
        Transaction transaction = new Transaction();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Warsaw"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime tommorowMidnight = todayMidnight.plusDays(1);


        Assert.assertEquals(transaction.getCustomerID(), "1:20");
        Assert.assertEquals(transaction.getDateRange(), todayMidnight.toString() + ":" + tommorowMidnight.toString());
        Assert.assertEquals(transaction.getItemsFile(), null);
        Assert.assertEquals(transaction.getItemsCount(), "1:5");
        Assert.assertEquals(transaction.getItemsQuantity(), "1:30");
        Assert.assertEquals(transaction.getEventsCount(), "100");
        Assert.assertEquals(transaction.getOutDir(), "outFile");



        //then

    }
}
