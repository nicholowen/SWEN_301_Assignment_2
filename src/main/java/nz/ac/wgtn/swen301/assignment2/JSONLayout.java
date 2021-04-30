package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import java.util.LinkedHashMap;
import java.util.Map;

public class JSONLayout extends Layout {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public JSONLayout(){
    }

    @Override
    public String format(LoggingEvent loggingEvent) {
        Map<String, Object> jsonObj = new LinkedHashMap<>();

        jsonObj.put("logger", loggingEvent.getLoggerName());
        jsonObj.put("level", loggingEvent.getLevel().toString());
        jsonObj.put("starttime", loggingEvent.getTimeStamp());
        jsonObj.put("thread", loggingEvent.getThreadName());
        jsonObj.put("message", loggingEvent.getMessage());

        //using gson to parse map object to json
        return gson.toJson(jsonObj);
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }
}
