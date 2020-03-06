package com.example.nguyen.multihopdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

public class BroadCastReceiverWifi extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private Context context;
    private String TAG = "##WifiBR";
    PeerListListener myPeerListListener;

    public BroadCastReceiverWifi(WifiP2pManager mManager, Channel mChannel, Context context,PeerListListener PLL) {
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.context = context;
        this.myPeerListListener = PLL;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            //check to see if wifi is enable  and notify appropriate activity

            //check to see if Wifi P2P is on and supported
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                //Wifi P2P is enabled
            } else {
                //Wifi P2P is not enabled
            }
            // Check to see if Wifi is enabled and notify appropriate activity
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            //call wifiP2pManager.requestPeers() to get a list of current peers
            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and calling activity is notified with a
            // callback on PeerListListener.onPeerAvailable()
            if(mManager != null){
                mManager.requestPeers(mChannel,myPeerListListener);
            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            //respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            //respond to this devices wifi state changing
        }
    }
}
