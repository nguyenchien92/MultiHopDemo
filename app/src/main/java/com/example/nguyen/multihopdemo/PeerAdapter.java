package com.example.nguyen.multihopdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PeerAdapter extends RecyclerView.Adapter<PeerAdapter.PeerHolder> {

    private Context context;
    private List<String> mList;

    public PeerAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    public PeerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.fragment_peer, parent, false);

        return new PeerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeerHolder holder, int position) {
        String namePeer = mList.get(position);

        holder.textView.setText(namePeer);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PeerHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public PeerHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
        }
    }
}
