package com.example.care_mobile_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemAdapter extends FirebaseRecyclerAdapter<ItemModel,ItemAdapter.myViewHolder>
{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull ItemModel model) {
        holder.title.setText(model.getTitle());
        holder.category.setText(model.getCategory());
        holder.con.setText(model.getCon());

        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.firebase.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnItemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.item_update_popup))
                        .setExpanded(true,1800)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.editName);
                EditText title = view.findViewById(R.id.editTitle);
                EditText district = view.findViewById(R.id.editDistrict);
                EditText number = view.findViewById(R.id.editNumber);
                EditText category = view.findViewById(R.id.editCategory);
                EditText price= view.findViewById(R.id.editPrice);
                EditText con = view.findViewById(R.id.editCondition);
                EditText des = view.findViewById(R.id.editDescription);

                Button btnItemUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                title.setText(model.getTitle());
                district.setText(model.getDistrict());
                number.setText(model.getNumber());
                category.setText(model.getCategory());
                price.setText(model.getPrice());
                con.setText(model.getCon());
                des.setText(model.getDes());

                dialogPlus.show();

                btnItemUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("title",title.getText().toString());
                        map.put("district",district.getText().toString());
                        map.put("number",number.getText().toString());
                        map.put("category",category.getText().toString());
                        map.put("price",price.getText().toString());
                        map.put("condition",con.getText().toString());
                        map.put("description",des.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("item")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.title.getContext(), "Error while Updating", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView title,category,con;

        Button btnItemEdit, btnItmDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.item1);
            title = (TextView)itemView.findViewById(R.id.item_text);
            category = (TextView)itemView.findViewById(R.id.cat_text);
            con = (TextView)itemView.findViewById(R.id.con_text);

            btnItemEdit = (Button)itemView.findViewById(R.id.btnItemEdit);
            btnItmDelete = (Button)itemView.findViewById(R.id.btnItemDelete);
        }
    }
}
