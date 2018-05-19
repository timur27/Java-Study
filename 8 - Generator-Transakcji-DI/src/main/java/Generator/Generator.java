package Generator;

import Config.AppConfig;
import Model.SavedObject;
import Model.SavedObjectList;
import Model.Transaction;
import Reader.Parser;
import Writer.JSONWriter;
import Writer.XMLWriter;
import Writer.YAMLWriter;
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
        XMLWriter xmlWriter = (XMLWriter) ctx.getBean("XMLWriter");
        JSONWriter jsonWriter = (JSONWriter) ctx.getBean("JSONWriter");
        YAMLWriter yamlWriter = (YAMLWriter) ctx.getBean("YAMLWriter");
        Parser parser = (Parser) ctx.getBean("Parser");

        log.info("Starting application");
        Transaction resultTransaction = transactionGenerator.createAndFillTransaction(args);

        if (resultTransaction == null)
            return;

        int eventCount = Integer.valueOf(resultTransaction.getEventsCount());

        SavedObjectList savedObjectList = new SavedObjectList();
        for (int i = 0; i < eventCount; i++){
            log.info("Begin with mapping...");
            SavedObject objectToWrite = parser.parse(resultTransaction, i, file);
            savedObjectList.add(objectToWrite);
        }
        if (resultTransaction.getFormatOption().equals("xml")){
            File file = new File(resultTransaction.getOutDir() + ".xml");
            file.createNewFile();
            log.info("Begin with mapping to xml");
            xmlWriter.writeToFile(savedObjectList,file);
            log.info("Successfully done!");
        }
        else if(resultTransaction.getFormatOption().equals("json")){
            File file = new File(resultTransaction.getOutDir() + ".json");
            file.createNewFile();
            log.info("Begin with mapping to json");
            jsonWriter.writeToFile(savedObjectList.getSavedObjects(), file);
            log.info("Successfully done!");
        }
        else if (resultTransaction.getFormatOption().equals("yaml")){
            File file = new File(resultTransaction.getOutDir() + ".yaml");
            file.createNewFile();
            log.info("Begin with mapping to YAML");
            yamlWriter.writeToFile(savedObjectList.getSavedObjects(), file);
            log.info("Successfully done!");
        }

        System.out.println(resultTransaction);
    }
}