package com.excellenceict.queuematebigscreen.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.excellenceict.queuematebigscreen.R;
import com.excellenceict.queuematebigscreen.service.model.QueueModel;

import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueAdapterViewHolder>{

    private List<QueueModel> queueList;
    private Context context;

    public QueueAdapter(List<QueueModel> queueList) {
        this.queueList = queueList;
    }

    @NonNull
    @Override
    public QueueAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new QueueAdapterViewHolder(LayoutInflater.from(context).inflate(
                R.layout.custome_queue_row,parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull QueueAdapterViewHolder holder, int position) {
        QueueModel queueModel =queueList.get(position);

            holder.jQueueRoomNo.setText(queueModel.getRoomNumber());
            holder.jQueueCurrent.setText(queueModel.getCurrentQueueId()+" - "+queueModel.getCurrentPersionName());
            holder.jQueueNext.setText(queueModel.getNextQueueId()+ " - "+queueModel.getNextPersionName());
            holder.jQueueWaiting.setText(queueModel.getNextQueueId()+" - "+queueModel.getWaitingPersionName());

    }

    @Override
    public int getItemCount() {
        if(this.queueList !=null){
            return queueList.size();
        }else {
            return 0;
        }

    }

    public class QueueAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView jQueueRoomNo,jQueueCurrent,jQueueNext, jQueueWaiting;

        public QueueAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            jQueueRoomNo = itemView.findViewById(R.id.queueRoomNo);
            jQueueCurrent = itemView.findViewById(R.id.queueCurrent);
            jQueueNext = itemView.findViewById(R.id.queueNext);
            jQueueWaiting = itemView.findViewById(R.id.queueWaiting);
        }
    }
}
