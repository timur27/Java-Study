package Generator;

import Model.Transaction;
import Reader.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;

public class Generator {
    private static final Logger log = LoggerFactory.getLogger("sda");

    public static void main(String[] args) throws IOException{
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("TransactionGenerator");
        Parser parser = (Parser) ctx.getBean("Parser");

        log.info("Starting application");
        Transaction resultTransaction = transactionGenerator.createAndFillTransaction(args);

        if (resultTransaction == null)
            return;

        int eventCount = Integer.valueOf(resultTransaction.getEventsCount());

        File file = new File(resultTransaction.getOutDir() + "File.json");

        file.createNewFile();

        for (int i = 0; i < eventCount; i++){
            parser.parse(resultTransaction, i, file);
        }

        System.out.println(resultTransaction);
    }
}