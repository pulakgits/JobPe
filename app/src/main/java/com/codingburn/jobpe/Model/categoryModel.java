package com.codingburn.jobpe.Model;

public class categoryModel {
    String categoryImage,jobType;
    public categoryModel(){

    }

    public categoryModel(String categoryImage, String jobType) {
        this.categoryImage = categoryImage;
        this.jobType = jobType;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
