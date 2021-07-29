package com.example.quiz.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quiz.CategoryModel;
import com.example.quiz.R;
import com.example.quiz.SetsActivity;
import com.example.quiz.StartActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CatagoryAdapter extends RecyclerView.Adapter<CatagoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CatagoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.catagory_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatagoryAdapter.ViewHolder holder, int position) {
        holder.setData(categoryModelList.get(position).getUrl(),
                categoryModelList.get(position).getName(),categoryModelList.get(position).getSets());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final CircleImageView image;
        private final TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.card_image);
            title=itemView.findViewById(R.id.card_text);
        }
        private void setData(String url,final String title,final int sets){
            try {
                Glide.with(itemView.getContext()).load(url).into(image);
            }
            catch (Exception e){
                Toast.makeText(itemView.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            this.title.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(itemView.getContext(), SetsActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("sets",sets);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
