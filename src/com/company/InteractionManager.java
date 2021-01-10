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
        Object[] interDetail = null;
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Date of interaction (dd-MM-yyyy): ");
            String doi = scanner.nextLine();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date checkDate;
            checkDate = df.parse(doi);

            System.out.print("Involved lead: ");
            String i_lead = scanner.nextLine();
            int checkId;
            checkId = Integer.parseInt(i_lead);

            System.out.print("Mean of interaction (email/ telephone/ face to face/ social media): ");
            String i_mean = scanner.nextLine();

            System.out.print("Interaction potential (positive/ neutral/ negative): ");
            String i_potential = scanner.nextLine();

            interDetail = new Object[]{checkDate,
                                       i_lead,
                                       i_mean,
                                       i_potential};
        }catch (ParseException | NumberFormatException e){
            System.out.println("Wrong Input type");
        }
        return interDetail;
    }

    public void addInteraction(){
        try {
            Object[] interDetail = askForIDetail();
            Interaction inter1 = new Interaction(
                    PaddingZeros(latestIdInter),
                    (Date) interDetail[0],
                    "lead_" + PaddingZeros(Integer.parseInt(interDetail[1].toString())),
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
            e.printStackTrace();
        }
    }

    public void delete(){
        try {// For File not found and not found ID
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);

            boolean found = false;
            System.out.print("ID interaction you want to delete?: ");
            String id = scannerInput.nextLine();
            String i_id = null;
            try {
                i_id = "inter_" + PaddingZeros(Integer.parseInt(id));
            }catch (NumberFormatException e){
                System.out.print("");
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
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);

            boolean found = false;
            System.out.print("ID interaction you want to change?: ");
            String id = scannerInput.nextLine();
            String i_id = null;
            try {
                i_id = "inter_" + PaddingZeros(Integer.parseInt(id));
            }catch (NumberFormatException e){
                System.out.print("");
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
                    String updateLine = parts[1] + "," + "lead_" + PaddingZeros(Integer.parseInt(parts[2])) + "," + parts[3] + "," + parts[4] + "\n";
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
                System.out.println("Not found or wrong input type");
            }
        }
        catch (IOException | NullPointerException e){
            System.out.println("");
        }
    }

    public void intersByPotential() {
        try {
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date checkSDate = null;
            Date checkEDate = null;

            try {
                System.out.print("Start date (dd-MM-yyyy):");
                String startDate = scannerInput.nextLine();
                checkSDate = df.parse(startDate);

                System.out.print("End date (dd-MM-yyyy):");
                String endDate = scannerInput.nextLine();
                checkEDate = df.parse(endDate);
            }catch (ParseException e){
                System.out.println("Wrong Input Type!");
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

                boolean reportPeriod = dateInter.compareTo(checkSDate) >= 0 && dateInter.compareTo(checkEDate) <= 0;

                if (reportPeriod && data[4].equals("positive")) {
                    positive++;
                } else if (reportPeriod && data[4].equals("neutral")) {
                    neutral++;
                } else {
                    negative++;
                }
            }
            System.out.println("Positive: " + positive);
            System.out.println("Neutral: " + neutral);
            System.out.println("Negative: " + negative);

            scanner.close();
        }catch (ParseException | FileNotFoundException | NullPointerException e){
            System.out.println("");
        }
    }

    public void intersByMonth() {
        try {
            Scanner scanner = new Scanner(new File("Interaction.txt"));
            Scanner scannerInput = new Scanner(System.in);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date checkSDate = null;
            Date checkEDate = null;

            

            try {
                System.out.print("Start date (dd-MM-yyyy):");
                String startDate = scannerInput.nextLine();
                checkSDate = df.parse(startDate);

                System.out.print("End date (dd-MM-yyyy):");
                String endDate = scannerInput.nextLine();
                checkEDate = df.parse(endDate);
            }catch (ParseException e){
                System.out.println("Wrong Input Type!");
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

                boolean reportPeriod = dateInter.compareTo(checkSDate) >= 0 && dateInter.compareTo(checkEDate) <= 0;

                if (reportPeriod && data[4].equals("positive")) {
                    positive++;
                } else if (reportPeriod && data[4].equals("neutral")) {
                    neutral++;
                } else {
                    negative++;
                }
            }
            System.out.println("Positive: " + positive);
            System.out.println("Neutral: " + neutral);
            System.out.println("Negative: " + negative);

            scanner.close();
        }catch (ParseException | FileNotFoundException | NullPointerException e){
            System.out.println("");
        }
    }

    public static String PaddingZeros(int value) {
        return String.format("%03d", value);
    }
}
