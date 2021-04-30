package nz.ac.wgtn.swen301.assignment2;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Assert;
import org.junit.Test;

public class JSONLayoutTest {

    @Test // reparses a json file converted back to json object to ensure it is a valid json file and properties match.
    public void test_ParsedValuesAssertEquals(){
        JSONLayout layout = new JSONLayout();
        Logger logger = Logger.getLogger(JSONLayout.class);
        LoggingEvent lg = new LoggingEvent("JSONLayoutTest", logger, Level.WARN, "Warning, this is a test!", null);
        String json = layout.format(lg);
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        Assert.assertEquals(jsonObject.get("level").toString(), "\"WARN\"");
        Assert.assertEquals(jsonObject.get("message").toString(), "\"Warning, this is a test!\"" );
        Assert.assertEquals(jsonObject.get("logger").toString(), "\"nz.ac.wgtn.swen301.assignment2.JSONLayout\"");
    }


}
