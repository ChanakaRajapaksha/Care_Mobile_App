package com.example.care_mobile_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAdapter extends FirebaseRecyclerAdapter<ItemModel,ViewAdapter.myViewHolder>
{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ItemModel model) {

        holder.title.setText(model.getTitle());
        holder.category.setText(model.getCategory());
        holder.price.setText(model.getPrice());
        holder.name.setText(model.getName());
        holder.number.setText(model.getNumber());
        holder.district.setText(model.getDistrict());

        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item,parent,false);
        return new myViewHolder(view);
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView title,category,price;
        TextView name;
        TextView number,district;


        public myViewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.item1);
            title = (TextView)itemView.findViewById(R.id.item_text);
            category = (TextView)itemView.findViewById(R.id.cat_text);
            price = (TextView)itemView.findViewById(R.id.price_text);
            name = (TextView)itemView.findViewById(R.id.name_text);
            number = (TextView)itemView.findViewById(R.id.number_text);
            district = (TextView)itemView.findViewById(R.id.dis_text);

        }
    }
}


