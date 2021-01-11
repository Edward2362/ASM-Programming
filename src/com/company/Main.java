package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, IOException {
        LeadManager leadManager = new LeadManager();
        InteractionManager interactionManager = new InteractionManager();
        Scanner inputScanner = new Scanner(System.in);
        int lastIdLead = 0;
        int lastIdInter = 0;

        Scanner scannerLead = new Scanner(new File("Lead.txt"));
        while (scannerLead.hasNext()){
            System.out.println(scannerLead.nextLine());
            lastIdLead++;
        }

        Scanner scannerInter = new Scanner(new File("Interaction.txt"));
        while (scannerInter.hasNext()){
            System.out.println(scannerInter.nextLine());
            lastIdInter++;
        }

        InteractionManager.setLatestIdInter(lastIdInter);
//        interactionManager.askForIDetail();
//        interactionManager.addInteraction();
//        interactionManager.serialize();

//        interactionManager.update();
//        interactionManager.delete();

//        interactionManager.intersByPotential();
//        interactionManager.intersByMonth();


        LeadManager.setLatestIdLead(lastIdLead);
        leadManager.askForLDetail();
//        leadManager.addLead();
//        leadManager.serialize();

//        leadManager.update();
//        leadManager.delete();

//        leadManager.leadsByAges();

//        while (true){
//            System.out.print("Hello! How is your day \n" +
//                    "Type your order");
//            String userInput = inputScanner.next();23
//            if (userInput.equals("exit".toLowerCase())){
//                break;
//            }
//        }
        //Menu
//        while(true){
//            System.out.println("Welcome to our company web \n"+"");
//        }

    }
}
