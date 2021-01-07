package com.company;

import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {
        int lastID = 0;
        LeadManager leadManager = new LeadManager();
        leadManager.addlead();
        leadManager.addlead();
        leadManager.serialize();
    }
}
