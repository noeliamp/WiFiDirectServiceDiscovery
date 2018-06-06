package discovery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Noelia on 27/05/2016.
 */
public class Connection {

    private long connecting;
    private long conIni;
    private long disconnecting;
    private long conEnd;
    private long conDur;
    private long failure;

    private ArrayList<MessageRow> messages = new ArrayList<MessageRow>();
    private String messagesString;
    private String connectedPeer = "";
    private static int numCon = 0;
    private int id;
    private boolean groupOwner= false;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
    private boolean failed = false;
    private boolean initiator;

    public Connection(boolean initiator) {
        this.id = numCon++;
        this.initiator = initiator;
        this.conIni = 0;
    }

    public Connection(String id, Boolean go, Boolean initiator, Boolean failed, Long failure, String connectedPeer, Long connecting, Long connectionInit, Long disconnecting, Long connectionEnd, String connectionMessages) {
        this.id = Integer.parseInt(id);
        this.groupOwner = go;
        this.initiator= initiator;
        this.failed= failed;
        this.failure = failure;
        this.connectedPeer= connectedPeer;
        this.connecting = connecting;
        this.conIni = connectionInit;
        this.disconnecting= disconnecting;
        this.conEnd = connectionEnd;
        this.messagesString= connectionMessages;


    }

    public void setConnecting(long connecting) {
        this.connecting = connecting;
    }

    public int getId() {
        return id;
    }

    public long getConIni() {
        return conIni;
    }

    public long getConEnd() {
        return conEnd;
    }

    public void setConEnd(long conEnd) {
        this.conEnd = conEnd;
    }

    public void setConIni(long conIni) {
        this.conIni = conIni;
    }

    public long getConDur() {
        return conDur;
    }

    public boolean isInitiator() {
        return initiator;
    }

    public long getFailure() {
        return failure;
    }

    public void setFailure(long failure) {
        this.failure = failure;
    }

    public boolean isGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(boolean groupOwner) {
        this.groupOwner = groupOwner;
    }

    public void setConnectedPeer(String connectedPeer) {
        this.connectedPeer = connectedPeer;
    }

    public ArrayList<MessageRow> getMessages() {
        return messages;
    }

    public void addMessage(MessageRow row){
        this.messages.add(row);
    }

    public String getConnectedPeer() {
        return connectedPeer;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public long getConnecting() {
        return connecting;
    }

    public long getDisconnecting() {
        return disconnecting;
    }

    public void setDisconnecting(long disconnecting) {
        this.disconnecting = disconnecting;
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();
        JSONObject jsonObjectRow = new JSONObject();
        JSONArray jsonArrayRow = new JSONArray();


        try {
            jsonObject.put("Connection ID", getId());
            jsonObject.put("Group owner?", isGroupOwner());
            jsonObject.put("Initiator", isInitiator());
            jsonObject.put("Failed", isFailed());
            jsonObject.put("Failure", sdf.format(getFailure()));

            jsonObject.put("Connected Peer", getConnectedPeer());

            jsonObject.put("Connecting", sdf.format(getConnecting()));
            jsonObject.put("Connection Init", sdf.format(getConIni()));
            jsonObject.put("Disconnecting", sdf.format(getDisconnecting()));
            jsonObject.put("Connection End", sdf.format(getConEnd()));

            for (MessageRow row : messages) {
                jsonObjectRow = row.getAsJSONObject(row);
                jsonArrayRow.put(jsonObjectRow);
            }
            jsonObject.put("Connection Messages", jsonArrayRow);

//            jsonObject.put("Exchange Init", getExchIni());
//            jsonObject.put("Exchange End", getExchEnd());
//            jsonObject.put("Exchange Duration", getExchDur());
//            jsonObject.put("List of messages", getMessages());


            return jsonObject; //jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
