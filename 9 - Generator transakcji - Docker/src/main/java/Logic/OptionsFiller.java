package Logic;


import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Configuration
public class OptionsFiller{
    private static final Logger log = LoggerFactory.getLogger("sda");
    public CommandLine commandLineExecutor(String[] args, Options options){
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
