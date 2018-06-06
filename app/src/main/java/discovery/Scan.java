package discovery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Noelia on 16/06/2016.
 */
public class Scan {

    private long scanIni;
    private long scanEnd;
    private long scanDur;
    private static int numScan = 0;
    private int id;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
    private ArrayList<String> peers = new ArrayList();


    public Scan(long scanIni)  {
        id=numScan++;
        this.scanIni = scanIni;

    }

    public Scan(String id, Long scanInit, Long scanEnd, Long scanDur) {
        this.id= Integer.parseInt(id);
        this.scanIni = scanInit;
        this.scanEnd= scanEnd;
        this.scanDur=scanDur;
    }


    public int getId() {
        return id;
    }

    public long getScanIni() {
        return scanIni;
    }

    public long getScanEnd() {
        return scanEnd;
    }

    public long getScanDur() {
        return scanDur;
    }

    public void setScanDur() {
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT 2"));
        Date date1 = new Date(scanIni);
        Date date2 = new Date(scanEnd);
        this.scanDur = date2.getTime() - date1.getTime();
        Date date = new Date(scanDur);
    }

    public void setScanEnd(long scanEnd) {
        this.scanEnd = scanEnd;

    }

    public void setPeers(ArrayList<String> peers) {
        this.peers = peers;
    }


    public ArrayList<String> getPeers() {
        return peers;
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonObject.put("Scan ID", getId());
            jsonObject.put("Scan Init", sdf.format(getScanIni()));
            jsonObject.put("Scan End", sdf.format(getScanEnd()));
            jsonObject.put("Scan Duration", sdf.format(getScanDur()));

            for (String peer : getPeers()){
                //String sub = peer.device.deviceName.substring(peer.device.deviceName.lastIndexOf('_') + 1);
                jsonArray.put(peer);
            }
            jsonObject.put("Peers", jsonArray);

            return jsonObject; //jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject peersToJSON(){

        JSONObject jsonObject= new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            for (String peer : peers){
                //String sub = peer.device.deviceName.substring(peer.device.deviceName.lastIndexOf('_') + 1);
                jsonArray.put(peer);
            }
            jsonObject.put("Peers", jsonArray);

            return jsonObject; //jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    /**
     * convert a json string representation of array list into arrayList.
     */
    public static ArrayList<String> parsePeers(String jsonPeers){
        ArrayList<String> peers = new ArrayList<>();

        if(!jsonPeers.isEmpty()) {
            jsonPeers = jsonPeers.replaceAll("[^\\d,]", "");
            peers = new ArrayList(Arrays.asList(jsonPeers.split(",")));
        }
        return peers;
    }
}
