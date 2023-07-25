package com.codingburn.jobpe.Model;

public class JobModel {
    private  String companyLogo,jobHeadline,jobRole,jobQualify,lastDateApply;
    private String jobId,link;

    private String aboutCompany, companyName,postName,salary,experience,jobLocation,batch,companyWebsite,lastDate,qualifyRequired,skill,selectionProcess,applyProcess,companyTableHeadline;

    // default constructors
    public JobModel(){
    }

    public JobModel(String companyLogo, String jobHeadline, String jobRole, String jobQualify, String lastDateApply, String jobId, String link, String aboutCompany, String companyName, String postName, String salary, String experience, String jobLocation, String batch, String companyWebsite, String lastDate, String qualifyRequired, String skill, String selectionProcess, String applyProcess, String companyTableHeadline) {
        this.companyLogo = companyLogo;
        this.jobHeadline = jobHeadline;
        this.jobRole = jobRole;
        this.jobQualify = jobQualify;
        this.lastDateApply = lastDateApply;
        this.jobId = jobId;
        this.link = link;
        this.aboutCompany = aboutCompany;
        this.companyName = companyName;
        this.postName = postName;
        this.salary = salary;
        this.experience = experience;
        this.jobLocation = jobLocation;
        this.batch = batch;
        this.companyWebsite = companyWebsite;
        this.lastDate = lastDate;
        this.qualifyRequired = qualifyRequired;
        this.skill = skill;
        this.selectionProcess = selectionProcess;
        this.applyProcess = applyProcess;
        this.companyTableHeadline = companyTableHeadline;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getJobHeadline() {
        return jobHeadline;
    }

    public void setJobHeadline(String jobHeadline) {
        this.jobHeadline = jobHeadline;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobQualify() {
        return jobQualify;
    }

    public void setJobQualify(String jobQualify) {
        this.jobQualify = jobQualify;
    }

    public String getLastDateApply() {
        return lastDateApply;
    }

    public void setLastDateApply(String lastDateApply) {
        this.lastDateApply = lastDateApply;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getQualifyRequired() {
        return qualifyRequired;
    }

    public void setQualifyRequired(String qualifyRequired) {
        this.qualifyRequired = qualifyRequired;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSelectionProcess() {
        return selectionProcess;
    }

    public void setSelectionProcess(String selectionProcess) {
        this.selectionProcess = selectionProcess;
    }

    public String getApplyProcess() {
        return applyProcess;
    }

    public void setApplyProcess(String applyProcess) {
        this.applyProcess = applyProcess;
    }

    public String getCompanyTableHeadline() {
        return companyTableHeadline;
    }

    public void setCompanyTableHeadline(String companyTableHeadline) {
        this.companyTableHeadline = companyTableHeadline;
    }
}
