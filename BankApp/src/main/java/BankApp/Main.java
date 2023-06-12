package BankApp;

import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean mainEnd = false;
        while (!mainEnd) {
            try {
                System.out.println("Zadejte číslo účtu: ");
                String accountNumber = sc.next();
                if(accountNumber.equals("end")){
                    mainEnd=true;
                }
                BankApp.Ucet ucet = new Ucet(accountNumber);
                String choice;
                printMenu();
                choice = sc.next();
                switch (choice) {
                    case "1" -> ucet.deposit(sc);
                    case "2" -> ucet.withdraw(sc);
                    case "3" -> ucet.showTransactions();
                    case "4" -> ucet.calculateBalance();
                    case "5" -> ucet.calculateBalanceForPeriod(sc);
                    case "6" -> {
                        System.out.println("Ukončení aplikace");
                        mainEnd=true;
                    }
                    default -> System.out.println("Neplatná volba");
                }
            } catch (Exception e) {
                System.err.println("Něco se pokazilo...\n" +
                        "zkus to znovu a lépe BRUH");
                }
            }
        }

    public static void printMenu(){
    System.out.println("\n1. Provedení vkladu\n" +
            "2. Provedení výběru\n" +
            "3. Zobrazení výpisu z účtu\n" +
            "4. Výpočet bilance\n" +
            "5. Výpočet bilance v zadaném období\n" +
            "6. Ukončení aplikace\n" +
            "Vyberte akci: \n");
    }
}

