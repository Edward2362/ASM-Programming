package com.company;

import java.io.*;
import java.net.PasswordAuthentication;
import java.util.*;
import java.text.*;

public class InteractionManager {

    private static int latestIdInter = 0;
    private Interaction[] Inters = new Interaction[100];
    private int size;

    public static int getLatestIdInter() {
        return latestIdInter;
    }

    public static void setLatestIdInter(int latestIdInter) {
        InteractionManager.latestIdInter = latestIdInter;
    }

    public Interaction[] getInters() {
        return Inters;
    }

    public void setInters(Interaction[] inters) {
        Inters = inters;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object[] askForIDetail() {
        Scanner scanner = new Scanner(System.in);
        boolean check = false;
        Date i_doi = null;
        String i_lead = "";
        String i_mean = "";
        String i_potential = "";

        while (!check) {
            try {
                System.out.print("Date of interaction (dd-MM-yyyy): ");
                String unTestedDate = scanner.nextLine();
                if (unTestedDate.equals("")){
                    System.out.println("Date cannot be blank!");
                }else {
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    i_doi = df.parse(unTestedDate);
                    check = true;
                }
            } catch (ParseException e) {
                System.out.println("Wrong Date format!");
                check = false;
            }
        }
        check = false;

        while (!check) {
            try {
                System.out.print("Involved lead: ");
                i_lead = scanner.nextLine();
                if (i_lead.equals("")){
                    System.out.println("Lead ID cannot be blank!");
                }else {
                    int checkId = Integer.parseInt(i_lead);
                    check = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wrong ID format!");
                check = false;
            }
        }
        check = false;

        while (!check) {
            System.out.print("Mean of interaction (email/ telephone/ face to face/ social media): ");
            i_mean = scanner.nextLine();
            if (i_mean.equals("")){
                System.out.println("Mean of interaction cannot be blank!");
            }else if (!(i_mean.equalsIgnoreCase("email") ||
                    i_mean.equalsIgnoreCase("telephone") ||
                    i_mean.equalsIgnoreCase("face to face") ||
                    i_mean.equalsIgnoreCase("social media"))) {
                System.out.println("Wrong mean of contact!");
            }else {
                check = true;
            }
        }
        check = false;

        while (!check) {
            System.out.print("Interaction potential (positive/ neutral/ negative): ");
            i_potential = scanner.nextLine();
            if (i_potential.equals("")){
                System.out.println("Interaction potential cannot be blank!");
            } else if (!(i_potential.equalsIgnoreCase("positive") ||
                    i_potential.equalsIgnoreCase("neutral") ||
                    i_potential.equalsIgnoreCase("negative"))) {
                System.out.println("Wrong rating format!");
            }else {
                check = true;
            }
        }
        return new Object[]{i_doi,
                            i_lead,
                            i_mean,
                            i_potential};
    }

    public void addInteraction(){
        try {
            Object[] interDetail = askForIDetail();
            Interaction inter1 = new Interaction(
                    modifyId(latestIdInter),
                    (Date) interDetail[0],
                    "lead_" + modifyId(Integer.parseInt(interDetail[1].toString())),
                    interDetail[2].toString(),
                    interDetail[3].toString());
            setLatestIdInter(++latestIdInter);
            this.Inters[size] = inter1;
            this.setSize(size + 1);
        }catch (NullPointerException e){
            System.out.println("");
        }
    }

    public void serialize(){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File("Interaction.txt"), true);
            for (int i = 0; i < getSize(); i++) {
                fileWriter.write(String.valueOf(this.getInters()[i].toString1()) + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Missing file Interaction.txt");;
        }
    }

    public void delete(){
        try {// For File not found and not found ID
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);
            boolean found = false;
            String id = "";

            boolean check = false;
            String i_id = null;
            while (!check) {
                try {
                    System.out.print("ID interaction you want to delete?: ");
                    id = scannerInput.nextLine();
                    int checkId = Integer.parseInt(id);
                    i_id = "inter_" + modifyId(checkId);
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
                if (parts[0].equals(i_id)) {
                    found = true;
                    updateList.append(i_id).append(",").append("deleted").append("\n");
                } else {
                    updateList.append(line).append("\n").append(";");
                }
            }
            scanner.close();

            String[] updateArray = updateList.toString().split(";");
            FileWriter fileWriter = new FileWriter(new File("Interaction.txt"));
            for (int i = 0; i < updateArray.length; i++) {
                fileWriter.write(updateArray[i]);
            }
            fileWriter.close();
            if (!found){
                System.out.println("Not found or wrong input type");
            }
        }
        catch (IOException | NullPointerException e){
            System.out.println("Missing file Interaction.txt");
        }
    }

    public void update(){
        try {// For File not found and not found ID
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);
            boolean found = false;
            String id = "";

            boolean check = false;
            String i_id = null;
            while (!check) {
                try {
                    System.out.print("ID interaction you want to delete?: ");
                    id = scannerInput.nextLine();
                    int checkId = Integer.parseInt(id);
                    i_id = "inter_" + modifyId(checkId);
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
                if (parts[0].equals(i_id) && !parts[1].equals("deleted")) {
                    found = true;
                    Object[] updateLeadArr = askForIDetail();
                    updateList.append(i_id).append(",");
                    for (int pElement = 1, uElement = 0; pElement < parts.length; pElement++, uElement++) {
                        if (pElement == 1) {
                            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            parts[1] = df.format(updateLeadArr[0]);
                        } else {
                            parts[pElement] = updateLeadArr[uElement].toString();
                        }
                    }
                    String updateLine = parts[1] + "," + "lead_" + modifyId(Integer.parseInt(parts[2])) + "," + parts[3] + "," + parts[4] + "\n";
                    updateList.append(updateLine).append(";");
                } else {
                    updateList.append(line).append("\n").append(";");
                }
            }
            scanner.close();

            String[] updateArray = updateList.toString().split(";");
            FileWriter fileWriter = new FileWriter(new File("Interaction.txt"));
            for (int i = 0; i < updateArray.length; i++) {
                fileWriter.write(updateArray[i]);
            }
            fileWriter.close();
            if (!found){
                System.out.println("Not found!");
            }
        }
        catch (IOException | NullPointerException e){
            System.out.println("Missing file Interaction.txt");
        }
    }

    public void intersByPotential() {
        try {
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = null;
            Date endDate = null;

            try {
                while (true){
                    System.out.print("Start date (dd-MM-yyyy):");
                    String unTestedSDate = scannerInput.nextLine();
                    startDate = df.parse(unTestedSDate);

                    System.out.print("End date (dd-MM-yyyy):");
                    String unTestedEDate = scannerInput.nextLine();
                    endDate = df.parse(unTestedEDate);
                    if (endDate.before(startDate)){
                        System.out.println("End date is before start date");
                    }else{
                        break;
                    }
                }
            }catch (ParseException e){
                System.out.println("Wrong Date format!");
            }

            String[] data;
            int positive = 0;
            int negative = 0;
            int neutral = 0;
            while (scanner.hasNext()) {  //returns a boolean value
                data = scanner.nextLine().split(",");
                if (data[1].equals("deleted") && scanner.hasNext()) {
                    data = scanner.nextLine().split(",");
                }else if (data[1].equals("deleted")) {
                    scanner.close();
                    break;
                }

                Date dateInter = new SimpleDateFormat("dd-MM-yyyy").parse(data[1]);
                boolean reportPeriod = dateInter.compareTo(startDate) >= 0 && dateInter.compareTo(endDate) <= 0;

                if (reportPeriod && data[4].equals("positive")) {
                    positive++;
                } else if (reportPeriod && data[4].equals("neutral")) {
                    neutral++;
                } else if (reportPeriod && data[4].equals("negative")){
                    negative++;
                }
            }
            System.out.format("%-15s%-15s%-15s","Positive", "Neutral", "Negative");
            System.out.println("");
            System.out.format("%-15s%-15s%-15s", positive, neutral, negative);

            scanner.close();
        }catch (ParseException | FileNotFoundException | NullPointerException e){
            System.out.println("Missing file Interaction.txt");
        }
    }

    public void intersByMonth() {
        try {
            Scanner inputScanner = new Scanner(System.in);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date startDate = null;
            Date endDate = null;

            try {
                while (true){
                    System.out.print("Start date (dd-MM-yyyy):");
                    String unTestedSDate = inputScanner.nextLine();
                    startDate = df.parse(unTestedSDate);

                    System.out.print("End date (dd-MM-yyyy):");
                    String unTestedEDate = inputScanner.nextLine();
                    endDate = df.parse(unTestedEDate);
                    if (endDate.before(startDate)){
                        System.out.println("End date is before start date");
                    }else{
                        break;
                    }
                }
            }catch (ParseException e){
                System.out.println("Wrong Date format!");
            }

            StringBuffer monthsWithYear = new StringBuffer();
            int yearOfSDate = startDate.getYear() + 1900;
            int yearOfEDate = endDate.getYear() + 1900;

            if (yearOfSDate < yearOfEDate){
                for (int monthOfSYear = startDate.getMonth() + 1; monthOfSYear < 13; monthOfSYear++) {
                    monthsWithYear.append(String.format("%02d", monthOfSYear)).append("-").append(yearOfSDate).append(",");
                }
                while (yearOfSDate < yearOfEDate){
                    yearOfSDate++;
                    if (yearOfSDate == yearOfEDate){
                        for (int monthOfEyear = 1; monthOfEyear <= endDate.getMonth() + 1; monthOfEyear++) {
                            monthsWithYear.append(String.format("%02d", monthOfEyear)).append("-").append(yearOfSDate).append(",");
                        }
                    }else {
                        for (int monthOfOYear = 1; monthOfOYear < 13; monthOfOYear++) {
                            monthsWithYear.append(String.format("%02d", monthOfOYear)).append("-").append(yearOfSDate).append(",");
                        }
                    }
                }
            }else {
                for (int monthSYear = startDate.getMonth() + 1; monthSYear <= endDate.getMonth() + 1; monthSYear++) {
                    monthsWithYear.append(monthSYear).append("-").append(yearOfSDate).append(",");
                }
            }

            String[] mWYArr = monthsWithYear.toString().split(",");
            int [] countInter = new int[mWYArr.length];
            String[] data;
            int display = 0;
            while (display < mWYArr.length) {
                int counter = 0;
                Scanner scanner = new Scanner(new File("Interaction.txt"));
                while (scanner.hasNext()) {  //returns a boolean value
                    data = scanner.nextLine().split(",");
                    if (data[1].equals("deleted") && scanner.hasNext()) {
                        data = scanner.nextLine().split(",");
                    } else if (data[1].equals("deleted")) {
                        scanner.close();
                        break;
                    }

                    Date dateInterFullDate = new SimpleDateFormat("dd-MM-yyyy").parse(data[1]);

                    boolean reportPeriod = dateInterFullDate.compareTo(startDate) >= 0 && dateInterFullDate.compareTo(endDate) <= 0;

                    if (reportPeriod && data[1].contains(mWYArr[display])) {
                        counter++;
                    }
                }
                scanner.close();

                countInter[display] = counter;
                display++;
            }

            for (int i = 0; i < mWYArr.length; i++) {
                System.out.format("%-15s", mWYArr[i]);
            }
            System.out.println("");
            for (int i = 0; i < countInter.length; i++) {
                System.out.format("%-15s", countInter[i]);
            }
        }catch (ParseException | FileNotFoundException | NullPointerException e){
            System.out.println("Missing file Interaction.txt");
        }
    }

    public static String modifyId(int value) {
        return String.format("%03d", value);
    }
}
