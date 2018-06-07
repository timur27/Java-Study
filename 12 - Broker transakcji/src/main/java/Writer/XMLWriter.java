package Writer;


import Model.SavedObject;
import Model.SavedObjectList;
import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;


@Service
@Configuration
public class XMLWriter {

    public void writeToFile(SavedObjectList savedObjectList, File file) throws IOException {
        String xmlString = "";
        XStream xstream = new XStream();
        xstream.alias("object", SavedObject.class);
        xstream.alias("objects", SavedObjectList.class);
        xstream.addImplicitCollection(SavedObjectList.class, "list");

        String xml = xstream.toXML(savedObjectList);
        Files.write(file.toPath(), Arrays.asList(xml), StandardOpenOption.APPEND);
    }
}