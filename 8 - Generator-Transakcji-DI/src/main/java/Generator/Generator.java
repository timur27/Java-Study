package Generator;

import Config.AppConfig;
import Model.SavedObject;
import Model.SavedObjectList;
import Model.Transaction;
import Reader.Parser;
import Writer.XMLWriter;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.xml.bind.annotation.XmlList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generator {
    private static final Logger log = LoggerFactory.getLogger("sda");
    private static List<SavedObject> savedObjects = new ArrayList<SavedObject>();

    public static void main(String[] args) throws IOException{
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();

        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("TransactionGenerator");
        XMLWriter xmlWriter = (XMLWriter) ctx.getBean("XMLWriter");
        Parser parser = (Parser) ctx.getBean("Parser");

        log.info("Starting application");
        Transaction resultTransaction = transactionGenerator.createAndFillTransaction(args);

        if (resultTransaction == null)
            return;

        int eventCount = Integer.valueOf(resultTransaction.getEventsCount());

        File file = new File(resultTransaction.getOutDir() + ".json");

        file.createNewFile();

        SavedObjectList savedObjectList = new SavedObjectList();
        for (int i = 0; i < eventCount; i++){
            log.info("Begin with mapping...");
            SavedObject objectToWrite = parser.parse(resultTransaction, i, file);
            savedObjectList.add(objectToWrite);
            savedObjects.add(objectToWrite);
        }
        if (resultTransaction.getFormatOption().equals("xml")){
            log.info("Begin with mapping to xml");
            xmlWriter.writeToFile(savedObjectList,file);
        }

        else{
            log.info("Begin with mapping to json");
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedObjects);
            Files.write(file.toPath(), Arrays.asList(json), StandardOpenOption.APPEND);
            log.info("Successfully done!");
        }

        System.out.println(resultTransaction);
    }
}