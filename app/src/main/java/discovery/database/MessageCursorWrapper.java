package discovery.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.text.SimpleDateFormat;
import java.util.UUID;

import discovery.BatteryEvent;
import discovery.Connection;
import discovery.GeoEvent;
import discovery.MessageRow;
import discovery.Scan;
import discovery.database.MessageDbSchema.BatteryTable;
import discovery.database.MessageDbSchema.ConnectionTable;
import discovery.database.MessageDbSchema.GeoTable;
import discovery.database.MessageDbSchema.MessageTable;
import discovery.database.MessageDbSchema.ScanTable;

import static discovery.Scan.parsePeers;


/**
 * Created by Noelia on 14/04/2016.
 */
public class MessageCursorWrapper extends CursorWrapper {
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    public MessageCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public MessageRow getMessage(){
        String uuid = getString(getColumnIndex(MessageTable.Cols.UUID));
        String msg = getString(getColumnIndex(MessageTable.Cols.MSG));
        String sender = getString(getColumnIndex(MessageTable.Cols.SENDER));
        String time = getString(getColumnIndex(MessageTable.Cols.TIME));
        String ttl = getString(getColumnIndex(MessageTable.Cols.TTL));
        String path = getString(getColumnIndex(MessageTable.Cols.PATH));


        MessageRow message = new MessageRow(UUID.fromString(uuid), sender, msg, time, ttl, path);
        return message;

    }

    public Connection getConnection(){
        String id = getString(getColumnIndex(ConnectionTable.Cols.ID));
        String go = getString(getColumnIndex(ConnectionTable.Cols.GO));
        String initiator = getString(getColumnIndex(ConnectionTable.Cols.INITIATOR));
        String failed = getString(getColumnIndex(ConnectionTable.Cols.FAILED));
        Long failure = getLong(getColumnIndex(ConnectionTable.Cols.FAILURE));
        String connectedPeer = getString(getColumnIndex(ConnectionTable.Cols.CONNECTED_PEER));
        Long connecting = getLong(getColumnIndex(ConnectionTable.Cols.CONNECTING));
        Long connectionInit = getLong(getColumnIndex(ConnectionTable.Cols.CONNECTION_INIT));
        Long disconnecting = getLong(getColumnIndex(ConnectionTable.Cols.DISCONNECTING));
        Long connectionEnd = getLong(getColumnIndex(ConnectionTable.Cols.CONNECTION_END));
        String connectionMessages = getString(getColumnIndex(ConnectionTable.Cols.CONNECTION_MESSAGES));

        boolean a = "1".equals(initiator);
        boolean b = "1".equals(go);
        boolean c = "1".equals(failed);

        Connection connection = new Connection(id, b, a, c,
                failure, connectedPeer, connecting, connectionInit, disconnecting, connectionEnd, connectionMessages);
        return connection;

    }

    public Scan getScan(){
        String id = getString(getColumnIndex(ScanTable.Cols.ID));
        Long scanInit = getLong(getColumnIndex(ScanTable.Cols.SCAN_INIT));
        Long scanEnd = getLong(getColumnIndex(ScanTable.Cols.SCAN_END));
        Long scanDur = getLong(getColumnIndex(ScanTable.Cols.SCAN_DURATION));
        String peers = getString(getColumnIndex(ScanTable.Cols.PEERS));


        Scan scan = new Scan(id, scanInit, scanEnd, scanDur);
        scan.setPeers(parsePeers(peers));

        //scan.setPeers(listdata);
        return scan;

    }

    public GeoEvent getGeo() {

        String action = getString(getColumnIndex(GeoTable.Cols.ACTION));
        String time = getString(getColumnIndex(GeoTable.Cols.TIME));

        GeoEvent geoEvent = new GeoEvent(action,time);
        return geoEvent;
    }

    public BatteryEvent getBattery() {

        int percentage = getInt(getColumnIndex(BatteryTable.Cols.PERCENTAGE));
        String time = getString(getColumnIndex(BatteryTable.Cols.TIME));

        BatteryEvent batteryEvent = new BatteryEvent(percentage,time);
        return batteryEvent;

    }

}
