import org.springframework.boot.ApplicationPid

import java.nio.charset.Charset

import static ch.qos.logback.classic.Level.*
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.status.OnConsoleStatusListener
import ch.qos.logback.core.FileAppender

displayStatusOnConsole()
scan('5 minutes')  // Scan for changes every 5 minutes.
setupAppenders()
setupLoggers()

def displayStatusOnConsole() {
    statusListener OnConsoleStatusListener
}

def setupAppenders() {

    //add to converters to handle colour and whitespace
    conversionRule 'clr', org.springframework.boot.logging.logback.ColorConverter
    conversionRule 'wex', org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter


    def consolePatternFormat = "%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%5p) %clr([%property{PID} - %thread]){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(>){faint} %m%n%wex"
    def filePatternFormat = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%property{PID} - %thread] %-5level %-12logger{12}:[.%M] > %msg%n%wex"
    if (!System.getProperty("PID")) {
        System.setProperty("PID", (new ApplicationPid()).toString())
    }


    //def logfileDate = timestamp('yyyy-MM-dd') // Formatted current date.
    // hostname is a binding variable injected by Logback.
    //def filePatternFormat = "%d{HH:mm:ss.SSS} %-5level [${hostname}] %logger - %msg%n"
    //appender('logfile', FileAppender) {
    //    file = "simple.${logfileDate}.log"
    //    encoder(PatternLayoutEncoder) {
    //        pattern = filePatternFormat
    //    }
    //}

    // Add custom converter for %smiley pattern.
    //conversionRule 'smiley', SmileyConverter


    appender('STDOUT', ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            charset = Charset.forName('UTF-8')
            pattern = consolePatternFormat
            //pattern =
        }
    }
}

def setupLoggers() {
    //logger 'com.mrhaki.java.Simple', getLogLevel(), ['logfile']
    logger 'application.Application', DEBUG
    root DEBUG, ['STDOUT']
}

def getLogLevel() {
    (isDevelopmentEnv() ? DEBUG : INFO)
}

def isDevelopmentEnv() {
    def env =  System.properties['app.env'] ?: 'DEV'
    env == 'DEV'
}