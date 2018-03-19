package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.DiscountsConfig;

import java.math.BigDecimal;

public class DiscountConfigWrapper {
    public static final DiscountConfigWrapper SINGLETON = new DiscountConfigWrapper();

    public static DiscountConfigWrapper getWrapper(){
        return SINGLETON;
    }

    public BigDecimal getDiscountForItem(Item item, Customer customer) {
        return DiscountsConfig.getDiscountForItem(item,customer);
    }

    public boolean isWeekendPromotion() {
        return DiscountsConfig.isWeekendPromotion();
    }
}
