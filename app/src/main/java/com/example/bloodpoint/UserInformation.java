package com.example.bloodpoint;

public class UserInformation {
    public String name;
    public String city;
    public String phoneno;
    public String gender;
    public String bloodgroup;
    public Boolean isDonor;
    public int totalDonations;
    public String lastDonationDate;

    public UserInformation(){

    }


    public UserInformation(String name, String city, String phoneno, String gender, String bloodgroup, Boolean isDonor,int totalDonations,String lastDonationDate) {
        this.name = name;
        this.city = city;
        this.phoneno=phoneno;
        this.gender=gender;
        this.bloodgroup=bloodgroup;
        this.isDonor=isDonor;
        this.totalDonations=totalDonations;
        this.lastDonationDate=lastDonationDate;
    }
        public UserInformation(int totalDonations,String lastDonationDate) {
        this.totalDonations=totalDonations;
        this.lastDonationDate=lastDonationDate;
    }

    public UserInformation(String city, String phoneno, boolean isDonor) {
        this.city = city;
        this.phoneno=phoneno;
        this.isDonor=isDonor;
    }
}
