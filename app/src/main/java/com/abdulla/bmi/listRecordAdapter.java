package com.example.bmiProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;


public class listRecordAdapter extends RecyclerView.Adapter<listRecordAdapter.ViewHolder> {
    private List<Records> recordsList;
    private Context context;

    public listRecordAdapter(Context context,  List<Records> results) {
        this.context=context;
        this.recordsList = results;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemstatus,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLength.setText(recordsList.get(position).getLengthRecords());
       holder.tvStatus.setText(recordsList.get(position).getStutesRecords());
        holder.tvWeight.setText(recordsList.get(position).getWeightRecords());
        holder.tvDate.setText(recordsList.get(position).getDateRecords());
    }

    @Override
    public int getItemCount() {
        return recordsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvLength, tvStatus, tvWeight, tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLength = itemView.findViewById(R.id.tvLength);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvWeight = itemView.findViewById(R.id.tvWeight);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

}







