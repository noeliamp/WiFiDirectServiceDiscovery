package discovery;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Noelia on 27/05/2016.
 */
public class ServerInfo extends Activity {

    private static final String TAG = "PTP_ServerInfo";

    private ArrayList<Connection> connections = new ArrayList<Connection>();
    private ArrayList<Scan> scans = new ArrayList<Scan>();
    private ArrayList<MessageRow> messages = new ArrayList<MessageRow>();
    private ArrayList<BatteryEvent> batteryLog = new ArrayList<>();
    private ArrayList<GeoEvent> geofenceEvents = new ArrayList<>();
    private String myName;



    public ServerInfo(String myName, ArrayList<Connection> connections, ArrayList<Scan> scans, ArrayList<MessageRow> messages, ArrayList<BatteryEvent> batteryLog,ArrayList<GeoEvent> geofenceEvents){
        this.myName = myName;
        this.connections = connections;
        this.scans = scans;
        this.messages = messages;
        this.batteryLog = batteryLog;
        this.geofenceEvents = geofenceEvents;
    }

    public String toJSON() {

        JSONObject mainJsonObject = new JSONObject();
        JSONObject jsonObjectCon = new JSONObject();
        JSONObject jsonObjectSca = new JSONObject();
        JSONObject jsonObjectGeo = new JSONObject();
        JSONObject jsonObjectBat = new JSONObject();
        JSONObject jsonObjectMes = new JSONObject();
        JSONArray jsonArrayCon = new JSONArray();
        JSONArray jsonArraySca = new JSONArray();
        JSONArray jsonArrayGeo = new JSONArray();
        JSONArray jsonArrayBat = new JSONArray();
        JSONArray jsonArrayMes = new JSONArray();


        String connectionsString = "";

        try {
            mainJsonObject.put("number of messages", messages.size());
            mainJsonObject.put("Messages: ", jsonArrayMes);
            for (MessageRow messageRow : messages) {
                jsonObjectMes = messageRow.getAsJSONObject(messageRow);
                jsonArrayMes.put(jsonObjectMes);
            }

            mainJsonObject.put("geofence Events", jsonArrayGeo);
            for (GeoEvent geoEvent : geofenceEvents) {
                jsonObjectGeo = geoEvent.toJSON();
                jsonArrayGeo.put(jsonObjectGeo);
            }

            mainJsonObject.put("battery", jsonArrayBat);
            for (BatteryEvent batteryEvent : batteryLog) {
                jsonObjectBat = batteryEvent.toJSON();
                jsonArrayBat.put(jsonObjectBat);
            }

            mainJsonObject.put("number of connections", connections.size());
            mainJsonObject.put("connections", jsonArrayCon);
            for (Connection connection : connections) {
                jsonObjectCon = connection.toJSON();
                jsonArrayCon.put(jsonObjectCon);
            }

            mainJsonObject.put("number of scans", this.scans.size());
            mainJsonObject.put("scans", jsonArraySca);
            for (Scan scan : scans) {
                jsonObjectSca = scan.toJSON();
                jsonArraySca.put(jsonObjectSca);
            }

            return mainJsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
