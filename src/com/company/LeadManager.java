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
        Scanner scanner = new Scanner(System.in);
        boolean check = false;
        String l_name = "";
        Date l_dob = null;
        String l_gender = "";
        String l_phone = "";
        String l_email = "";
        String l_address = "";

        while (!check) {
            System.out.print("Lead's name: ");
            l_name = scanner.nextLine();
            if (!checkName(l_name)){
                System.out.println("Invalid name!");
            }else if (l_name.equals("")){
                System.out.println("Name cannot be blank!");
            }
            else {
                check = true;
            }
        }
        check = false;

        while (!check) {
            try {
                System.out.print("Lead's date-of-birth (dd-MM-yyyy): ");
                String unTestedDate = scanner.nextLine();
                if (unTestedDate.equals("")){
                    System.out.println("Date-of-birth cannot be blank!");
                }else {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    l_dob = df.parse(unTestedDate);
                    check = true;
                }
            } catch (ParseException e) {
                System.out.println("Wrong Date format!");
                check = false;
            }
        }
        check = false;

        while (!check) {
//            try {
                System.out.print("Lead's gender (true if man/false if woman): ");
                l_gender = scanner.nextLine();
                if (l_gender.equals("")){
                    System.out.println("Gender cannot be blank!");
                }else if (!(l_gender.equalsIgnoreCase("true") || l_gender.equalsIgnoreCase("false"))){
                    System.out.println("gender can only true/man or false/woman!");
                }else {
                    check = true;
                }
//            } catch (InputMismatchException e) {
//                System.out.println("gender can only true/man or false/woman!");
//                scanner.nextLine();
//                check = false;
//            }
        }
        check = false;

        while (!check) {
            try {
                System.out.print("Lead's phone number: ");
                l_phone = scanner.nextLine();
                String regexStr = "^[0-9]{10}$";
                if (l_phone.equals("")){
                    System.out.println("Phone cannot be blank!");
                }else if (!l_phone.matches(regexStr)){
                    System.out.println("Wrong phone format!");
                }else {
                    int checkPhone = Integer.parseInt(l_phone);
                    check = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong phone format!");
                check = false;
            }
        }
        check = false;

        while (!check){
            System.out.print("Lead's email: ");
            l_email = scanner.nextLine();
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";
            if (!l_email.matches(emailRegex)){
                System.out.println("Wrong email format!");
            }else {
                check = true;
            }
        }
        check = false;

        while (!check) {
            System.out.print("Lead's address: ");
            l_address = scanner.nextLine();
            if (!checkAddress(l_address)){
                System.out.println("Invalid address!");
            }else {
                check = true;
            }
        }

        return new Object[]{l_name,
                            l_dob,
                            Boolean.parseBoolean(l_gender),
                            l_phone,
                            l_email,
                            l_address};
    }

    public void addLead(){
        try {
            Object[] leadDetail = askForLDetail();
            Lead newLead = new Lead(
                    modifyId(latestIdLead),
                    leadDetail[0].toString(),
                    (Date) leadDetail[1],
                    (Boolean) leadDetail[2],
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
                fileWriter.write(this.getLeads()[i].toString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Missing file Lead.txt");
        }
    }

    public void delete(){
        try {// For File not found and not found ID
            Scanner scanner = new Scanner(new File("Lead.txt"));
            Scanner scannerInput = new Scanner(System.in);
            boolean found = false;
            String id = "";

            boolean check = false;
            String l_id = null;
            while (!check) {
                try {
                    System.out.print("ID lead you want to delete?: ");
                    id = scannerInput.nextLine();
                    int checkId = Integer.parseInt(id);
                    l_id = "lead_" + modifyId(checkId);
                    check = true;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong ID format!");
                    check = false;
                }
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
            scanner.close();

            String[] updateArray = updateList.toString().split(";");
            FileWriter fileWriter = new FileWriter(new File("Lead.txt"));
            for (int i = 0; i < updateArray.length; i++) {
                fileWriter.write(updateArray[i]);
            }
            fileWriter.close();
            if (!found){
                System.out.println("Not found!");
            }
        }
        catch (IOException e){
            System.out.println("Missing file Lead.txt");
        }
    }

    public void update(){
        try {// For File not found and not found ID
            Scanner scanner = new Scanner(new File("Lead.txt"));
            Scanner scannerInput = new Scanner(System.in);
            boolean found = false;
            String id = "";

            boolean check = false;
            String l_id = null;
            while (!check) {
                try {
                    System.out.print("ID lead you want to delete?: ");
                    id = scannerInput.nextLine();
                    int checkId = Integer.parseInt(id);
                    l_id = "lead_" + modifyId(checkId);
                    check = true;
                } catch (NumberFormatException e) {
                    System.out.println("Wrong ID format!");
                    check = false;
                }
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
            scanner.close();

            String[] updateArray = updateList.toString().split(";");
            FileWriter fileWriter = new FileWriter(new File("Lead.txt"));
            for (int i = 0; i < updateArray.length; i++) {
                fileWriter.write(updateArray[i]);
            }
            fileWriter.close();
            if (!found){
                System.out.println("Not found!");
            }
        }
        catch (IOException e){
            System.out.println("Missing file Lead.txt");
        }
    }

    public void leadsByAges() {
        try {
            Scanner scanner = new Scanner(new File("Lead.txt"));

            String[] data;
            int age10 = 0;
            int age10to20 = 0;
            int age20to60 = 0;
            int age60above = 0;
            while (scanner.hasNext()) {  //returns a boolean value
                data = scanner.nextLine().split(",");
                if (data[1].equals("deleted") && scanner.hasNext()) {
                    data = scanner.nextLine().split(",");
                }else if (data[1].equals("deleted")) {
                    scanner.close();
                    break;
                }

                Date birthdate = new SimpleDateFormat("dd-MM-yyyy").parse(data[2]);
                Date currentDate = new Date();
                int diffInDays = (int) ((currentDate.getTime() - birthdate.getTime())
                        / (1000 * 60 * 60 * 24));
                double age = diffInDays / 365.25;
                double actualAge = Math.floor(age);

                if (actualAge > 60) {
                    age60above++;
                } else if (actualAge > 20) {
                    age20to60++;
                } else if (actualAge > 10) {
                    age10to20++;
                } else {
                    age10++;
                }
            }
            System.out.format("%-21s%-21s%-21s%-21s","0-10 (years old)", "10-20 (years old)", "20-60 (years old)", "60+ (years old)");
            System.out.println("");
            System.out.format("%-21s%-21s%-21s%-21s", age10, age10to20, age20to60, age60above);


            scanner.close();
        }catch (ParseException | FileNotFoundException e){
            System.out.println("Missing file Lead.txt");
        }
    }

    public boolean checkName(String name){
        char checkChar;
        String lowerName = name.toLowerCase();
        String[] nameArr = lowerName.split("");
        StringBuffer listAlphabet = new StringBuffer();
        listAlphabet.append(" ");
        for (checkChar = 'a'; checkChar < 'z'; ++checkChar) {
            listAlphabet.append(checkChar);
        }
        int elementInArr = 0;
        while (elementInArr < nameArr.length){
            boolean check = listAlphabet.toString().contains(nameArr[elementInArr]);
            if (!check){
                return false;
            }else {
                elementInArr++;
            }
        }
        return true;
    }

    public boolean checkAddress(String address) {
        char checkChar;
        String lowerAddress = address.toLowerCase();
        String[] addressArr = lowerAddress.split(" ");
        if (addressArr.length < 2) {
            return false;
        } else {
            try {
                int checkAddressNumber = Integer.parseInt(addressArr[0]);
            } catch (NumberFormatException e) {
                return false;
            }
            StringBuffer listOfAlphabet = new StringBuffer();
            listOfAlphabet.append(" ");
            for (checkChar = 'a'; checkChar < 'z'; ++checkChar) {
                listOfAlphabet.append(checkChar);
            }
            for (int a = 1; a < addressArr.length; a++) {
                int elementInArr = 0;
                String[] elementArr = addressArr[a].split("");
                while (elementInArr < elementArr.length) {
                    boolean check = listOfAlphabet.toString().contains(elementArr[elementInArr]);
                    if (!check) {
                        return false;
                    } else {
                        elementInArr++;
                    }
                }
            }
            return true;
        }
    }

    public static String modifyId(int value) {
        return String.format("%03d", value);
    }
}
