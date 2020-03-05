package com.example.bloodpoint;

public class BloodRequestsHelper {
    public String patientName;
    public String gender;
    public String bloodgroup;
    public String hospitalName;
    public String hospitalLocation;
    public String contactNo;
    public String reasonForRequest;
    public String timeStamp;
    public String userid;

    public BloodRequestsHelper() {
    }
    public BloodRequestsHelper(String patientName, String hospitalName, String hospitalLocation, String gender, String bloodgroup,String contactNo, String reasonForRequest,String timeStamp,String userid) {
        this.patientName = patientName;
        this.hospitalName = hospitalName;
        this.hospitalLocation=hospitalLocation;
        this.gender=gender;
        this.bloodgroup=bloodgroup;
        this.contactNo=contactNo;
        this.reasonForRequest=reasonForRequest;
        this.timeStamp=timeStamp;
        this.userid=userid;
    }
}
