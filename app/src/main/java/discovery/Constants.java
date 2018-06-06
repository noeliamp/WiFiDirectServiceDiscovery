package discovery;

import android.net.Uri;

import com.google.android.gms.location.Geofence;

public final class Constants {
	
	public static final String PACKAGE_NAME = Constants.class.getPackage().getName();
	
	public static final int MSG_NULL = 0;
	public static final int MSG_STARTSERVER = 1001;
	public static final int MSG_STARTCLIENT = 1002;
	public static final int MSG_CONNECT = 1003;
	public static final int MSG_DISCONNECT = 1004;   // p2p disconnect
	public static final int MSG_PUSHOUT_DATA = 1005;
	public static final int MSG_NEW_CLIENT = 1006;
	public static final int MSG_FINISH_CONNECT = 1007;
	public static final int MSG_PULLIN_DATA = 1008;
	public static final int MSG_REGISTER_ACTIVITY = 1009;
	
	public static final int MSG_SELECT_ERROR = 2001;
	public static final int MSG_BROKEN_CONN = 2002;  // network disconnect
	
	public static final int MSG_SIZE = 50;    // the lastest 50 messages
	public static final String MSG_SENDER = "sender";
	public static final String MSG_TIME = "time";
	public static final String MSG_MSG = "msg";
	public static final String MSG_UUID = "uuid";
	public static final String MSG_TTL = "ttl";
    public static final String MSG_PATH = "path";
    public static final String MSG_CONN = "connection";


    /////////////////////// geofencing constants

    // Request code to attempt to resolve Google Play services connection failures.
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    // Timeout for making a connection to GoogleApiClient (in milliseconds).
    public static final long CONNECTION_TIME_OUT_MS = 100;

    // For the purposes of this demo, the geofences are hard-coded and should not expire.
    // An app with dynamically-created geofences would want to include a reasonable expiration time.
    public static final long GEOFENCE_EXPIRATION_TIME = Geofence.NEVER_EXPIRE;

    // Geofence parameters for IMDEA's main building in Leganés.
    public static final String ANDROID_BUILDING_ID = "1";
    public static final double ANDROID_BUILDING_LATITUDE = 40.3392616;
    public static final double ANDROID_BUILDING_LONGITUDE = -3.7703349;
    public static final float ANDROID_BUILDING_RADIUS_METERS = 500.0f;


    // The constants below are less interesting than those above.

    // Path for the DataItem containing the last geofence id entered.
    public static final String GEOFENCE_DATA_ITEM_PATH = "/geofenceid";
    public static final Uri GEOFENCE_DATA_ITEM_URI =
            new Uri.Builder().scheme("wear").path(GEOFENCE_DATA_ITEM_PATH).build();
    public static final String KEY_GEOFENCE_ID = "geofence_id";

    // Keys for flattened geofences stored in SharedPreferences.
    public static final String KEY_LATITUDE = "com.example.wearable.geofencing.KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "com.example.wearable.geofencing.KEY_LONGITUDE";
    public static final String KEY_RADIUS = "com.example.wearable.geofencing.KEY_RADIUS";
    public static final String KEY_EXPIRATION_DURATION =
            "com.example.wearable.geofencing.KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION_TYPE =
            "com.example.wearable.geofencing.KEY_TRANSITION_TYPE";
    // The prefix for flattened geofence keys.
    public static final String KEY_PREFIX = "com.example.wearable.geofencing.KEY";

    // Invalid values, used to test geofence storage when retrieving geofences.
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

	// analytics tracking category, action, label and value
	public static final String CAT_LOCATION = "LocationRule";
	public static final String ACT_CREATE = "Create";
	public static final String ACT_DELETE = "Delete";
	public static final String LAB_HOME = "HomeRule";
	public static final String LAB_WORK = "WorkRule";
	public static final String LAB_OTHER = "OtherRule";

}
