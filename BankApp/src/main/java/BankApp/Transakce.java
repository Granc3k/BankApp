package BankApp;

import java.io.*;
public class Transakce {
    private String date;
    private double amount;

    public Transakce(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    public void saveTransactionToFile(String filename) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename, true)));
        dos.writeUTF(this.date);
        dos.writeDouble(this.amount);
        dos.close();
    }

}