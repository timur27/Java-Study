import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.LogManager;

@Slf4j
class GeneratorHelper{

    public static CommandLine commandLineExecutor(String[] args){

        Options options = OptionsGenerator.fillOptoins(new Options());      //wypelniam opcje parametrami

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try{
            log.info("Arguments would be parsed from cmd with already defined options");
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe){
            log.error("Arguments are not right, smthg went wrong");
            return null;
        }
        return cmd;
    }
}

public class Generator {
    private static final Logger log = LoggerFactory.getLogger("sda");
//    private static final Logger logger = LogManager.getLogger("ASYNC_JSON_FILE_APPENDER");

    public static void main(String[] args) throws IOException{

        log.info("Will be generated CMD with options");
        CommandLine cmd = GeneratorHelper.commandLineExecutor(args);
        if (cmd == null) return;
        log.info("sd");
        Transaction transaction = new Transaction();
        transaction = OptionsGenerator.fillTransaction(transaction, cmd);

        if (transaction == null)
            return;

        Random random = new Random();
        int eventCount = Integer.valueOf(transaction.getEventsCount());
        File file = new File(transaction.getOutDir() + "user.json");
        file.createNewFile();
        for (int i = 0; i < eventCount; i++){
            Parser.parse(transaction, i, file);
        }
        System.out.println(transaction);
    }
}