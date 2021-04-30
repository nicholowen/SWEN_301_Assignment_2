package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;

import javax.management.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MemAppender extends AppenderSkeleton implements MemAppenderMBean{

    private List<LoggingEvent> loggingEvents =  new ArrayList<>(); //stores logging events
    private long maxSize = 1000;
    private long discardedLogs = 0;

    public MemAppender(String name){
//        clearLogs();
//        discardedLogs = 0;
//        this.maxSize = 1000;
        this.setName(name);
    }

    @Override
    public void append(LoggingEvent loggingEvent) {
        if (this.getThreshold() != null && !loggingEvent.getLevel().isGreaterOrEqual(this.getThreshold())) return;
        loggingEvents.add(loggingEvent);
        if (loggingEvents.size() > maxSize) {
            loggingEvents.remove(0); // removes the zeroth event log (the first added/oldest).
            discardedLogs++; // counts how many logs have been discarded.
        }
    }

    //Logs can be accessed using the following non-static method
    //This returns a list of all logs (max 1000)
    public List<LoggingEvent> getCurrentLogs(){
        return Collections.unmodifiableList(loggingEvents);
    }

    @Override
    public String[] getLogs() {
        Layout layout = new PatternLayout();
        String[] logs = new String[(int)getLogCount()];
        for (int i = 0; i < loggingEvents.size(); i++){
            String s = layout.format(loggingEvents.get(i));
            logs[i] = s;
        }
        return logs;
    }

    @Override
    public long getLogCount() {
        return loggingEvents.size();
    }

    @Override
    public long getDiscardedLogCount(){
        return discardedLogs;
    }

    /*
    This method exports the log events currently stored into a JSON file.
    The JSON file should contain a JSON array containing JSON objects.
    Each JSON object represents a log event, the log
     */
    public void exportToJSON(String fileName) {
        if(loggingEvents.size() > 0) {
            try (FileWriter file = new FileWriter(fileName + ".json")) {

                file.write("[\n");

                //writes a all logging events to file after being formatted by JSONLayout
                JSONLayout layout = new JSONLayout();
                for (int i = 0; i < loggingEvents.size(); i++) {
                    String formatted = layout.format(loggingEvents.get(i));
                    file.write(formatted);
                    //if (i < loggingEvents.size() - 1)
                        file.write(",\n");
                }
                file.write("\n]");
                file.flush();
                this.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        this.closed = true;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public void clearLogs(){
        loggingEvents.clear();
    }

    public long getMaxSize(){
        return this.maxSize;
    }

    public void setMaxSize(long maxSize){
        if(maxSize < 0) throw new IllegalArgumentException();
        if(loggingEvents != null) {
            while (loggingEvents.size() > maxSize) {
                loggingEvents.remove(0);
                discardedLogs++;
            }
        }
        this.maxSize = maxSize;
    }

}
