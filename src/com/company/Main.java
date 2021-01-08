package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException, FileNotFoundException {
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

//        InteractionManager.setLatestIdInter(lastIdInter);
//        interactionManager.addInteraction();
//        interactionManager.serialize();

//        LeadManager.setLatestIdLead(lastIdLead);
//        leadManager.addlead();
//        leadManager.addlead();
//        leadManager.serialize();

//        while (true){
//            System.out.print("Hello! How is your day \n" +
//                    "Type your order");
//            String userInput = inputScanner.next();
//            if (userInput.equals("exit".toLowerCase())){
//                break;
//            }
//        }

    }
}
