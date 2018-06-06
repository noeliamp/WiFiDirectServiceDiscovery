
package discovery;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import discovery.WiFiServiceDiscoveryActivity.PTPLog;

/**
 * This fragment handles chat related UI which includes a list view for messages
 * and a message entry field with send button.
 */
public class WiFiChatFragment extends Fragment {
    private final static String TAG = "PTP_ChatFrag";

    private View view;
    private ChatManager chatManager;
    private TextView chatLine;
    private ListView listView;
    ChatMessageAdapter adapter = null;
    private List<String> items = new ArrayList<String>();
    private WiFiServiceDiscoveryActivity mApp;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mApp = (WiFiServiceDiscoveryActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatLine = (TextView) view.findViewById(R.id.txtChatLine);
        listView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ChatMessageAdapter(getActivity(), android.R.id.text1,
                items);
        listView.setAdapter(adapter);
        view.findViewById(R.id.button1).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (chatManager != null) {
                            Date now = new Date();
                            String time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(now);
                            String myName =  mApp.getMyDevice().deviceName;
                            String sub = myName.substring(myName.lastIndexOf('_') + 1);
                            MessageRow messageRow = new MessageRow(UUID.randomUUID(), mApp.getMyDevice().deviceName , chatLine.getText().toString(), null, null,sub +"-at-"+ time);

                            String jsonMsg = mApp.shiftInsertMessage(messageRow);
                            PTPLog.d(TAG, "Json: " + jsonMsg);
                            chatManager.write(jsonMsg.getBytes());

                            pushMessage("Me: " + jsonMsg);

                            mApp.addMessage(messageRow); // for db

                            chatLine.setText("");
                            chatLine.clearFocus();
                        }
                    }
                });

        view.findViewById(R.id.button2).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                            mApp.removeAllMessages();

                    }
                });

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        for (MessageRow msg : mApp.getMessagesDB()){
            String jsonMsg = mApp.shiftInsertMessage(msg);
            pushMessage(jsonMsg);
        }

    }

    public interface MessageTarget {
        public Handler getHandler();
    }

    public void setChatManager(ChatManager obj) {
        chatManager = obj;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public void pushMessage(String readMessage) {
        adapter.add(readMessage);
        PTPLog.e(TAG, "Show message on my device: " + readMessage);
        adapter.notifyDataSetChanged();

    }

    /**
     * ArrayAdapter to manage chat messages.
     */
    public class ChatMessageAdapter extends ArrayAdapter<String> {

        List<String> messages = null;

        public ChatMessageAdapter(Context context, int textViewResourceId,
                List<String> items) {
            super(context, textViewResourceId, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_1, null);
            }
            String message = items.get(position);
            if (message != null && !message.isEmpty()) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);

                if (nameText != null) {
                    nameText.setText(message);
                    if (message.startsWith("Me: ")) {
                        nameText.setTextAppearance(getActivity(),
                                R.style.normalText);
                    } else {
                        nameText.setTextAppearance(getActivity(),
                                R.style.boldText);
                    }
                }
            }
            return v;
        }
    }
}
