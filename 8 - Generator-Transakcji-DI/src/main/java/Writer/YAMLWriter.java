package Writer;


import Model.SavedObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@Configuration
public class YAMLWriter {
    public void writeToFile(List<SavedObject> savedObjects, File file) throws IOException {
        Representer representer = new Representer();
        representer.addClassTag(SavedObject.class, new Tag("savedObject"));
        Yaml yaml = new Yaml(representer, new DumperOptions());
        yaml.dump(savedObjects, new PrintWriter(file, "UTF-8"));
    }
}

