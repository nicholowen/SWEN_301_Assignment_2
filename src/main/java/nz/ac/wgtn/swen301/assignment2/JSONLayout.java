package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.HashMap;
import java.util.Map;

public class JSONLayout extends Layout {
    private String logger;
    private String level;
    private long starttime;
    private String thread;
    private String message;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public JSONLayout(){
    }

    @Override
    public String format(LoggingEvent loggingEvent) {
        try {
            Map<String, Object> jsonObj = new HashMap<>();

            jsonObj.put("logger", loggingEvent.getLoggerName());
            jsonObj.put("level", loggingEvent.getLevel());
            jsonObj.put("starttime", loggingEvent.getTimeStamp());
            jsonObj.put("thread", loggingEvent.getThreadName());
            jsonObj.put("Message", loggingEvent.getMessage());

            //don't really see the point in this gson to json though... maybe just for pretty?
            return gson.toJson(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }
}
