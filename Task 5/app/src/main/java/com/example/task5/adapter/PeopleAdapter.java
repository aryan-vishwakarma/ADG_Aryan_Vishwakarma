package com.example.task5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task5.People;
import com.example.task5.R;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder>{

    Context context;
    List<People> peopleList;

    public PeopleAdapter(Context context, List<People> peopleList) {
        this.context = context;
        this.peopleList = peopleList;
    }

    @NonNull
    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.people_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleAdapter.ViewHolder holder, int position) {
        People people=peopleList.get(position);
        holder.image.setImageResource(people.getImage());
        holder.about.setText(people.getAbout());
        holder.name.setText(people.getName());
        holder.followers.setText(people.getFollowers());
        holder.posts.setText(people.getPosts());
        holder.followings.setText(people.getFollowing());
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView about;
        TextView followers;
        TextView posts;
        TextView followings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.peopleImage);
            name=itemView.findViewById(R.id.peopleName);
            about=itemView.findViewById(R.id.people_about);
            followers=itemView.findViewById(R.id.peopleFollowers);
            posts=itemView.findViewById(R.id.peoplePosts);
            followings=itemView.findViewById(R.id.peoplefollowing);
        }
    }
}
