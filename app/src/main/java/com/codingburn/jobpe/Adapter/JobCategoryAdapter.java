package com.codingburn.jobpe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codingburn.jobpe.HomeTabUi.AfterClickJobType;
import com.codingburn.jobpe.Model.categoryModel;
import com.codingburn.jobpe.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class JobCategoryAdapter extends RecyclerView.Adapter<JobCategoryAdapter.ViewHolder>{
    Context context;
    ArrayList<categoryModel> arrayList = new ArrayList<>();

    public JobCategoryAdapter(){
    }
    // constructors of Adapter
    public JobCategoryAdapter(Context context,ArrayList<categoryModel> arrayList ){
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_job_row,parent,false);
        return new JobCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        categoryModel model = arrayList.get(position);
        Picasso.get().load(model.getCategoryImage() ).into(holder.imageView);
        holder.jobTypeName.setText(model.getJobType());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AfterClickJobType.class);
                intent.putExtra("jobType",model.getJobType());
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        if(arrayList != null){
            return arrayList.size();
        }else {
            return 0;
        }
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{
        private  ImageView imageView;
        private  TextView jobTypeName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.jobCategoryImage);
            jobTypeName = itemView.findViewById(R.id.jobCategoryTxt);
        }
    }
}
