package Config;

import Generator.OptionsFiller;
import Generator.OptionsGenerator;
import Generator.TransactionGenerator;
import Reader.Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "OptionsFiller")
    public OptionsFiller optionsFiller(){
        OptionsFiller optionsFiller = new OptionsFiller();
        return optionsFiller;
    }

    @Bean(name = "Parser")
    public Parser parser(){
        Parser parser = new Parser();
        return parser;
    }

    @Bean(name = "TransactionGenerator")
    public TransactionGenerator transactionGenerator(){
        TransactionGenerator transactionGenerator = new TransactionGenerator();
        return transactionGenerator;
    }

    @Bean(name = "OptionsGenerator")
    public OptionsGenerator optionsGenerator(){
        OptionsGenerator optionsGenerator = new OptionsGenerator();
        return optionsGenerator;
    }

}
