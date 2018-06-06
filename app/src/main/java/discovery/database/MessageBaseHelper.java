package discovery.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import discovery.database.MessageDbSchema.MessageTable;
import discovery.database.MessageDbSchema.ScanTable;
import discovery.database.MessageDbSchema.ConnectionTable;
import discovery.database.MessageDbSchema.GeoTable;
import discovery.database.MessageDbSchema.BatteryTable;

/**
 * Created by Noelia on 13/04/2016.
 */
public class MessageBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "messageBase.db";

    public MessageBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + MessageTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                MessageTable.Cols.UUID + ", " +
                MessageTable.Cols.MSG + ", " +
                MessageTable.Cols.SENDER + ", " +
                MessageTable.Cols.TIME + ", " +
                MessageTable.Cols.TTL + ", " +
                MessageTable.Cols.PATH +
                ")");
        db.execSQL("create table " + ScanTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ScanTable.Cols.ID + ", " +
                ScanTable.Cols.SCAN_INIT + ", " +
                ScanTable.Cols.SCAN_END + ", " +
                ScanTable.Cols.SCAN_DURATION + ", " +
                ScanTable.Cols.PEERS +
                ")");
        db.execSQL("create table " + ConnectionTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                ConnectionTable.Cols.ID + ", " +
                ConnectionTable.Cols.GO + ", " +
                ConnectionTable.Cols.INITIATOR + ", " +
                ConnectionTable.Cols.FAILED + ", " +
                ConnectionTable.Cols.FAILURE + ", " +
                ConnectionTable.Cols.CONNECTED_PEER + ", " +
                ConnectionTable.Cols.CONNECTING + ", " +
                ConnectionTable.Cols.CONNECTION_INIT + ", " +
                ConnectionTable.Cols.DISCONNECTING + ", " +
                ConnectionTable.Cols.CONNECTION_END + ", " +
                ConnectionTable.Cols.CONNECTION_MESSAGES +
                ")");
        db.execSQL("create table " + GeoTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                GeoTable.Cols.ACTION + ", " +
                GeoTable.Cols.TIME +
                ")");
        db.execSQL("create table " + BatteryTable.NAME + "(" +
                "_id integer primary key autoincrement, " +
                BatteryTable.Cols.PERCENTAGE + ", " +
                BatteryTable.Cols.TIME +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}
