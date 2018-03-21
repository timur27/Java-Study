import java.math.BigDecimal;

public class Parser {
    public static BigDecimal parseToBigDecimal(String value){
        return new BigDecimal(value);
    }

    public static int parseToInt(String value){
        return Integer.valueOf(value);
    }

    public static Long parseToLong(String value){
        return Long.parseLong(value);
    }
}
