package com.example.nguyen.multihopdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class BroadCastFragment extends Fragment {

    private View rootView;
    private TextView tvLoggedIn;
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mBroadCastReceiver;
    private IntentFilter intentFilter;
    private PeerAdapter adapter;
    private RecyclerView rvPeers;
    private List<String> mNamePeers = new ArrayList<>();
    private Button btRefresh;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.broadcast_fragment, container, false);

        tvLoggedIn = rootView.findViewById(R.id.loggedIn);
        rvPeers = rootView.findViewById(R.id.rv_peers);
        btRefresh = rootView.findViewById(R.id.button_refresh);

        createP2p();
        configUI();


        return rootView;
    }

    private void configUI() {
        //current haven't data...
        adapter = new PeerAdapter(getContext(),mNamePeers);
        rvPeers.setAdapter(adapter);
        rvPeers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void createP2p() {
        Bundle bundle = this.getArguments();
        if (bundle != null)
            tvLoggedIn.setText(bundle.getString("name"));


        //obtain instance of WifiP2pManager;

        mManager = (WifiP2pManager) getContext().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(getContext(), getContext().getMainLooper(), null);
        mBroadCastReceiver = new BroadCastReceiverWifi(mManager, mChannel, getContext());

        //Create an intent filter and add the same intents that your broadcast receiver checks for:

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


        //Discover peers
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //If the discovery process succeeds and detects peers,
                // the system broadcasts the WIFI_P2P_PEERS_CHANGED_ACTION intent,
                // which you can listen for in a broadcast receiver to obtain a list of peers.
                // When your application receives the WIFI_P2P_PEERS_CHANGED_ACTION intent,
                // you can request a list of the discovered peers with requestPeers()
            }

            @Override
            public void onFailure(int reason) {

            }
        });

    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(mBroadCastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(mBroadCastReceiver);
    }
    //When you have obtained a WifiP2pManager.Channel and set up a broadcast receiver,
    // your application can make Wi-Fi P2P method calls and receive Wi-Fi P2P intents.
    //
    //You can now implement your application and use the Wi-Fi P2P features by calling the methods in WifiP2pManager.
    // The next sections describe how to do common actions such as discovering and connecting to peers.

}
