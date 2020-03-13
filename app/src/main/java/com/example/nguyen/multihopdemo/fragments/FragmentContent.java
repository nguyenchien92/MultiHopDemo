package com.example.nguyen.multihopdemo.fragments;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyen.multihopdemo.R;
import com.example.nguyen.multihopdemo.adapteres.PeersAdapter;
import com.example.nguyen.multihopdemo.models.WifiDirectBroadCastReceiver;

import java.util.ArrayList;
import java.util.List;

public class FragmentContent extends Fragment {

    private View rootView;
    private Button btOnOff, btDiscover, btSend;
    private TextView tvReadMsg, tvConnectionStatus;
    private EditText edSend;

    private WifiManager wifiManager;
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private WifiDirectBroadCastReceiver broadCastReceiver;
    private IntentFilter filter;
    private PeersAdapter adapter;

    private List<WifiP2pDevice> peers = new ArrayList<>();
    private List<String> deviceNames = new ArrayList<>(); // will use  this list to show device name in recyclerview
    private List<WifiP2pDevice> listDevices = new ArrayList<>();// will use  this list  to connect a Device

    private RecyclerView rvPeers;

    private PeerListListener peerListListener = new PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
            List<WifiP2pDevice> refreshedDevice = (List<WifiP2pDevice>) wifiP2pDeviceList.getDeviceList();

            if (!refreshedDevice.equals(peers)) {
                peers.clear();
                peers.addAll(refreshedDevice);

                for (WifiP2pDevice device : refreshedDevice) {
                    deviceNames.add(device.deviceName);
                    listDevices.add(device);
                }

                adapter = new PeersAdapter(getContext(), deviceNames);
//                rvPeers.setAdapter(adapter);
//                rvPeers.setLayoutManager(new LinearLayoutManager(getContext()));
            }
            if (peers.size() == 0) {
                Log.d("WIFI P2P", "No devices found");
            }
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_main_content, container, false);

        initWork();
        exqListener();

        return rootView;
    }

    private void exqListener() {
//        btOnOff.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(wifiManager.isWifiEnabled()){
//                    wifiManager.setWifiEnabled(false);
//                    btOnOff.setText("ON");
//                }else {
//                    wifiManager.setWifiEnabled(true);
//                    btOnOff.setText("OFF");
//                }
//            }
//        });
        btDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPeers();
            }
        });
    }

    private void findPeers() {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //Discovery started successfully
                tvConnectionStatus.setText("Discovery started ");
            }

            @Override
            public void onFailure(int reason) {
                //Discovery not started
                tvConnectionStatus.setText("Discovery starting failed - code:" + reason);
            }
        });
    }

//

    private void initWork() {
//        btOnOff = rootView.findViewById(R.id.bt_wifi_on_off);
        btDiscover = rootView.findViewById(R.id.bt_discover);
        btSend = rootView.findViewById(R.id.bt_send);
        tvReadMsg = rootView.findViewById(R.id.tv_read_msg);
        tvConnectionStatus = rootView.findViewById(R.id.tv_connection_status);
        edSend = rootView.findViewById(R.id.ed_send);
        rvPeers = rootView.findViewById(R.id.rv_list_peers);

        wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        wifiP2pManager = (WifiP2pManager) getContext().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(getContext(), getContext().getMainLooper(), null);

        broadCastReceiver = new WifiDirectBroadCastReceiver(getContext(), wifiP2pManager, channel, peerListListener);
        filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        rvPeers.setAdapter(adapter);
        rvPeers.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(broadCastReceiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadCastReceiver);
    }
}
