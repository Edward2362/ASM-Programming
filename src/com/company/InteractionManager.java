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

    public void addInteraction() throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Date of interaction (dd-MM-yyyy): ");
        String doi = scanner.nextLine();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date doiCheck;
        doiCheck = df.parse(doi);
        String i_doi = df.format(doiCheck);
        System.out.print("Involved lead: ");
        String i_lead = scanner.nextLine();
        System.out.print("Mean of interaction (email/ telephone/ face to face/ social media): ");
        String i_mean = scanner.nextLine();
        System.out.print("Interaction potential (positive/ neutral/ negative): ");
        String i_potential = scanner.nextLine();
        Interaction inter1 = new Interaction(PaddingZeros(latestIdInter), doiCheck, i_lead, i_mean, i_potential);
        setLatestIdInter(++latestIdInter);
        this.Inters[size] = inter1;
        this.setSize(size + 1);
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

    public static String PaddingZeros(int value) {
        return String.format("%03d", value);
    }
}
