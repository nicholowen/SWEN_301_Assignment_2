package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MemAppenderTest {

    @Test
    public void test1(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
        memAppender.append(lg);
        memAppender.exportToJSON("logs.json");
    }

    @Test
    public void test2(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.clearLogs();
        long maxSize = memAppender.getMaxSize();
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        memAppender.exportToJSON("something.json");
        Assert.assertEquals(memAppender.getCurrentLogs().size(), maxSize);
    }

    @Test // threshold test - append a lower priority
    public void test3(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.clearLogs();
        memAppender.setThreshold(Level.ERROR);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test ", null);
        memAppender.append(lg);
        Assert.assertEquals(memAppender.getCurrentLogs().size(), 0);
    }

    @Test // threshold test - append an equal or greater priority
    public void test4(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.clearLogs();
        memAppender.setThreshold(Level.ERROR);
        memAppender.append(new LoggingEvent("ErrorLog", logger, Level.ERROR, "This is a threshold test ", null));
        memAppender.append(new LoggingEvent("FatalLog", logger, Level.FATAL, "This is a threshold test ", null));
        Assert.assertEquals(memAppender.getCurrentLogs().size(), 2); //added 2 logs of Level ERROR +
    }

    @Test
    public void test5(){
        MemAppender memAppender5 = new MemAppender("MA");
        System.out.println(memAppender5.getName());
        String name = "NEW NAME";
        memAppender5.clearLogs();
        memAppender5.setName(name);
        System.out.println(memAppender5.getName());
        Assert.assertEquals(memAppender5.getName(), name);
    }


}
