package com.chhating;

import android.content.Intent;
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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.SubViewHolder> {

    MainActivity mainActivity;
    ArrayList<Users> usersArrayList;
    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);
        return new SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.SubViewHolder holder, int position) {

        Users user=usersArrayList.get(position);

        //to remove the profile of loginID
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(user.getUserId())){
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

        holder.userName.setText(user.userName);
        holder.userStatus.setText(user.status);
        Picasso.get().load(user.profilepic).into(holder.userImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mainActivity, ChatActivity.class);
                intent.putExtra("nameeee",user.getUserName());
                intent.putExtra("rcvImg",user.getProfilepic());
                intent.putExtra("uIddd",user.getUserId());
                mainActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userImg;
        TextView userName,userStatus;

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg=itemView.findViewById(R.id.user_img);
            userName=itemView.findViewById(R.id.user_name);
            userStatus=itemView.findViewById(R.id.user_status);
        }
    }
}
