package com.chhating;

import static com.chhating.ChatActivity.receverImgg;
import static com.chhating.ChatActivity.senderImg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<msgModelClass> msgAdapterArrayList;
    int item_SEND=1;
    int item_RECIVE=2;

    public MessageAdapter(Context context, ArrayList<msgModelClass> msgAdapterArrayList) {
        this.context = context;
        this.msgAdapterArrayList = msgAdapterArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==item_SEND){
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view=LayoutInflater.from(context).inflate(R.layout.reciver_layout,parent,false);
            return new ReciverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        msgModelClass messages=msgAdapterArrayList.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();

                return false;
            }
        });

        if (holder.getClass()==SenderViewHolder.class){
            SenderViewHolder viewHolder=(SenderViewHolder) holder;
            viewHolder.msgTxt.setText(messages.getMessage());
            Picasso.get().load(senderImg).into(viewHolder.circleImageView);

        }else {
            ReciverViewHolder viewHolder=(ReciverViewHolder) holder;
            viewHolder.msgTxt.setText(messages.getMessage());
            Picasso.get().load(receverImgg).into(viewHolder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {

        return msgAdapterArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelClass messages=msgAdapterArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderid())){
            return item_SEND;
        }else {
            return item_RECIVE;
        }
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgTxt;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.sender_profile);
            msgTxt=itemView.findViewById(R.id.sender_txtmsg);
        }
    }
    class ReciverViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgTxt;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView=itemView.findViewById(R.id.reciver_profile);
            msgTxt=itemView.findViewById(R.id.reciver_textset);

        }
    }
}
