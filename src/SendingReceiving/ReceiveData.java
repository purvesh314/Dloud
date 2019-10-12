/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SendingReceiving;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author purvesh
 */
public class ReceiveData {

    public void recvData(String folderName) throws Exception {
        
            int id = 1;
            System.out.println("Server started...");
            System.out.println("Waiting for connections...");

            ServerSocket welcomeSocket;

            // port number is passed by the user
            
                welcomeSocket = new ServerSocket(8000);
            

            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client with ID " + id + " connected from " + connectionSocket.getInetAddress().getHostName() + "...");
                Thread server = new ThreadedServer(connectionSocket, id, folderName);
                id++;
                server.start();
            }
        }
    

}

class ThreadedServer extends Thread {

    int n;
    int m;
    String name, f, ch, fileData;
    String filename;
    Socket connectionSocket;
    int counter;
    String dirName;

    public ThreadedServer(Socket s, int c, String dir) {
        connectionSocket = s;
        counter = c;

        // set dirName to the one that's entered by the user
        dirName = dir;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            InputStream inFromClient = connectionSocket.getInputStream();
            PrintWriter outPw = new PrintWriter(connectionSocket.getOutputStream());
            OutputStream output = connectionSocket.getOutputStream();
            DataInputStream dis = new DataInputStream(inFromClient);
            ObjectOutputStream oout = new ObjectOutputStream(output);
			// oout.writeObject("Server says Hi!");
            //
            // File ff = new File(dirName);
            // ArrayList<String> names = new ArrayList<String>(Arrays.asList(ff.list()));
            // int len = names.size();
            // oout.writeObject(String.valueOf(names.size()));
            //
            // for(String name: names) {
            // 	oout.writeObject(name);
            // }
            //
            // filename = dis.readUTF();
            name = "tttt";
            // name = "test.pdf.0.splitpart";
            ch = name.substring(0, 1);

            if (ch.equals("*")) {
                n = name.lastIndexOf("*");
                filename = name.substring(1, n);
                FileInputStream file = null;
                BufferedInputStream bis = null;
                boolean fileExists = true;
                System.out.println("Request to download file " + filename + " recieved from " + connectionSocket.getInetAddress().getHostName() + "...");
                filename = dirName + filename;
                //System.out.println(filename);
                try {
                    file = new FileInputStream(filename);
                    bis = new BufferedInputStream(file);
                } catch (FileNotFoundException excep) {
                    fileExists = false;
                    System.out.println("FileNotFoundException:" + excep.getMessage());
                }
                if (fileExists) {
                    oout = new ObjectOutputStream(output);
                    oout.writeObject("Success");
                    System.out.println("Download begins");
                    sendBytes(bis, output);
                    System.out.println("Completed");
                    bis.close();
                    file.close();
                    oout.close();
                    output.close();
                } else {
                    oout = new ObjectOutputStream(output);
                    oout.writeObject("FileNotFound");
                    bis.close();
                    file.close();
                    oout.close();
                    output.close();
                }
            } else {
                // String fname = dis.readUTF();
                filename = dis.readUTF();
                int startIndex = dis.readInt();
                int endIndex = dis.readInt();
                System.out.println("Start:" + startIndex + "End:" + endIndex);
                int fsize;
                for (int i = startIndex; i < endIndex; i++) {
                    fsize = dis.readInt();
                    System.out.println("Filesize:" + fsize);
                    try {
                        boolean complete = true;
                        name = filename + "." + i + ".splitPart";
                        System.out.println("Name:" + name);
                        System.out.println("Request to upload file " + name + " recieved from " + connectionSocket.getInetAddress().getHostName() + "...");
                        File directory = new File(dirName);
                        if (!directory.exists()) {
                            System.out.println("Dir made");
                            directory.mkdir();
                        }

                        int size = fsize;
                        byte[] data = new byte[size];
                        File fc = new File(directory, name);
                        System.out.println("File:" + fc);
                        FileOutputStream fileOut = new FileOutputStream(fc);
                        DataOutputStream dataOut = new DataOutputStream(fileOut);

                        byte[] buf = new byte[fsize];
                        int fileSize = fsize;
                        while (fileSize > 0 && (n = inFromClient.read(buf, 0, (int) Math.min(buf.length, fileSize))) != -1) {
                            fileOut.write(buf, 0, n);
                            fileSize -= n;
                        }

						// m = inFromClient.read(data, 0, fsize);
                        // if (m == -1) {
                        // 	complete = false;
                        // 	System.out.println("Completed");
                        // } else {
                        // 	dataOut.write(data, 0, m);
                        // 	dataOut.flush();
                        // }
                        fileOut.close();

                        System.out.println("File Done:" + name);
                    } catch (Exception exc) {
                        System.out.println(exc.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void sendBytes(BufferedInputStream in, OutputStream out) throws Exception {
        int size = 9022386;
        byte[] data = new byte[size];
        int bytes = 0;
        int c = in.read(data, 0, data.length);
        out.write(data, 0, c);
        out.flush();
    }
}
