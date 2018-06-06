package discovery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Noelia on 02/10/2017.
 */

public class BatteryEvent {

    private int percentage;
    private String time;

    public BatteryEvent(int percentage, String time) {
        this.percentage = percentage;
        this.time = time;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();

        try {
            jsonObject.put("Percentage", getPercentage());
            jsonObject.put("Time", getTime());

            return jsonObject; //jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
