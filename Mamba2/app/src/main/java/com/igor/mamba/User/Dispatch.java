package com.igor.mamba.User;

public class Dispatch {
    public String adminId;
    public String adminName;
    public String adminEmail;
    public String adminImage;
    public String adminContact;
    public String adminCountry;
    public String adminJob;
    public String adminAbout;

    public Dispatch(String adminId, String adminName, String adminEmail, String adminImage, String adminContact, String adminCountry, String adminJob, String adminAbout) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminImage = adminImage;
        this.adminContact = adminContact;
        this.adminCountry = adminCountry;
        this.adminJob = adminJob;
        this.adminAbout = adminAbout;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(String adminImage) {
        this.adminImage = adminImage;
    }

    public String getAdminContact() {
        return adminContact;
    }

    public void setAdminContact(String adminContact) {
        this.adminContact = adminContact;
    }

    public String getAdminCountry() {
        return adminCountry;
    }

    public void setAdminCountry(String adminCountry) {
        this.adminCountry = adminCountry;
    }

    public String getAdminJob() {
        return adminJob;
    }

    public void setAdminJob(String adminJob) {
        this.adminJob = adminJob;
    }

    public String getAdminAbout() {
        return adminAbout;
    }

    public void setAdminAbout(String adminAbout) {
        this.adminAbout = adminAbout;
    }
}
