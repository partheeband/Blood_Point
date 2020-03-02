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

public class SearchDonorAdapter extends RecyclerView.Adapter<SearchDonorAdapter.SearchDonorViewHolder> {
    private Context mCtx;
    private List<UserInformationHelper> DonorList;
    public SearchDonorAdapter(Context mCtx, List<UserInformationHelper> DonorList) {
        this.mCtx = mCtx;
        this.DonorList = DonorList;
    }

    @NonNull
    @Override
    public SearchDonorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mCtx).inflate(R.layout.list_layout_searchdonor, parent, false);
        return new SearchDonorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDonorViewHolder holder, int position) {
        UserInformationHelper userInformation = DonorList.get(position);
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

    public class SearchDonorViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName,textViewBloodGroup,textViewContactNo,textViewCity,textViewLastDonationDate,textViewTotalDonation;
        ImageView imageViewGender;
        public SearchDonorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewGender=itemView.findViewById(R.id.textViewGender);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewBloodGroup=itemView.findViewById(R.id.textViewBloodGroup);
            textViewContactNo=itemView.findViewById(R.id.textViewContactNo);
            textViewCity=itemView.findViewById(R.id.textViewCity);
            textViewLastDonationDate=itemView.findViewById(R.id.textViewLastDonationDate);
            textViewTotalDonation=itemView.findViewById(R.id.textViewTotalDonation);
        }
    }
}
