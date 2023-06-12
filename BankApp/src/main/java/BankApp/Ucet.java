package BankApp;

import java.io.*;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Ucet {
    private String accountNumber;
    private String filename;

    public Ucet(String accountNumber) {
        this.accountNumber = accountNumber;
        this.filename = accountNumber + ".dat";
    }

    public void deposit(Scanner sc) throws IOException {
        System.out.print("Zadejte částku pro vklad: ");
        double amount = sc.nextDouble();
        System.out.print("Zadejte datum (dd-mm-yyyy): ");
        String date = sc.next();
        Transakce transakce = new Transakce(date, amount);
        transakce.saveTransactionToFile(this.filename);
        System.out.println("Vklad byl vložen");
    }

    public void withdraw(Scanner sc) throws IOException {
        System.out.print("Zadejte částku pro výběr: ");
        double amount = sc.nextDouble();
        System.out.print("Zadejte datum (dd-mm-yyyy): ");
        String date = sc.next();
        Transakce transakce = new Transakce(date, -amount);  // Výběr peněz se ukládá jako záporná částka
        transakce.saveTransactionToFile(this.filename);
        System.out.println("Částka: "+amount+"kč byla vybrána z účtu.");
    }

    public void showTransactions() throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(this.filename)));
        DecimalFormat df = new DecimalFormat("###,###.##");
        try {
            while (true) {
                String date = dis.readUTF();
                double amount = dis.readDouble();
                System.out.println(date + ": " + (amount > 0 ? "Vklad" : "Výběr") + " " + df.format(Math.abs(amount)));
            }
        } catch (EOFException e) {
            //konec souboru
        }
        dis.close();
    }

    public void calculateBalance() throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(this.filename)));
        double balance = 0;
        try {
            while (true) {
                dis.readUTF();  // přeskočení datumu
                balance += dis.readDouble();
            }
        } catch (EOFException e) {
            //konec souboru
        }
        dis.close();
        System.out.println("Bilance: " + balance);
    }

    public void calculateBalanceForPeriod(Scanner sc) throws IOException {
        System.out.print("Zadejte začátek období (dd-MM-yyyy): ");
        String startDateStr = sc.next();
        System.out.print("Zadejte konec období (dd-MM-yyyy): ");
        String endDateStr = sc.next();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDate = LocalDate.parse(startDateStr, dtf);
        LocalDate endDate = LocalDate.parse(endDateStr, dtf);

        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(this.filename)));
        double balance = 0;
        try {
            while (true) {
                String dateStr = dis.readUTF();
                double amount = dis.readDouble();
                LocalDate transactionDate = LocalDate.parse(dateStr, dtf);

                if ((transactionDate.isEqual(startDate) || transactionDate.isAfter(startDate))
                        && (transactionDate.isEqual(endDate) || transactionDate.isBefore(endDate))) {
                    balance += amount;
                }
            }
        } catch (EOFException e) {
            //konec souboru
        }
        dis.close();
        System.out.println("Bilance pro zadané období: " + balance);
    }
}
