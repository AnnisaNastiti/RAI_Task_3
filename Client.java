/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author annisa
 */
public class Client extends ReadInput{

    static Socket clientSocket = null;
    static PrintStream outStream = null;
    static DataInputStream inStream = null;
    static BufferedReader inLine = null;
    static boolean close = false;

    public static void main(String[] args) {
        try {
            int port = 8000;
            String host = "localhost";
            if (args.length < 2) {
                System.out.println("Terhubung ke port " + port);
            } else {
                host = args[0];
                port = Integer.valueOf(args[1]).intValue();
            }
            clientSocket = new Socket(host, port);
            inLine = new BufferedReader(new InputStreamReader(System.in));
            outStream = new PrintStream(clientSocket.getOutputStream());
            inStream = new DataInputStream(clientSocket.getInputStream());
        } catch (IOException ex) {
            System.out.println("IO Exception - koneksi client");
        }
        if (clientSocket != null && outStream != null && inStream != null) {
            try {
                new Thread(new Client()).start();
                while (!close) {
                    try {
                        outStream.println(inLine.readLine().trim());
                    } catch (IOException ex) {
                        System.out.println("IO Exception - client chat");
                    }
                }
                outStream.close();
                inStream.close();
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("IO Exception - closing");
            }
        }
    }

    /*@Override
    public void run() {
        String respon;
        try {
            while ((respon = inStream.readLine()) != null) {
                System.out.println(respon);
                if (respon.indexOf("___ Bye") != -1) {
                    break;
                }
            }
            close = true;
        } catch (IOException ex) {
            System.out.println("IO Exception - respon");
        }
    }*/
}

