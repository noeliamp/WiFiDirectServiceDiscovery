
package discovery;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Handles reading and writing of messages with socket buffers. Uses a Handler
 * to post messages to UI thread for UI updates.
 */
public class ChatManager implements Runnable {

    private Socket socket = null;
    private Handler handler;
    private int counter;
    private int sum;
    private String superString = "";
    private boolean firstStep = false;

    public ChatManager(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    private InputStream iStream;
    private OutputStream oStream;
    private static final String TAG = "ChatHandler";

    @Override
    public void run() {

        try {

            iStream = socket.getInputStream();
            oStream = socket.getOutputStream();

            handler.obtainMessage(WiFiServiceDiscoveryActivity.MY_HANDLE, this).sendToTarget();

            while (true) {
                try {
                    // Read from the InputStream
                    byte[] buffer = new byte[1024];
                    int bytes = iStream.read(buffer);
                    sum = sum + bytes;
                    Log.d(TAG, "recogiendoo " + bytes);
                    if (bytes == -1) {
                        break;
                    }
                    // Send the obtained bytes to the UI Activity
                    Message mesage = handler.obtainMessage(WiFiServiceDiscoveryActivity.MESSAGE_READ, bytes, -1, buffer);


                    byte[] readBuf = (byte[]) mesage.obj;
                    String readMessage = new String(readBuf, 0 , mesage.arg1);
                    Log.d(TAG, "recogiendoo 2 " + readMessage);

                    superString = superString + readMessage;


                    if(firstStep==false) {
                        if(readMessage.startsWith("uids list")){
                            counter = Integer.parseInt(readMessage.substring(readMessage.indexOf('t') + 1, readMessage.indexOf('[')));
                            firstStep = true;
                        }
                        else{
                            if(!readMessage.equals("1") && !readMessage.equals("EMPTY")){
                                counter = Integer.parseInt(readMessage.substring(0, readMessage.indexOf('{')));
                            }
                            if(readMessage.equals("EMPTY")){
                                counter = 5;
                            }
                            if(readMessage.equals("1")){
                                counter = Integer.parseInt(readMessage.substring(0));
                            }
                            firstStep = true;
                        }

                    }

                    if(sum == counter){
                        byte[] buffer2 = superString.getBytes();
                        handler.obtainMessage(WiFiServiceDiscoveryActivity.MESSAGE_READ, sum, -1, buffer2).sendToTarget();
                        sum = 0;
                        counter = 0;
                        firstStep = false;
                        superString = "";
                    }

                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    public void write(byte[] buffer) {
        try {
            Log.d(TAG, "ESCRIBIENDOOOO ");
            if (!socket.isClosed()) {
                oStream = socket.getOutputStream();
                oStream.write(buffer);
                oStream.flush();
            }
            else{
                Log.d(TAG, "SOCKET IS CLOSEEEED ");
            }
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }

}
