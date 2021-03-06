package com.company;

import java.util.Date;
import java.text.*;

public class Interaction {

    private String inter_id;
    private Date inter_doi;
    private String inter_ref;
    private String inter_mean;
    private String inter_potential;

    public Interaction(String inter_id, Date inter_doi, String inter_ref, String inter_mean, String inter_potential) {
        this.inter_id = inter_id;
        this.inter_doi = inter_doi;
        this.inter_ref = inter_ref;
        this.inter_mean = inter_mean;
        this.inter_potential = inter_potential;
    }

    public String getInter_id() {
        return inter_id;
    }

    public void setInter_id(String inter_id) {
        this.inter_id = inter_id;
    }

    public Date getInter_doi() {
        return inter_doi;
    }

    public void setInter_doi(Date inter_doi) {
        this.inter_doi = inter_doi;
    }

    public String getInter_ref() {
        return inter_ref;
    }

    public void setInter_ref(String inter_ref) {
        this.inter_ref = inter_ref;
    }

    public String getInter_mean() {
        return inter_mean;
    }

    public void setInter_mean(String inter_mean) {
        this.inter_mean = inter_mean;
    }

    public String getInter_potential() {
        return inter_potential;
    }

    public void setInter_potential(String inter_potential) {
        this.inter_potential = inter_potential;
    }

    public String DatetoString(Date d){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(d);}

    public String toString1() {
        return "inter_" + inter_id + ',' + DatetoString(inter_doi) + ',' + inter_ref + ',' + inter_mean + ',' + inter_potential;
    }
}
