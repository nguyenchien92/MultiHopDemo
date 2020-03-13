package com.example.nguyen.multihopdemo.adapteres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nguyen.multihopdemo.R;

import java.util.List;

public class PeersAdapter extends RecyclerView.Adapter<PeersAdapter.PeersHolder> {

    private Context context;
    private List<String> mList;

    public PeersAdapter(Context context, List<String> mList) {
        this.context = context;
        this.mList = mList;
    }

    public PeersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_peer, parent, false);

        return new PeersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeersHolder holder, int position) {
        String nameDevice = mList.get(position);
        holder.tvNameDevice.setText(nameDevice);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class PeersHolder extends RecyclerView.ViewHolder {

        TextView tvNameDevice;

        public PeersHolder(@NonNull View itemView) {
            super(itemView);

            tvNameDevice = itemView.findViewById(R.id.tv_name_device);
        }
    }
}
