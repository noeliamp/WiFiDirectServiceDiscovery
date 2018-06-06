package discovery;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import discovery.WiFiServiceDiscoveryActivity.PTPLog;

import static discovery.Constants.MSG_MSG;
import static discovery.Constants.MSG_PATH;
import static discovery.Constants.MSG_SENDER;
import static discovery.Constants.MSG_TIME;
import static discovery.Constants.MSG_TTL;
import static discovery.Constants.MSG_UUID;

public class MessageRow implements Parcelable {
	private final static String TAG = "PTP_MSG";

    public UUID mUuid;
	public String mSender;
	public String mMsg;
	public String mTime;
    public String mTtl;
	public static final String mDel = "^&^";
    public String mPath;


	public MessageRow(UUID uuid, String sender, final String msg, String time, String ttl, String path){
		mTime = time;
		if( time == null ){
			Date now = new Date();
			//SimpleDateFormat timingFormat = new SimpleDateFormat("mm/dd hh:mm");
			//mTime = new SimpleDateFormat("dd/MM HH:mm").format(now);
			mTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(now);

		}
		mUuid = uuid;
        if (uuid == null ){
            mUuid = UUID.randomUUID();
        }
        mTtl = ttl;
        if (ttl == null){
            Date set = new Date();
            set.setHours(set.getHours() + 1);
            mTtl = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(set);
        }
		String sub = sender.substring(sender.lastIndexOf('_') + 1);
		mSender = sub;
		mMsg = msg;
        mPath = path;

    }

    public String getTtl() {
        return mTtl;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String mMsg) {
        this.mMsg = mMsg;
    }

    public String getTime() {
        return mTime;
    }


	public String getSender() {return mSender;}

	public void setSender(String mSender) {this.mSender = mSender;}

    public UUID getUuid() {
        return mUuid;
    }

    public String getPath() {return mPath;}


    public void setPath(String path) {mPath = path;}


    public MessageRow(Parcel in) {readFromParcel(in);}
	
	public String toString() {
		return mUuid + mDel + mSender + mDel + mMsg + mDel + mTime + mDel + mTtl + mDel + mPath;
	}
	
	
	public static JSONObject getAsJSONObject(MessageRow msgrow) {
		JSONObject jsonobj = new JSONObject();

        try{
            jsonobj.put(MSG_UUID, msgrow.mUuid);
            jsonobj.put(MSG_SENDER, msgrow.mSender);
			jsonobj.put(MSG_TIME, msgrow.mTime);
			jsonobj.put(MSG_MSG, msgrow.mMsg);
            jsonobj.put(MSG_TTL, msgrow.mTtl);
            jsonobj.put(MSG_PATH, msgrow.mPath);



        }catch(JSONException e){
			PTPLog.e(TAG, "getAsJSONObject : " + e.toString());
		}
		return jsonobj;
	}
	
	/**
	 * convert json object to message row.
	 */
	public static MessageRow parseMesssageRow(JSONObject jsonobj) {
		MessageRow row = null;
		if( jsonobj != null ){
			try{
				row = new MessageRow(UUID.fromString(jsonobj.getString(MSG_UUID)),jsonobj.getString(MSG_SENDER), jsonobj.getString(MSG_MSG), jsonobj.getString(MSG_TIME),jsonobj.getString(MSG_TTL), jsonobj.getString(MSG_PATH));
			}catch(JSONException e){
				PTPLog.e(TAG, "parseMessageRow: " + e.toString());
			}
		}
		return row;
	}
	
	/**
	 * convert a json string representation of messagerow into messageRow object.
	 */
	public static MessageRow parseMessageRow(String jsonMsg){
		JSONObject jsonobj = JSONUtils.getJsonObject(jsonMsg);
		//PTPLog.d(TAG, "parseMessageRow : " + jsonobj.toString());
		return parseMesssageRow(jsonobj);
	}

	public static final Creator<MessageRow> CREATOR = new Creator<MessageRow>() {
        public MessageRow createFromParcel(Parcel in) {
            return new MessageRow(in);
        }
 
        public MessageRow[] newArray(int size) {
            return new MessageRow[size];
        }
    };
    
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUuid.toString());
        dest.writeString(mSender);
		dest.writeString(mMsg);
		dest.writeString(mTime);
        dest.writeString(mTtl);
        dest.writeString(mPath);

    }
	
	public void readFromParcel(Parcel in) {
        mUuid = UUID.fromString(in.readString());
        mSender = in.readString();
		mMsg = in.readString();
		mTime = in.readString();
        mTtl = in.readString();
        mPath = in.readString();


    }

}
