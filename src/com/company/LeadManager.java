package com.company;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class LeadManager {

    private static int latestIdLead;
    private Lead[] Leads = new Lead[100];
    private int size;

    public static int getLatestIdLead() {
        return latestIdLead;
    }

    public static void setLatestIdLead(int latestID) {
        LeadManager.latestIdLead = latestID;
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

    public Object[] askForLDetail() {
        Object[] leadDetail = null;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Lead's name: ");
            String l_name = scanner.nextLine();
            System.out.print("Lead's date-of-birth (dd-MM-yyyy): ");
            String dob = scanner.nextLine();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date checkDate;
            checkDate = df.parse(dob);
            System.out.print("Lead's gender (true if man/false if woman): ");
            boolean l_gender = scanner.nextBoolean();
            scanner.nextLine();
            System.out.print("Lead's phone number: ");
            String l_phone = scanner.nextLine();
            int checkPhone;
            checkPhone = Integer.parseInt(l_phone);
            System.out.print("Lead's email: ");
            String l_email = scanner.nextLine();
            System.out.print("Lead's address: ");
            String l_address = scanner.nextLine();
            leadDetail = new Object[]{l_name,
                                      checkDate,
                                      Boolean.toString(l_gender),
                                      l_phone,
                                      l_email,
                                      l_address};
        }catch (ParseException | InputMismatchException | NumberFormatException e){
            System.out.println("Wrong Input type");
        }
        return leadDetail;
    }

    public void addLead(){
        try {
            Object[] leadDetail = askForLDetail();
            Lead newLead = new Lead(
                    PaddingZeros(latestIdLead),
                    leadDetail[0].toString(),
                    (Date) leadDetail[1],
                    Boolean.getBoolean((String) leadDetail[2]),
                    leadDetail[3].toString(),
                    leadDetail[4].toString(),
                    leadDetail[5].toString());
            setLatestIdLead(++latestIdLead);
            this.Leads[size] = newLead;
            this.setSize(size + 1);
        }catch (NullPointerException e){
            System.out.println("");
        }
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

    public void delete(){
        try {// For File not found and not found ID
            boolean found = false;
            Scanner scanner = new Scanner(new File("Lead.txt"));
            Scanner scannerInput = new Scanner(System.in);
            System.out.print("ID lead you want to delete?: ");
            String id = scannerInput.nextLine();
            String l_id = null;
            try {
                l_id = "lead_" + PaddingZeros(Integer.parseInt(id));
            }catch (NumberFormatException e){
                System.out.print("");
            }
            StringBuffer updateList = new StringBuffer();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(l_id)) {
                    found = true;
                    updateList.append(l_id).append(",").append("deleted").append("\n");
                } else {
                    updateList.append(line).append("\n").append(";");
                }
            }
            String[] updateArray = updateList.toString().split(";");
            FileWriter fileWriter = new FileWriter(new File("Lead.txt"));
            for (int i = 0; i < updateArray.length; i++) {
                fileWriter.write(updateArray[i]);
            }
            fileWriter.close();
            System.out.println("Done");
            if (!found){
                System.out.println("Not found or wrong input type");
            }
        }
        catch (IOException | NullPointerException e){
            System.out.println("");
        }
    }

    public void update(){
        try {// For File not found and not found ID
            boolean found = false;
            Scanner scanner = new Scanner(new File("Lead.txt"));
            Scanner scannerInput = new Scanner(System.in);
            System.out.print("ID lead you want to change?: ");
            String id = scannerInput.nextLine();
            String l_id = null;
            try {
                l_id = "lead_" + PaddingZeros(Integer.parseInt(id));
            }catch (NumberFormatException e){
                System.out.print("");
            }
            StringBuffer updateList = new StringBuffer();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(l_id) && !parts[1].equals("deleted")) {
                    found = true;
                    Object[] updateLeadArr = askForLDetail();
                    updateList.append(l_id).append(",");
                    for (int pElement = 1, uElement = 0; pElement < parts.length; pElement++, uElement++) {
                        if (pElement == 2) {
                            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            parts[2] = df.format(updateLeadArr[1]);
                        } else {
                            parts[pElement] = updateLeadArr[uElement].toString();
                        }
                    }
                    String updateLine = parts[1] + "," + parts[2] + "," + parts[3] + "," + parts[4] + "," + parts[5] + "," + parts[6] + "\n";
                    updateList.append(updateLine).append(";");
                } else {
                    updateList.append(line).append("\n").append(";");
                }
            }
            String[] updateArray = updateList.toString().split(";");
            FileWriter fileWriter = new FileWriter(new File("Lead.txt"));
            for (int i = 0; i < updateArray.length; i++) {
                fileWriter.write(updateArray[i]);
            }
            fileWriter.close();
            if (!found){
                System.out.println("Not found or wrong input type");
            }
        }
        catch (IOException | NullPointerException e){
            System.out.println("");
        }
    }

    public static String PaddingZeros(int value) {
        return String.format("%03d", value);
    }
}
