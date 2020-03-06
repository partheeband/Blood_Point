package com.example.bloodpoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewBloodRequestAdapter extends RecyclerView.Adapter<ViewBloodRequestAdapter.ViewBloodRequestViewHolder>{
    private Context mCtx;
    private List<BloodRequestsHelper> BloodRequestList;
    private SearchDonorAdapter.onNoteListener mOnNoteListener;
    public ViewBloodRequestAdapter(Context mCtx, List<BloodRequestsHelper> BloodRequestList, SearchDonorAdapter.onNoteListener onNoteListener) {
        this.mCtx = mCtx;
        this.BloodRequestList = BloodRequestList;
        this.mOnNoteListener=onNoteListener;
    }


    @NonNull
    @Override
    public ViewBloodRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.list_layout_viewbloodrequest, parent, false);
        return new ViewBloodRequestViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBloodRequestViewHolder holder, int position) {
        BloodRequestsHelper bloodRequest = BloodRequestList.get(position);
        if (bloodRequest.gender.equals("FEMALE"))
        {
            holder.imageViewGender.setImageResource(R.drawable.icon_female_red);
        }
        holder.textViewPatientName.setText(bloodRequest.patientName);
        holder.textViewBloodGroup.setText(bloodRequest.bloodgroup);
        holder.textViewReasonForRequest.setText(bloodRequest.reasonForRequest);
        holder.textViewHospitalName.setText(bloodRequest.hospitalName);
        holder.textViewHospitalLocation.setText(bloodRequest.hospitalLocation);
        holder.textViewRequestDate.setText(String.valueOf(bloodRequest.timeStamp));
        holder.textViewContactNo.setText(bloodRequest.contactNo);

    }

    @Override
    public int getItemCount() {
        return BloodRequestList.size();
    }

    public class ViewBloodRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewPatientName,textViewBloodGroup,textViewReasonForRequest,textViewHospitalLocation,textViewHospitalName,textViewRequestDate,textViewContactNo;
        ImageView imageViewGender,imageViewCall;

        SearchDonorAdapter.onNoteListener onNoteListener;
        public ViewBloodRequestViewHolder(@NonNull View itemView, SearchDonorAdapter.onNoteListener onNoteListener) {
            super(itemView);
            imageViewGender=itemView.findViewById(R.id.imageViewGender);
            textViewPatientName=itemView.findViewById(R.id.textViewPatientName);
            textViewBloodGroup=itemView.findViewById(R.id.textViewBloodGroup);
            textViewReasonForRequest=itemView.findViewById(R.id.textViewCase);
            textViewHospitalName=itemView.findViewById(R.id.textViewHospitalName);
            textViewHospitalLocation=itemView.findViewById(R.id.textViewHospitalLocation);
            textViewRequestDate=itemView.findViewById(R.id.textViewRequestDate);
            textViewContactNo=itemView.findViewById(R.id.textViewContactNo);
            imageViewCall=itemView.findViewById(R.id.imageViewCall);

            this.onNoteListener=onNoteListener;
            imageViewCall.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
}
