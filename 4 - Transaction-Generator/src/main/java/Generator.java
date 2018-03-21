import org.apache.commons.cli.*;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Random;

class GeneratorHelper{
    public static CommandLine commandLineExecutor(String[] args){

        Options options = OptionsGenerator.fillOptoins(new Options());      //wypelniam opcje parametrami

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;

        try{
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe){
            System.out.println("Unfortunately, arguments are not right");
            return null;
        }
        return cmd;
    }
}

public class Generator {

    public static void main(String[] args) throws IOException{

        CommandLine cmd = GeneratorHelper.commandLineExecutor(args);
        if (cmd == null) return;

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