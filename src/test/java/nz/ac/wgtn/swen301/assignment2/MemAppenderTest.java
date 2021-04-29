package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemAppenderTest {

    @Test
    public void test1(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
        memAppender.append(lg);
        memAppender.exportToJSON("logs.json");
        memAppender.close();
    }

    @Test
    public void test2(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        long maxSize = memAppender.getMaxSize();
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        memAppender.exportToJSON("something.json");
        Assert.assertEquals(memAppender.getCurrentLogs().size(), maxSize);
        memAppender.close();
    }

    @Test // threshold test - append a lower priority
    public void test3(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.setThreshold(Level.ERROR);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test ", null);
        memAppender.append(lg);
        Assert.assertEquals(memAppender.getCurrentLogs().size(), 0);
        memAppender.close();
    }

    @Test // threshold test - append an equal or greater priority
    public void test4(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.setThreshold(Level.ERROR);
        memAppender.append(new LoggingEvent("ErrorLog", logger, Level.ERROR, "This is a threshold test ", null));
        memAppender.append(new LoggingEvent("FatalLog", logger, Level.FATAL, "This is a threshold test ", null));
        Assert.assertEquals(memAppender.getCurrentLogs().size(), 2); //added 2 logs of Level ERROR +
        memAppender.close();
    }

    @Test // tests memAppender is created correctly and is initialised correctly
    public void test5(){
        String name = "test5";
        MemAppender memAppender = new MemAppender(name);
        memAppender.clearLogs();
        Assert.assertTrue(memAppender != null);
        Assert.assertEquals(memAppender.getName(), name);
        memAppender.close();
    }

    @Test // tests name is not null and is correctly initialised in constructor
    public void test6(){
        String name = "test6";
        MemAppender memAppender = new MemAppender(name);
        Assert.assertNotNull(memAppender.getName());
        String newName = "NEW NAME";
        memAppender.setName(newName);
        Assert.assertEquals(memAppender.getName(), newName);
    }

    @Test (expected = IllegalArgumentException.class) // max size cannot be negative
    public void test7(){
        MemAppender memAppender = new MemAppender("MA");
        memAppender.setMaxSize(-1);
    }

    @Test
    public void test8(){
        MemAppender memAppender = new MemAppender("MA");
        Assert.assertEquals(memAppender.getMaxSize(), 1000);
        memAppender.setMaxSize(600);
        Assert.assertEquals(memAppender.getMaxSize(), 600);
    }

    @Test
    public void test9(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.clearLogs();
        long maxSize = memAppender.getMaxSize() + 100;
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        memAppender.exportToJSON("something.json");
        Assert.assertEquals(memAppender.getDiscardedLogCount(), 100);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testUnmodifiable(){
        MemAppender memAppender = new MemAppender("Unmodifiable");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        for(int i = 0; i < 10; i++){
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        List<LoggingEvent> eventLogs = memAppender.getCurrentLogs();
        eventLogs.add(new LoggingEvent("This should not work", logger, Level.WARN, "Is this a test?", null));
    }

    @Test (expected = Exception.class)
    public void testIOException(){
        MemAppender memAppender = new MemAppender("IOEXCEPTION");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
        memAppender.append(lg);
        memAppender.exportToJSON(null);
    }

    @Test
    public void testMaxSizeChange(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        long maxSize = memAppender.getMaxSize();
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        Assert.assertEquals(memAppender.getCurrentLogs().size(), maxSize);
        memAppender.setMaxSize(900);
        Assert.assertTrue(memAppender.getCurrentLogs().size() == 900);
        Assert.assertEquals(memAppender.getDiscardedLogCount(), 100);
        memAppender.close();
    }



}
