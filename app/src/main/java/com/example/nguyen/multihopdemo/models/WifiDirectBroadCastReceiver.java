package com.example.nguyen.multihopdemo.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.Toast;


public class WifiDirectBroadCastReceiver extends BroadcastReceiver {

    private Context context;
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private PeerListListener mListener;


    public WifiDirectBroadCastReceiver(Context context, WifiP2pManager wifiP2pManager
            , WifiP2pManager.Channel channel,PeerListListener mListener) {
        this.context = context;
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.mListener = mListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            //do something...
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(this.context, "Wifi is ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.context, "Wifi is OFF", Toast.LENGTH_SHORT).show();
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            if (wifiP2pManager != null){
                wifiP2pManager.requestPeers(channel,mListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
