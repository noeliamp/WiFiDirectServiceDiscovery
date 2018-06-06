
package discovery;

/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import discovery.WiFiServiceDiscoveryActivity.PTPLog;


/**
 * A BroadcastReceiver that notifies of important wifi p2p events.
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {//implements WifiP2pManager.PeerListListener {

    private WifiP2pManager manager;
    private Channel channel;
    private WiFiServiceDiscoveryActivity activity;
    private final static String TAG = "PTP_BrodRec";
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * @param manager WifiP2pManager system service
     * @param channel Wifi p2p channel
     * @param activity activity associated with the receiver
     */
    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
                                       WiFiServiceDiscoveryActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }


    /*
         * (non-Javadoc)
         * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
         * android.content.Intent)
         */
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d("ACTION", action);

        if(WiFiServiceDiscoveryActivity.getMyDevice() != null)
            Log.d("ACTION", "My status : "+ WiFiServiceDiscoveryActivity.getMyDevice().status);

        if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action) && !activity.isIdle() ){

            if (manager == null) {
                return;
            }
            NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
//            //WifiP2pGroup wifiP2pGroup1 = (WifiP2pGroup) intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            //NetworkInfo networkInfo2 = (NetworkInfo) intent.getParcelableExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST);
//            WifiP2pGroup wifiP2pGroup3 = (WifiP2pGroup) intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_GROUP);


            if (networkInfo.isConnected()) {

                Log.d(WiFiServiceDiscoveryActivity.TAG, "GROUP INFO " +  "-" + networkInfo);


                // we are connected with the other device, request connection
                // info to find group owner IP
                Log.d(WiFiServiceDiscoveryActivity.TAG, "Connected to p2p network. Requesting network details");
                manager.requestConnectionInfo(channel, (ConnectionInfoListener) activity); // the callback of this function will tell us information about the group

                ////////////////////// when p2p connected and this device didn't start the connection,
                /// create a connection to be added to the list //////

                if(activity.getCurrentCon() != null) {
                    PTPLog.d(TAG, "This device initiated the connection");
                    activity.getCurrentCon().setConIni(System.currentTimeMillis());
                    PTPLog.d(TAG, "CON INI:  " + sdf.format(activity.getCurrentCon().getConIni()));
                }

                else if(activity.getCurrentCon() == null) {
                    PTPLog.d(TAG, "This device did not initiate the connection");

                    activity.getScan().setScanEnd(System.currentTimeMillis());

                    ArrayList<String> peersList = new ArrayList();
                    for(WiFiP2pService peer : activity.getServicesList().listAdapter.getItems()){
                        String sub = peer.device.deviceName.substring(peer.device.deviceName.lastIndexOf('_') + 1);
                        peersList.add(sub);
                    }

                    activity.getScan().setPeers(peersList);
                    PTPLog.d(TAG, "PEERS:  " + activity.getScan().getPeers());

                    activity.getScan().setScanDur();
                    PTPLog.d(TAG, "STORING:  " + "ID- " + activity.getScan().getId());

                    activity.getScans().add(activity.getScan());

                    activity.addScan(activity.getScan()); // DATABASE

                    PTPLog.d(TAG, "SCAN INI: "+ "ID- " + activity.getScan().getId() +" - "  + sdf.format(activity.getScan().getScanIni()) + "\n" +
                            "SCAN END: "+ "ID- " + activity.getScan().getId() +" - "  + sdf.format(activity.getScan().getScanEnd()) + "\n" +
                            "SCAN DUR: "+ "ID- " + activity.getScan().getId() +" - "  + sdf.format(activity.getScan().getScanDur()));

                    activity.setScan(null);
                    activity.setCurrentCon(new Connection(false));
                    activity.getCurrentCon().setConIni(System.currentTimeMillis());
                    //activity.getConnections().add(activity.getCurrentCon());
                    PTPLog.d(TAG, "CON INI:  " + sdf.format(activity.getCurrentCon().getConIni()));
                }



            } else {
                // It's a disconnect
                PTPLog.d(TAG, "IM RECEIVING A DISCONNECT");

                PTPLog.d(TAG, "HAVING SENT/RECEIVED - : " + activity.isSendDB() + " - " + activity.isReceived());

                if(activity.getCurrentCon()!= null) {
                    PTPLog.d(TAG, "connection not null");

                    if(activity.isReceived() && activity.isSendDB()){
                        PTPLog.d(TAG, "RECEIVING A NOTIFICATION OF DISCONNECTION AFTER A SUCCEEDED CONNECTION");
                        activity.getCurrentCon().setConEnd(System.currentTimeMillis());
                        PTPLog.d(TAG, "CON END: " + sdf.format(new Date(activity.getCurrentCon().getConEnd())));

                        activity.getConnections().add(activity.getCurrentCon());
                        activity.addConnection(activity.getCurrentCon()); //DATABASE
                        activity.setSendDB(false);
                        activity.setReceived(false);
                        activity.setCurrentCon(null);
                        activity.getFragmentManager().beginTransaction().replace(R.id.container_root, activity.getServicesList(), "services").commit();
                        activity.getStatusTxtView().setText("");
                        activity.getStatusTxtView().setVisibility(View.VISIBLE);
                        activity.discoverService();
                        return;
                    }
                    else if(!(activity.isReceived() && activity.isSendDB())){
                        PTPLog.d(TAG, "current connection not null and true/false");
                        // We don't know if the disconnect is a broadcast!!
                        return;
                    }
                    return;

                } else {
                    PTPLog.d(TAG, "connection null");
                    if(activity.getScan() != null){
                        PTPLog.d(TAG, "scan not null");
                        return;
                    }else{
                        PTPLog.d(TAG, "scan null");
                        return;
                    }
                }
//                activity.discoverService();
//                activity.getFragmentManager().beginTransaction().replace(R.id.container_root, activity.getServicesList(), "services").commit();
//                activity.getStatusTxtView().setText("");
//                activity.getStatusTxtView().setVisibility(View.VISIBLE);
            }
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

            WifiP2pDevice device = (WifiP2pDevice) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            activity.updateThisDevice((WifiP2pDevice)intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));


        }

//        else if (WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
//            // a list of peers are available after discovery, use PeerListListener to collect
//            // request available peers from the wifi p2p manager. This is an
//            // asynchronous call and the calling activity is notified with a
//            // callback on PeerListListener.onPeersAvailable()
//            PTPLog.d(TAG, "processIntent: WIFI_P2P_PEERS_CHANGED_ACTION: call requestPeers() to get list of peers");
//            if (activity.getManager() != null) {
//                activity.getManager().requestPeers(activity.getChannel(), (WifiP2pManager.PeerListListener) this);
//            }
//        }
    }



//    @Override
//    public void onPeersAvailable(WifiP2pDeviceList peerList) {
//
//        activity.getmPeers().clear();
//        activity.getmPeers().addAll(peerList.getDeviceList());
//        PTPLog.d(TAG, "onPeersAvailable : update peer list...");
//
//        WifiP2pDevice connectedPeer = activity.getConnectedPeer();
//
//        if( connectedPeer != null ){
//            PTPLog.d(TAG, "onPeersAvailable : exist connected peer : " + connectedPeer.deviceName + " - " + connectedPeer.status);
//            activity.setConnectedPeer(connectedPeer);
//
//        }



//    }

}
