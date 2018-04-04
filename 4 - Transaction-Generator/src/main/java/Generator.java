import org.apache.commons.cli.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        File file = new File(transaction.getOutDir() + ".json");
        Files.deleteIfExists(file.toPath());
        file.createNewFile();
        List<SavedObject> savedObjects = new ArrayList<SavedObject>();
        SavedObject savedObject;

        for (int i = 0; i < eventCount; i++){
            savedObject = Parser.parse(transaction, i, file);
            savedObjects.add(savedObject);
        }


        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedObjects);
        Files.write(file.toPath(), Arrays.asList(json), StandardOpenOption.APPEND);

        System.out.println(transaction);
    }
}
