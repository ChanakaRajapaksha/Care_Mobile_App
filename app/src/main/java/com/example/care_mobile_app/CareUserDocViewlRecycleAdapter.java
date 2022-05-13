package com.example.care_mobile_app;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.net.MailTo;
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


        //E-mail button show to users
        holder.userbtnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = model.getEmail();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plan/text");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {mail});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                view.getContext().startActivity(Intent.createChooser(intent,""));
            }
        });


        //call button show to users
         holder.userbtncall.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 String mobileNo = model.getPhonenumber();
                 String call = "tel:" +mobileNo.trim();
                 Intent intent = new Intent(Intent.ACTION_DIAL);
                 intent.setData(Uri.parse(call));
                 view.getContext().startActivity(intent);
             }
         });


        //images retrive
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

        private static final int REQUEST_CALL=1;
        CircleImageView img;
        TextView name,specialization,hospital,workplace,whours,phonenumber,email;
        public Button userbtncall,userbtnemail;

        public userViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.viewrvimg1);
            name =(TextView)itemView.findViewById(R.id.viewrvnametext);
            specialization =(TextView)itemView.findViewById(R.id.viewrvspecializationtext);
            hospital =(TextView)itemView.findViewById(R.id.viewrvshospitaltext);
            workplace =(TextView)itemView.findViewById(R.id.viewrvworkplacetext);
            whours =(TextView)itemView.findViewById(R.id.viewrvwhourstext);
            userbtncall = itemView.findViewById(R.id.userbtncall);
            userbtnemail = itemView.findViewById(R.id.userbtnemail);


        }


    }


}
