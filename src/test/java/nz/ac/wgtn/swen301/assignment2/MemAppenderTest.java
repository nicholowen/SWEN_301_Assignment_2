package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MemAppenderTest {

    @Test
    public void test_1000LogAppend(){
        MemAppender memAppender = new MemAppender("test_1000LogAppend");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        long maxSize = memAppender.getMaxSize();
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            logger.log(Level.WARN, "this is a test " + i);
        }
        memAppender.exportToJSON("1000logs");
        Assert.assertEquals(memAppender.getCurrentLogs().size(), maxSize);
    }

    @Test // threshold test - append a lower priority
    public void test_BelowLevelThreshold(){
        MemAppender memAppender = new MemAppender("test_BelowLevelThreshold");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        memAppender.setThreshold(Level.ERROR);
        logger.log(Level.ERROR, "This is a threshold test");
        logger.log(Level.WARN, "Another Test");
        Assert.assertEquals(1, memAppender.getCurrentLogs().size());
    }

    @Test // threshold test - append an equal or greater priority
    public void test_AboveLevelThreshold(){
        MemAppender memAppender = new MemAppender("test_AboveLevelThreshold");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        memAppender.setThreshold(Level.ERROR);
        logger.log(Level.ERROR, "This is a threshold test");
        logger.log(Level.FATAL, "This is a threshold test");
        Assert.assertEquals(2, memAppender.getCurrentLogs().size()); //added 2 logs of Level > ERROR
    }

    @Test // tests memAppender is created correctly and is initialised correctly
    public void test_NameInitialisation(){
        String name = "test_NameInitialisation";
        MemAppender memAppender = new MemAppender(name);
        Assert.assertTrue(memAppender != null);
        Assert.assertEquals(memAppender.getName(), name);
    }

    @Test // tests name is not null and is correctly initialised in constructor
    public void test_SetName(){
        String name = "test_SetName";
        MemAppender memAppender = new MemAppender(name);
        Assert.assertNotNull(memAppender.getName());
        String newName = "NEW NAME";
        memAppender.setName(newName);
        Assert.assertEquals(memAppender.getName(), newName);
    }

    @Test (expected = IllegalArgumentException.class) // max size cannot be negative
    public void test_NegativeMaxSize(){
        MemAppender memAppender = new MemAppender("test_NegativeMaxSize");
        memAppender.setMaxSize(-1);
    }

    @Test //change max size
    public void test_SetMaxSize(){
        MemAppender memAppender = new MemAppender("test_SetMaxSize");
        Assert.assertEquals(memAppender.getMaxSize(), 1000);
        memAppender.setMaxSize(600);
        Assert.assertEquals(memAppender.getMaxSize(), 600);
    }

    @Test // tests discarded log count operates correctly
    public void test_DiscardedLogCount(){
        MemAppender memAppender = new MemAppender("test_DiscardedLogCount");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        long maxSize = memAppender.getMaxSize() + 100;
        for(int i = 0; i < maxSize; i++){ //logs 1100 events (max size is still 1000)
            memAppender.append(new LoggingEvent("Some will be deleted", logger, Level.WARN, "Is this a test?", null));
        }
        Assert.assertEquals(100, memAppender.getDiscardedLogCount());
    }

    @Test (expected = UnsupportedOperationException.class) // cannot modify an unmodifiable list
    public void test_ModifyUnmodifiableListFail(){
        MemAppender memAppender = new MemAppender("test_ModifyUnmodifiableListFail");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        for(int i = 0; i < 10; i++){
            logger.log(Level.WARN, "this is a test " + i);
        }
        List<LoggingEvent> eventLogs = memAppender.getCurrentLogs();
        eventLogs.add(new LoggingEvent("This should not work", logger, Level.WARN, "Is this a test?", null));
    }


    @Test // ensures correct number of logs are removed when max size is changed to a lower max size
    public void test_MaxSizeChangeWithPopulatedList(){
        MemAppender memAppender = new MemAppender("test_MaxSizeChangeWithPopulatedList");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        long maxSize = memAppender.getMaxSize();
        for(int i = 0; i < maxSize; i++){ //logs 1000 events
            logger.log(Level.INFO, "this is a test " + i);
        }
        Assert.assertEquals(memAppender.getCurrentLogs().size(), maxSize);
        memAppender.setMaxSize(900);
        Assert.assertEquals(900,memAppender.getCurrentLogs().size());
        Assert.assertEquals(100, memAppender.getDiscardedLogCount());
    }

    @Test // test functionality of getLogs method
    public void test_GetLogs(){
        MemAppender memAppender = new MemAppender("test_GetLogs");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        logger.log(Level.WARN, "this is a test");
        Assert.assertTrue(memAppender.getLogs().length == 1);
        Assert.assertEquals("this is a test\n", memAppender.getLogs()[0]);
    }

    @Test //test functionality of logCount method
    public void test_LogCount(){
        MemAppender memAppender = new MemAppender("test_LogCount");
        Logger logger = Logger.getLogger(MemAppenderTest.class);
        logger.addAppender(memAppender);
        logger.log(Level.DEBUG, "this is a test");
        Assert.assertEquals(1, memAppender.getLogCount());
    }

}
