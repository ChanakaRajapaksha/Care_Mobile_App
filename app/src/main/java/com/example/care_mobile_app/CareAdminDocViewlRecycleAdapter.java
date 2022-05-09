package com.example.care_mobile_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class CareAdminDocViewlRecycleAdapter extends FirebaseRecyclerAdapter<CareAdminDocViewlRecycleModel,CareAdminDocViewlRecycleAdapter.adminViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CareAdminDocViewlRecycleAdapter(@NonNull FirebaseRecyclerOptions<CareAdminDocViewlRecycleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull adminViewHolder holder,final int position, @NonNull CareAdminDocViewlRecycleModel model) {

        holder.name.setText(model.getName());
        holder.specialization.setText(model.getSpecialization());
        holder.hospital.setText(model.getHospital());
        holder.workplace.setText(model.getWorkplace());
        holder.whours.setText(model.getWhours());
        holder.phonenumber.setText(model.getPhonenumber());
        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getProfilepictureurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        holder.adminbtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.careadmin_doc_update))
                        .setExpanded(true,2000)
                        .create();
               // dialogPlus.show();

                View view1 = dialogPlus.getHolderView();
                EditText name = view1.findViewById(R.id.adminupdocRegName);
                EditText specialization = view1.findViewById(R.id.adminupdocRegspecial);
                EditText hospital = view1.findViewById(R.id.adminupdocRegworkhos);
                EditText workplace = view1.findViewById(R.id.adminupdocRegWorkHours);
                EditText phonenumber = view1.findViewById(R.id.adminupPhoneNumregDoc);
                EditText email = view1.findViewById(R.id.adminupemailDocReg);

                Button adminbtnUpdateDocd = view1.findViewById(R.id.adminbtnUpdateDocd);

                name.setText(model.getName());
                specialization.setText(model.getSpecialization());
                hospital.setText(model.getHospital());
                workplace.setText(model.getWorkplace());
                phonenumber.setText(model.getPhonenumber());
                email.setText(model.getEmail());

                dialogPlus.show();

                adminbtnUpdateDocd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("specialization",specialization.getText().toString());
                        map.put("hospital",hospital.getText().toString());
                        map.put("workplace",workplace.getText().toString());
                        map.put("phonenumber",phonenumber.getText().toString());
                        map.put("email",email.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Doctors")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(),"Data Update Successfully.",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.name.getContext(),"Error while Updating",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                       }
                });
            }
        });

        holder.adminbtnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("are you sure ?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Doctors")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public adminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.careadmindocviewlrecycle_item,parent,false);
        return new CareAdminDocViewlRecycleAdapter.adminViewHolder(view);

    }

    class adminViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,specialization,hospital,workplace,whours,phonenumber,email;

        Button adminbtnUpdateDocd,adminbtnEdit,adminbtnDelete;


        public adminViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.adminviewrvimg1);
            name =(TextView)itemView.findViewById(R.id.adminviewrvnametext);
            specialization =(TextView)itemView.findViewById(R.id.adminviewrvspecializationtext);
            hospital =(TextView)itemView.findViewById(R.id.adminviewrvshospitaltext);
            workplace =(TextView)itemView.findViewById(R.id.adminviewrvworkplacetext);
            whours =(TextView)itemView.findViewById(R.id.adminviewrvwhourstext);
            phonenumber =(TextView)itemView.findViewById(R.id.adminviewrvphonenumbertext);
            email =(TextView)itemView.findViewById(R.id.adminviewrvemailtext);

            adminbtnEdit =(Button)itemView.findViewById(R.id.adminbtnEdit);
            adminbtnUpdateDocd =(Button)itemView.findViewById(R.id.adminbtnUpdateDocd);
            adminbtnDelete =(Button)itemView.findViewById(R.id.adminbtnDelete);

        }
    }


}
