package com.codingburn.jobpe.Adapter;
import com.codingburn.jobpe.HomeTabUi.JobDetailsActivity;
import com.codingburn.jobpe.Model.*;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburn.jobpe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JobRecyclerAdapter extends  RecyclerView.Adapter<JobRecyclerAdapter.ViewHolder>{

    Context context;
    ArrayList<JobModel> jobModelArrayList = new ArrayList<>();

    public JobRecyclerAdapter(Context context,  ArrayList<JobModel> jobModelArrayList){
        this.context = context;
        this.jobModelArrayList = jobModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobModel model = jobModelArrayList.get(position);
        Picasso.get().load(model.getCompanyLogo()).into(holder.companyLogo);
        holder.jobHeadline.setText(model.getJobHeadline());
        holder.jobRole.setText(model.getJobRole());
        holder.jobQualify.setText(model.getJobQualify());
        holder.lastDateApply.setText(model.getLastDateApply());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailsActivity.class);
                intent.putExtra("link",model.getLink());
                intent.putExtra("jobId", model.getJobId());
                intent.putExtra("jobHeadline",model.getJobHeadline());
                intent.putExtra("companyLogo",model.getCompanyLogo());
                intent.putExtra("aboutCompany",model.getAboutCompany());
                intent.putExtra("companyTableHeadline", model.getCompanyTableHeadline());
                intent.putExtra("companyName", model.getCompanyName());
                intent.putExtra("postName", model.getPostName());
                intent.putExtra("salary", model.getSalary());
                intent.putExtra("experience", model.getExperience());
                intent.putExtra("jobLocation", model.getJobLocation());
                intent.putExtra("batch", model.getBatch());
                intent.putExtra("companyWebsite", model.getCompanyWebsite());
                intent.putExtra("lastDate", model.getLastDate());
                intent.putExtra("qualifyRequired", model.getQualifyRequired());
                intent.putExtra("skill", model.getSkill());
                intent.putExtra("selectionProcess", model.getSelectionProcess());
                intent.putExtra("applyProcess", model.getApplyProcess());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(jobModelArrayList != null){
            return  jobModelArrayList.size();
        }else{
            return  0;
        }
    }

    public void setFilter(ArrayList<JobModel> filteredList) {
        this.jobModelArrayList = filteredList;
        notifyDataSetChanged();
    }

    // viewHolder class
    public  static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView companyLogo;
        TextView jobHeadline,jobRole,jobQualify,lastDateApply;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyLogo = itemView.findViewById(R.id.companyLogo);
            jobHeadline = itemView.findViewById(R.id.jobHeadline);
            jobRole = itemView.findViewById(R.id.jobRole);
            jobQualify = itemView.findViewById(R.id.jobQualify);
            lastDateApply = itemView.findViewById(R.id.lastDateApply);
        }
    }
}
