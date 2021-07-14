package com.example.task5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task5.Pet;
import com.example.task5.R;

import java.util.List;

public class petsAdapter extends RecyclerView.Adapter<petsAdapter.ViewHolder> {

    private Context context;
    private List<Pet> petList;

    public petsAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public petsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull petsAdapter.ViewHolder holder, int position) {

        Pet pet=petList.get(position);
        holder.name.setText(pet.getPetName());
        holder.image.setImageResource(pet.getImage());
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView image;
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image=itemView.findViewById(R.id.cardImage);
            name=itemView.findViewById(R.id.cardText);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
