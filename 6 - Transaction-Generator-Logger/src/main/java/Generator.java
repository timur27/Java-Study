import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Random;

class GeneratorHelper{
    private static final Logger logger = LogManager.getLogger("GeneratorHelper");
    public static CommandLine commandLineExecutor(String[] args){

        Options options = OptionsGenerator.fillOptoins(new Options());      //wypelniam opcje parametrami

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try{
            logger.info("Arguments would be parsed from cmd with already defined options");
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe){
            logger.error("Arguments are not right, smthg went wrong");
            return null;
        }
        return cmd;
    }
}

public class Generator {
    private static final Logger logger = LogManager.getLogger("Generator");

    public static void main(String[] args) throws IOException{
        logger.info("Will be generated CMD with options");
        CommandLine cmd = GeneratorHelper.commandLineExecutor(args);
        if (cmd == null) return;
        logger.info("sd");
        Transaction transaction = new Transaction();
        transaction = OptionsGenerator.fillTransaction(transaction, cmd);

        if (transaction == null)
            return;

        Random random = new Random();
        int eventCount = Integer.valueOf(transaction.getEventsCount());
        File file = new File(transaction.getOutDir() + "user.json");
        for (int i = 0; i < eventCount; i++){
            Parser.parse(transaction, i, file);
        }
        System.out.println(transaction);
    }
}