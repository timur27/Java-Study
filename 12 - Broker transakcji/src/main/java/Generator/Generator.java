package Generator;

import Config.AppConfig;
import Messaging.JmsProducerQueue;
import Messaging.JmsProducerTopic;
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

import java.io.*;
import java.util.Properties;

public class Generator {
    private static final Logger log = LoggerFactory.getLogger("sda");


    public static void main(String[] args) throws Exception {
        MessagingService messagingService = new MessagingService();
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();


        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("TransactionGenerator");
        XMLWriter xmlWriter = (XMLWriter) ctx.getBean("XMLWriter");
        JSONWriter jsonWriter = (JSONWriter) ctx.getBean("JSONWriter");
        YAMLWriter yamlWriter = (YAMLWriter) ctx.getBean("YAMLWriter");
        Parser parser = (Parser) ctx.getBean("Parser");
        String propertiesPath = "";

        if (args.length != 0)
            propertiesPath = args[0];

        log.info("Starting application");
        Transaction resultTransaction;

        if (args.length != 0 && args[0].equals("D:/generator.properties")){
            System.out.println("HELLO");
            File fileToReadProperties = new File(propertiesPath);
            Properties props = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToReadProperties));
            props.load(bufferedReader);

            String[] parameters = new String[]{
                    "-customerIds",
                    props.getProperty("customerIds"),
                    "-dateRange",
                    props.getProperty("dateRange").replace("\"", ""),
                    "-itemsFile",
                    "." + props.getProperty("itemsFile"),
                    "-itemsCount",
                    props.getProperty("itemsCount"),
                    "-itemsQuantity",
                    props.getProperty("itemsQuantity"),
                    "-eventsCount",
                    props.getProperty("eventsCount"),
                    "-outDir",
                    props.getProperty("outDir"),
                    "--format=" + props.getProperty("format"),
                    "-queue", props.getProperty("queue"),
                    "-broker", props.getProperty("broker")
            };
            resultTransaction = transactionGenerator.createAndFillTransactionFromFile(parameters);
        }
        else
            resultTransaction = transactionGenerator.createAndFillTransaction(args);


        if (resultTransaction == null)
            return;

        File file;
        if (resultTransaction.getFormatOption().equals("xml")){
            file = new File( resultTransaction.getOutDir() + "/" + "outputFile.xml");
            file.createNewFile();
        }
        else if(resultTransaction.getFormatOption().equals("json")){
            file = new File(resultTransaction.getOutDir() + "/" + "outputFile.json");
            file.createNewFile();
        }
        else{
            file = new File(resultTransaction.getOutDir() + "/" + "outputFile.yaml");
            file.createNewFile();
        }

        int eventCount = Integer.valueOf(resultTransaction.getEventsCount());

        SavedObjectList savedObjectList = new SavedObjectList();
        for (int i = 0; i < eventCount; i++){
            log.info("Begin with mapping...");
            SavedObject objectToWrite = parser.parse(resultTransaction, i, file);
            savedObjectList.add(objectToWrite);
        }
        if (resultTransaction.getFormatOption().equals("xml")){
            log.info("Begin with mapping to xml");
            xmlWriter.writeToFile(savedObjectList,file);
            messagingService.checkIfMessagingAndSend(resultTransaction);
            log.info("Successfully done!");
        }
        else if(resultTransaction.getFormatOption().equals("json")){
            log.info("Begin with mapping to json");
            jsonWriter.writeToFile(savedObjectList.getSavedObjects(), file);
            messagingService.checkIfMessagingAndSend(resultTransaction);
            log.info("Successfully done!");
        }
        else if (resultTransaction.getFormatOption().equals("yaml")){
            log.info("Begin with mapping to YAML");
            yamlWriter.writeToFile(savedObjectList.getSavedObjects(), file);
            messagingService.checkIfMessagingAndSend(resultTransaction);
            log.info("Successfully done!");
        }
    }
}

class MessagingService{
    private JmsProducerQueue jmsProducerQueue;
    private JmsProducerTopic jmsProducerTopic;


    public void checkIfMessagingAndSend(Transaction transaction) throws Exception {
        if (!transaction.getBroker().equals("")){
            if (!transaction.getQueueName().equals("")){
                jmsProducerQueue = new JmsProducerQueue(transaction.getBroker(), transaction.getQueueName());
                jmsProducerQueue.sendMessage(String.valueOf(transaction));
            }
            if (!transaction.getTopic().equals("") || !transaction.getTopic().equals("")){
                jmsProducerTopic = new JmsProducerTopic(transaction.getBroker(), transaction.getTopic());
                jmsProducerTopic.sendMessage(String.valueOf(transaction));
            }
        }
    }
}