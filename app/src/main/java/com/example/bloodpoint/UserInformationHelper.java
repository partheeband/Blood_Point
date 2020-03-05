package com.example.bloodpoint;

public class UserInformationHelper {
    public String name;
    public String city;
    public String phoneno;
    public String gender;
    public String bloodgroup;
    public Boolean isDonor;
    public int totalDonations;
    public String lastDonationDate;
    public String email;

    public UserInformationHelper(){

    }


    public UserInformationHelper(String name, String city, String phoneno, String gender, String bloodgroup, Boolean isDonor, int totalDonations, String lastDonationDate,String email) {
        this.name = name;
        this.city = city;
        this.phoneno=phoneno;
        this.gender=gender;
        this.bloodgroup=bloodgroup;
        this.isDonor=isDonor;
        this.totalDonations=totalDonations;
        this.lastDonationDate=lastDonationDate;
        this.email=email;
    }
    public UserInformationHelper(int totalDonations, String lastDonationDate) {
        this.totalDonations=totalDonations;
        this.lastDonationDate=lastDonationDate;
    }

    public UserInformationHelper(String city, String phoneno, boolean isDonor) {
        this.city = city;
        this.phoneno=phoneno;
        this.isDonor=isDonor;
    }
}
