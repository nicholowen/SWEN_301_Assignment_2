package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import sun.plugin2.message.Message;

import java.io.IOException;

public class MemAppenderTest {

    Logger logger = Logger.getLogger("TEST");
    MemAppender memAppender = new MemAppender();

//    @Before
//    public void init(){
//        logger = Logger.getLogger("TEST");
//    }

    @Test
    public void test1(){
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", new Throwable());
        memAppender.append(lg);
        memAppender.exportToJSON("logs.json");
    }

}
