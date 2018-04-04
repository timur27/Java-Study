package Generator;

import Config.AppConfig;
import Model.Transaction;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class TransactionGenerator {
    public AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    private static final Logger log = LoggerFactory.getLogger("sda");

    public Transaction createAndFillTransaction(String [] args){
        ctx.register(AppConfig.class);
        ctx.refresh();
        OptionsFiller optionsFiller = (OptionsFiller) ctx.getBean("OptionsFiller");
        OptionsGenerator optionsGenerator = (OptionsGenerator) ctx.getBean("OptionsGenerator");
        Options options = optionsGenerator.fillOptoins(new Options());
        Transaction transaction = new Transaction();
        CommandLine commandLine = optionsFiller.commandLineExecutor(args, options);

        log.info("Will be generated CMD with options");

        if (commandLine == null)
            return null;

        transaction = optionsGenerator.fillTransaction(transaction, commandLine);
        return transaction;
    }
}