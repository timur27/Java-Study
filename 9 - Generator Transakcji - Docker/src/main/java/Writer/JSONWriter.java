package Writer;


import Model.SavedObject;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

@Service
@Configuration
public class JSONWriter {

    public void writeToFile(List<SavedObject> savedObjects, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(savedObjects);
        Files.write(file.toPath(), Arrays.asList(json), StandardOpenOption.APPEND);
    }
}
