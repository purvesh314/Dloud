/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SendReceive;

import ConnectToPeers.ConnectAllPeers;
import ConnectToPeers.ServerConnection;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import UserInterface.TCPClient;

/**
 *
 * @author tecomp
 */
public class SendFiles {

    List<ServerConnection> serverConnectionList = new ArrayList<>();
    String filename;
    int numberOfParts;

    public SendFiles(String filename, int numberOfParts) {
        this.filename = filename;
        this.numberOfParts = numberOfParts;

        ConnectAllPeers cap = new ConnectAllPeers();
        serverConnectionList = cap.getServerConnectionList();
    }

    public List<ServerConnection> getServerConnectionList() {
        return serverConnectionList;
    }

    public String getFilename() {
        return filename;
    }

    public int getNumberOfParts() {
        return numberOfParts;
    }

    void sendFiles() {

        int numberOfConnections = 2;
        TCPClient t = new TCPClient();
        String dirName = t.getDirName();
        int startIndex=0,endIndex=5;
        for (int i = 0; i < numberOfConnections; i++) {
            Socket s = new Socket();
            s = serverConnectionList.get(i).getS();
            ThreadedServer thread = new ThreadedServer(s, startIndex, endIndex, filename);
            thread.start();
            startIndex+=5;
            endIndex+=5;
        }

    }

    class ThreadedServer extends Thread {

        /*
	 * This is the main Class which will be sending files from server to client
	 * This will be the Thread for sending files for each client
         */
        Socket connectionSocket;         // Socket of client for communications
        String filename;                 // Filename for each transfer
        String file;
        String dirName;                  // Directory name from which files are stored on server
        int startIndex;                  // This will be used as start to iterate for loop for sending
        int endIndex;                    // This will be used as end to iterate for loop for sending

        public ThreadedServer(Socket s, int startIndex, int endIndex, String file) {
            connectionSocket = s;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.filename=file;
            dirName = "Splitted-parts/";
        }

        public void run() {
            try {
                long before_used_memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                long start_time = System.currentTimeMillis();

                BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                InputStream inFromClient = connectionSocket.getInputStream();
                PrintWriter outPw = new PrintWriter(connectionSocket.getOutputStream());
                OutputStream output = connectionSocket.getOutputStream();

                DataOutputStream oout = new DataOutputStream(output);
                oout.writeUTF("Server says Hi !!");

                File ff = new File(dirName);

                for (int i = this.startIndex; i <= this.endIndex; i++) {
                    filename = file + i;
                    FileInputStream file = null;
                    BufferedInputStream bis = null;
                    boolean fileExists = true;
                    System.out.println("Request to download file " + filename + " recieved from " + connectionSocket.getInetAddress().getHostName() + "...");
                    filename = dirName + filename;

                    try {
                        file = new FileInputStream(filename);
                        bis = new BufferedInputStream(file);
                    } catch (FileNotFoundException excep) {
                        fileExists = false;
                        System.out.println("FileNotFoundException:" + excep.getMessage());
                    }
                    if (fileExists) {
                        oout = new DataOutputStream(output);
                        oout.writeInt((int) file.getChannel().size());
                        oout.flush();
                        System.out.println("Download begins");
                        sendBytes(bis, output, (int) file.getChannel().size());
                        System.out.println("Completed");
                    } else {
                        oout = new DataOutputStream(output);
                        oout.writeChars("FileNotFound");
                    }
                    file.close();
                    bis.close();
                }

                System.out.println("\nTotal time taken to split: " + ((System.currentTimeMillis() - start_time) / 1000) + "s");

                long after_used_memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                System.out.println("Total memory consumption: " + (after_used_memory - before_used_memory) + "\n");
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
            }

        }

        void sendBytes(BufferedInputStream in, OutputStream out, int filesize) throws Exception {
            int size = filesize;
            byte[] data = new byte[filesize];
            int bytes = 0;
            int c = in.read(data, 0, filesize);
            out.write(data, 0, filesize);
            out.flush();
        }
    }
}
