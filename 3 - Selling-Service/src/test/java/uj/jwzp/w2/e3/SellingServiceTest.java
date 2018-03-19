package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingServiceTest {

    @Mock
    private DiscountConfigWrapper discountConfigWrapper;

    @Mock
    private PersistenceLayer persistenceLayer;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void notSell() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        //when
        boolean sold = uut.sell(i, 7, c, discountConfigWrapper.isWeekendPromotion());

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.moneyService.getMoney(c));
    }

    @Test
    public void sell() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");

        //when
        boolean sold = uut.sell(i, 1, c, discountConfigWrapper.isWeekendPromotion());

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }


    @Test
    public void sellALot() {
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(),Mockito.any(),Mockito.anyInt())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Łojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));

        //when
        Mockito.when(discountConfigWrapper.isWeekendPromotion()).thenReturn(false);
        boolean sold = uut.sell(i, 10, c, discountConfigWrapper.isWeekendPromotion());

        //then
        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(970), uut.moneyService.getMoney(c));
    }

    @Test
    public void testName(){
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(),Mockito.any(),Mockito.anyInt())).thenReturn(Boolean.TRUE);
        Item item = new Item("PC", new BigDecimal(100));

        Customer customer = new Customer(1, "Arkadiusz", "Lojasiewicza 6");
        uut.moneyService.addMoney(customer, new BigDecimal(990));

        //when
        Mockito.when(discountConfigWrapper.isWeekendPromotion()).thenReturn(false);
        boolean sold = uut.sell(item, 1, customer, discountConfigWrapper.isWeekendPromotion());

        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(950), uut.moneyService.getMoney(customer));
    }


    @Test
    public void testWeekendPromotionAndItemCostBiggerThan5(){
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(),Mockito.any(),Mockito.anyInt())).thenReturn(Boolean.TRUE);
        Item item = new Item("Lenovo", new BigDecimal(15));

        Customer customer = new Customer(1, "Timur", "Sliska14");
        uut.moneyService.addMoney(customer, new BigDecimal(10));

        //when
        Mockito.when(discountConfigWrapper.isWeekendPromotion()).thenReturn(true);
        boolean sold = uut.sell(item, 1, customer, discountConfigWrapper.isWeekendPromotion());


        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(17), uut.moneyService.getMoney(customer));
    }

    @Test
    public void testWeekendPromotionAndItemCostSmallerThan5(){
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(),Mockito.any(),Mockito.anyInt())).thenReturn(Boolean.TRUE);
        Item item = new Item("Lody", new BigDecimal(4));

        Customer customer = new Customer(1, "Timur", "Sliska14");
        uut.moneyService.addMoney(customer, new BigDecimal(10));

        //when
        Mockito.when(discountConfigWrapper.isWeekendPromotion()).thenReturn(true);
        boolean sold = uut.sell(item, 1, customer, discountConfigWrapper.isWeekendPromotion());

        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(18), uut.moneyService.getMoney(customer));
    }



    @Test
    public void testKrakowObwarzanek(){
        //given
        SellingService uut = new SellingService(persistenceLayer);
        Mockito.when(persistenceLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Mockito.when(persistenceLayer.saveTransaction(Mockito.any(),Mockito.any(),Mockito.anyInt())).thenReturn(Boolean.TRUE);
        Item item = new Item("Obwarzanek", new BigDecimal(2));

        Customer customer = new Customer(1, "Timur", "Kraków");
        uut.moneyService.addMoney(customer, new BigDecimal(5));

        //when
        boolean sold = uut.sell(item, 1, customer, discountConfigWrapper.isWeekendPromotion());

        Assert.assertTrue(sold);
        Assert.assertEquals(BigDecimal.valueOf(14), uut.moneyService.getMoney(customer));
    }
}
