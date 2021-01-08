package com.company;

import  java.util.*;
import  java.text.*;

public class Lead {

    private String lead_id;
    private String lead_name;
    private Date lead_dob;
    private boolean lead_gender;
    private String lead_phone;
    private String lead_email;
    private String lead_address;

    public Lead(String lead_id, String lead_name, Date lead_dob, boolean lead_gender, String lead_phone, String lead_email, String lead_address) {
        this.lead_id = lead_id;
        this.lead_name = lead_name;
        this.lead_dob = lead_dob;
        this.lead_gender = lead_gender;
        this.lead_phone = lead_phone;
        this.lead_email = lead_email;
        this.lead_address = lead_address;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getLead_name() {
        return lead_name;
    }

    public void setLead_name(String lead_name) {
        this.lead_name = lead_name;
    }

    public Date getLead_dob() {
        return lead_dob;
    }

    public void setLead_dob(Date lead_dob) {
        this.lead_dob = lead_dob;
    }

    public boolean isLead_gender() {
        return lead_gender;
    }

    public void setLead_gender(boolean lead_gender) {
        this.lead_gender = lead_gender;
    }

    public String getLead_phone() {
        return lead_phone;
    }

    public void setLead_phone(String lead_phone) {
        this.lead_phone = lead_phone;
    }

    public String getLead_email() {
        return lead_email;
    }

    public void setLead_email(String lead_email) {
        this.lead_email = lead_email;
    }

    public String getLead_address() {
        return lead_address;
    }

    public void setLead_address(String lead_address) {
        this.lead_address = lead_address;
    }

    public String DatetoString(Date d){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(d);}

    @Override
    public String toString() {
        return "lead_" + lead_id + ',' + lead_name  + ',' + DatetoString(lead_dob) + ',' + lead_gender + ',' + lead_phone + ',' + lead_email + ',' + lead_address;
    }
}
