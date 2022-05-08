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

public class CareUserDocViewlRecycleAdapter extends FirebaseRecyclerAdapter<CareUserDocViewlRecycleModel,CareUserDocViewlRecycleAdapter.userViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CareUserDocViewlRecycleAdapter(@NonNull FirebaseRecyclerOptions<CareUserDocViewlRecycleModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull userViewHolder holder, int position, @NonNull CareUserDocViewlRecycleModel model) {
        holder.name.setText(model.getName());
        holder.specialization.setText(model.getSpecialization());
        holder.hospital.setText(model.getHospital());
        holder.workplace.setText(model.getWorkplace());
        holder.whours.setText(model.getWhours());
    //   holder.phonenumber.setText(model.getPhonenumber());
//        holder.email.setText(model.getEmail());

        Glide.with(holder.img.getContext())
                .load(model.getProfilepictureurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.careuserdocviewlrecycle_item,parent,false);
        return new userViewHolder(view);
    }

    class userViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView name,specialization,hospital,workplace,whours,phonenumber,email;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.viewrvimg1);
            name =(TextView)itemView.findViewById(R.id.viewrvnametext);
            specialization =(TextView)itemView.findViewById(R.id.viewrvspecializationtext);
            hospital =(TextView)itemView.findViewById(R.id.viewrvshospitaltext);
            workplace =(TextView)itemView.findViewById(R.id.viewrvworkplacetext);
            whours =(TextView)itemView.findViewById(R.id.viewrvwhourstext);
    //        phonenumber =(Button)itemView.findViewById(R.id.userbtncall);
//            email =(TextView)itemView.findViewById(R.id.viewrvemailtext);

        }
    }


}
