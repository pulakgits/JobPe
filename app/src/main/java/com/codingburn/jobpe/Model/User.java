package com.codingburn.jobpe.Model;

public class User {
private String profileImg, profileName, email,password, aboutMe, skill, institute,passoutYear,degree;

// default constructors
    public User(){
    }
    // parameterized constructors


    public User(String profileImg, String profileName, String email, String password, String aboutMe, String skill,String institute,String passoutYear, String degree) {
        this.profileImg = profileImg;
        this.profileName = profileName;
        this.email = email;
        this.password = password;
        this.aboutMe = aboutMe;
        this.skill = skill;
        this.institute  = institute;
        this.passoutYear = passoutYear;
        this.degree = degree;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getPassoutYear() {
        return passoutYear;
    }

    public void setPassoutYear(String passoutYear) {
        this.passoutYear = passoutYear;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
