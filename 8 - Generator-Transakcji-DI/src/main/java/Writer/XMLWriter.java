package Writer;


import Model.SavedObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

@Service
@Configuration
public class XMLWriter {

    public void writeToFile(SavedObject objectToWrite, File file) throws IOException {
        String xmlString = "";
        try {
            JAXBContext context = JAXBContext.newInstance(SavedObject.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            StringWriter sw = new StringWriter();
            m.marshal(objectToWrite, sw);
            xmlString = sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        Files.write(file.toPath(), Arrays.asList(xmlString), StandardOpenOption.APPEND);
    }

}