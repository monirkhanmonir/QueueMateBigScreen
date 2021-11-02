package com.excellenceict.queuematebigscreen.view.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

        if(queueModel.getCurrentQueueId()=="" || queueModel.getCurrentQueueId()==null){
            holder.jQueueCurrent.setText(" - ");
        }else {
            holder.jQueueCurrent.setText(queueModel.getCurrentQueueId()+" - "+queueModel.getCurrentPersionName());
        }

        if(queueModel.getNextQueueId()=="" || queueModel.getNextQueueId()==null){
            holder.jQueueNext.setText(" - ");
        }else {
            holder.jQueueNext.setText(queueModel.getNextQueueId()+ " - "+queueModel.getNextPersionName());
            callingAlertEfect(holder.jNextCallingLayout);
        }

        if(queueModel.getWaitingQueueId()=="" || queueModel.getWaitingQueueId()==null){
            holder.jQueueWaiting.setText(" - ");
        }else {
            holder.jQueueWaiting.setText(queueModel.getWaitingQueueId()+" - "+queueModel.getWaitingPersionName());
        }

        if(position%2==1){
            Log.d("TAG","Print adapter position"+position);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.jQueue_row_layout_id.setBackground(context.getDrawable(R.color.appPoorBackground));
            }
        }




    }

    @Override
    public int getItemCount() {
        if(this.queueList !=null){
            return queueList.size();
        }else {
            return 0;
        }

    }

    private void callingAlertEfect(LinearLayout layout){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(layout,"backgroundColor",
                Color.parseColor("#4D0433DC"), Color.parseColor("#110736DF"),Color.parseColor("#4D0433DC"));
        objectAnimator.setDuration(2500);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        //objectAnimator.setRepeatMode(Animation.REVERSE);
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.start();

    }


    public class QueueAdapterViewHolder extends RecyclerView.ViewHolder {
       private TextView jQueueRoomNo,jQueueCurrent,jQueueNext, jQueueWaiting;
       private LinearLayout jQueue_row_layout_id, jNextCallingLayout;


        public QueueAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            jQueueRoomNo = itemView.findViewById(R.id.queueRoomNo);
            jQueueCurrent = itemView.findViewById(R.id.queueCurrent);
            jQueueNext = itemView.findViewById(R.id.queueNext);
            jQueueWaiting = itemView.findViewById(R.id.queueWaiting);
            jQueue_row_layout_id = itemView.findViewById(R.id.queue_row_layout_id);
            jNextCallingLayout = itemView.findViewById(R.id.nextCallingLayout);

        }
    }
}
