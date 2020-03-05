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

public class SearchDonorAdapter extends RecyclerView.Adapter<SearchDonorAdapter.SearchDonorViewHolder>{
    private Context mCtx;
    private List<UserInformationHelper> DonorList;
    private onNoteListener mOnNoteListener;

    public SearchDonorAdapter(Context mCtx, List<UserInformationHelper> DonorList,onNoteListener onNoteListener) {
        this.mCtx=mCtx;
        this.DonorList = DonorList;
        this.mOnNoteListener=onNoteListener;
    }

    @NonNull
    @Override
    public SearchDonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.list_layout_searchdonor, parent, false);
        return new SearchDonorViewHolder(view,mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDonorViewHolder holder, int position) {
        UserInformationHelper userInformation = DonorList.get(position);
        if (userInformation.gender.equals("FEMALE"))
        {
            holder.imageViewGender.setImageResource(R.drawable.icon_female_red);
        }
        if (userInformation.totalDonations<=1)
        {
            holder.imageViewStar.setVisibility(View.GONE);
        }
        holder.textViewName.setText(userInformation.name);
        holder.textViewBloodGroup.setText(userInformation.bloodgroup);
        holder.textViewContactNo.setText(userInformation.phoneno);
        holder.textViewCity.setText(userInformation.city);
        holder.textViewLastDonationDate.setText(userInformation.lastDonationDate);
        holder.textViewTotalDonation.setText(String.valueOf(userInformation.totalDonations));
    }

    @Override
    public int getItemCount() {
        return DonorList.size();
    }

    public class SearchDonorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewName,textViewBloodGroup,textViewContactNo,textViewCity,textViewLastDonationDate,textViewTotalDonation;
        ImageView imageViewGender,imageViewStar,imageViewCall;

        onNoteListener onNoteListener;
        public SearchDonorViewHolder(@NonNull View itemView, onNoteListener onNoteListener) {
            super(itemView);
            imageViewGender=itemView.findViewById(R.id.imageViewGender);
            imageViewStar=itemView.findViewById(R.id.imageViewStar);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewBloodGroup=itemView.findViewById(R.id.textViewBloodGroup);
            textViewContactNo=itemView.findViewById(R.id.textViewContactNo);
            textViewCity=itemView.findViewById(R.id.textViewCity);
            textViewLastDonationDate=itemView.findViewById(R.id.textViewLastDonationDate);
            textViewTotalDonation=itemView.findViewById(R.id.textViewTotalDonation);
            imageViewCall=itemView.findViewById(R.id.imageViewCall);

            this.onNoteListener=onNoteListener;
            imageViewCall.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface onNoteListener{
        void onNoteClick(int position);
    }
}
