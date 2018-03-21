
import com.internal.DiscountCalculator;
import com.external.PaymentsService;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class LoggerExample {
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(LoggerExample.class);
        logger.debug("BEGIN");
        BasicConfigurator.configure();
        DiscountCalculator discountCalculator = new DiscountCalculator();
        PaymentsService paymentsService = new PaymentsService();
        BigDecimal ticketPrice = Parser.parseToBigDecimal(args[0]);
        int customerAge = Parser.parseToInt(args[1]);
        long customerID = Parser.parseToLong(args[2]);
        long companyID = Parser.parseToLong(args[3]);

        BigDecimal discountValue = discountCalculator.calculateDiscount(ticketPrice, customerAge);
        boolean paymentMade = paymentsService.makePayment(customerID, companyID, ticketPrice.subtract(discountValue));
        if (paymentMade)
            System.out.println("Payment successfully");
        else
            System.out.println("Problem occured. Please, try again");

        logger.info("DONE!");
    }
}
