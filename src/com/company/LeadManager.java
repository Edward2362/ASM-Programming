package com.company;

import java.io.*;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LeadManager {

    private static int l_id = 0;
    private Lead[] Leads = new Lead[100];
    private int size;
    private int lastID;

    public static int getL_id() {
        return l_id;
    }

    public static void setL_id(int l_id) {
        LeadManager.l_id = l_id;
    }

    public Lead[] getLeads() {
        return Leads;
    }

    public void setLeads(Lead[] leads) {
        Leads = leads;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public void addlead() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Lead's name: ");
        String l_name = scanner.nextLine();
        System.out.print("Lead's date-of-birth: ");
        String dob = scanner.nextLine();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date dobCheck;
        dobCheck = df.parse(dob);
        String l_dob = df.format(dobCheck);
        System.out.print("Lead's gender: ");
        boolean l_gender = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Lead's phone number: ");
        String l_phone = scanner.nextLine();
        System.out.print("Lead's email: ");
        String l_email = scanner.nextLine();
        System.out.print("Lead's address: ");
        String l_address = scanner.nextLine();
        this.setL_id(this.lastID);
        Lead lead1 = new Lead(PaddingZeros(l_id), l_name, l_dob, l_gender, l_phone, l_email, l_address);
        this.setLastID(this.lastID + 1);
        this.Leads[size] = lead1;
        this.setSize(size + 1);
    }

    public void serialize(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("Lead.txt"), true);
            for (int i = 0; i < getSize(); i++) {
                fileWriter.write(String.valueOf(this.getLeads()[i].toString()) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String PaddingZeros(int value) {
        return String.format("%03d", value);
    }
}
