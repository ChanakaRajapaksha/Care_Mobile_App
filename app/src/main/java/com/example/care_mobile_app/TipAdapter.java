package com.example.care_mobile_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class TipAdapter extends FirebaseRecyclerAdapter<com.example.care_mobile_app.TipsModel,TipAdapter.tipViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TipAdapter(@NonNull FirebaseRecyclerOptions<com.example.care_mobile_app.TipsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull tipViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull com.example.care_mobile_app.TipsModel model) {
        holder.tipTitle.setText(model.getTitle());
        holder.tipDescription.setText(model.getDescription());

        Glide.with(holder.tipimg.getContext())
                .load(model.getTurl())
                .into(holder.tipimg);

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.tipimg.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_tips_popup))
                        .setExpanded(true, 1700)
                        .create();
                //dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText tipTitle = view.findViewById(R.id.update_tip_title);
                EditText tipDescription = view.findViewById(R.id.update_tip_description);
                EditText turl = view.findViewById(R.id.update_img_url);

                Button btnupdate = view.findViewById(R.id.btnUpdateTip);

                tipTitle.setText(model.getTitle());
                tipDescription.setText(model.getDescription());
                turl.setText(model.getTurl());

                dialogPlus.show();

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", tipTitle.getText().toString());
                        map.put("description", tipDescription.getText().toString());
                        map.put("turl", turl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Tips")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.tipTitle.getContext(), "Data Updated Successfully.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.tipTitle.getContext(), "Error While Updating.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.tipTitle.getContext());
                builder.setTitle("Are You Sure, You Want to Delete?");
                builder.setMessage("Deleted data can't be Undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Tips")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.tipTitle.getContext(), "Cancelled.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public tipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_item, parent, false);
        return new tipViewHolder(view);
    }

    class tipViewHolder extends RecyclerView.ViewHolder{

        ImageView tipimg;
        TextView tipTitle, tipDescription;

        Button btnUpdate, btnDelete;

        @SuppressLint("ClickableViewAccessibility")
        public tipViewHolder(@NonNull View itemView) {
            super(itemView);

            tipimg = (ImageView) itemView.findViewById(R.id.tip_img1);
            tipTitle = (TextView) itemView.findViewById(R.id.tip_title);
            tipDescription = (TextView) itemView.findViewById(R.id.tip_description);

            btnUpdate = (Button) itemView.findViewById(R.id.btnUpdate);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

        }
    }
}
