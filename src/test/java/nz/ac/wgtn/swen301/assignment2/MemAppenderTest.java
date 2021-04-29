package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
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
    public void test1000LogAppend(){
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
    public void testBelowLevelThreshold(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.setThreshold(Level.ERROR);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test ", null);
        memAppender.append(lg);
        Assert.assertEquals(0, memAppender.getCurrentLogs().size());
        memAppender.close();
    }

    @Test // threshold test - append an equal or greater priority
    public void testAboveLevelThreshold(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        memAppender.setThreshold(Level.ERROR);
        memAppender.append(new LoggingEvent("ErrorLog", logger, Level.ERROR, "This is a threshold test ", null));
        memAppender.append(new LoggingEvent("FatalLog", logger, Level.FATAL, "This is a threshold test ", null));
        Assert.assertEquals(2, memAppender.getCurrentLogs().size()); //added 2 logs of Level > ERROR
        memAppender.close();
    }

    @Test // tests memAppender is created correctly and is initialised correctly
    public void testNameInitialisation(){
        String name = "test5";
        MemAppender memAppender = new MemAppender(name);
        Assert.assertTrue(memAppender != null);
        Assert.assertEquals(memAppender.getName(), name);
        memAppender.close();
    }

    @Test // tests name is not null and is correctly initialised in constructor
    public void testSetName(){
        String name = "test6";
        MemAppender memAppender = new MemAppender(name);
        Assert.assertNotNull(memAppender.getName());
        String newName = "NEW NAME";
        memAppender.setName(newName);
        Assert.assertEquals(memAppender.getName(), newName);
    }

    @Test (expected = IllegalArgumentException.class) // max size cannot be negative
    public void testNegativeMaxSize(){
        MemAppender memAppender = new MemAppender("MA");
        memAppender.setMaxSize(-1);
    }

    @Test
    public void testSetMaxSize(){
        MemAppender memAppender = new MemAppender("MA");
        Assert.assertEquals(memAppender.getMaxSize(), 1000);
        memAppender.setMaxSize(600);
        Assert.assertEquals(memAppender.getMaxSize(), 600);
        memAppender.close();
    }

    @Test
    public void testDiscardedLogCount(){
        MemAppender memAppender = new MemAppender("MA");
        memAppender.clearLogs();
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        long maxSize = memAppender.getMaxSize() + 100;
        for(int i = 0; i < maxSize; i++){ //logs 1100 events (max size is still 1000)
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        memAppender.exportToJSON("something.json");
        Assert.assertEquals(100, memAppender.getDiscardedLogCount());
        memAppender.close();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testModifyUnmodifiableListFail(){
        MemAppender memAppender = new MemAppender("Unmodifiable");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        for(int i = 0; i < 10; i++){
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        List<LoggingEvent> eventLogs = memAppender.getCurrentLogs();
        memAppender.close(); // close appender to ensure next test starts with default values
        eventLogs.add(new LoggingEvent("This should not work", logger, Level.WARN, "Is this a test?", null));
    }

    @Test (expected = Exception.class)
    public void testFileWriterIOExceptionPass(){
        MemAppender memAppender = new MemAppender("IOEXCEPTION");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
        memAppender.append(lg);
        memAppender.exportToJSON(null);
    }

    @Test
    public void testMaxSizeChangeWithPopulatedList(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        long maxSize = memAppender.getMaxSize();
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            LoggingEvent lg = new LoggingEvent("Test" + i, logger, Level.WARN, "this is a test " + i, null);
            memAppender.append(lg);
        }
        Assert.assertEquals(memAppender.getCurrentLogs().size(), maxSize);
        memAppender.setMaxSize(900);
        Assert.assertEquals(900,memAppender.getCurrentLogs().size());
        Assert.assertEquals(100, memAppender.getDiscardedLogCount());
        memAppender.close();
    }

    @Test
    public void testGetLogs(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
        memAppender.append(lg);
        Assert.assertTrue(memAppender.getLogs().length == 1);
        Assert.assertEquals("this is a test\n", memAppender.getLogs()[0]);
        memAppender.close();
    }

    @Test
    public void testLogCount(){
        MemAppender memAppender = new MemAppender("MA");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        LoggingEvent lg = new LoggingEvent("Test", logger, Level.WARN, "this is a test", null);
        memAppender.append(lg);
        Assert.assertEquals(1, memAppender.getLogCount());
        memAppender.close();
    }
//
//    @After
//    public void runMbean() {
//        MemAppender ma = new MemAppender("MBeanTest");
//        ma.runMBean();
//    }
}
