package discovery;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Noelia on 02/10/2017.
 */

public class GeoEvent {
    public String action;
    public String time;

    public GeoEvent(String action, String time) {
        this.action = action;
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
            jsonObject.put("Action", getAction());
            jsonObject.put("Time", getTime());

            return jsonObject; //jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
