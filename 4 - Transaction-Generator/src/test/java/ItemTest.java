import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemTest {

    @Test
    public void constructorTest(){
        Item item = new Item("Dlugopis", 25, 1.2);
        Item item1 = new Item("Dlugopis", 25, 1.2);

        Assert.assertEquals(item, item1);
    }

    @Test
    public void savedObjectConstructorTest(){
        List<Item> itemList = new ArrayList<>();
        Item item = new Item("Dlugopis", 25, 1.2);
        Item item1 = new Item("Dlugopis", 25, 1.2);
        itemList.add(item);
        itemList.add(item1);

        SavedObject savedObject = new SavedObject(1, LocalDateTime.of(2018,04, 23, 23, 12), 2, itemList, 2.75);
        SavedObject savedObject1 = new SavedObject(1, LocalDateTime.of(2018,04, 23, 23, 12), 2, itemList, 2.75);

        Assert.assertEquals(savedObject, savedObject1);
    }
}