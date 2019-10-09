/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SendingReceiving;

import ConnectToPeers.ServerConnection;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import ConnectToPeers.ConnectAllPeers;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author purvesh
 */
public class SendData {

    List<ServerConnection> serverConnectionList = new ArrayList<ServerConnection>();
    String filename;

    public SendData(String filename, List<ServerConnection> serverConnectionList) {
        this.filename = filename;
        this.serverConnectionList = serverConnectionList;

    }

    public void sendData() {
        ServerConnection s;
        int startPoint, endPoint;
        startPoint = 0;
        endPoint = 5;
        for (int i = 0; i < serverConnectionList.size(); i++) {
            s = serverConnectionList.get(i);
            Thread t = new ThreadedSending(startPoint, endPoint, s.getS(), filename, s.getDos());
            t.start();
            startPoint += 5;
            endPoint += 5;
        }
    }

}

class ThreadedSending extends Thread {

    int startCounter, endCounter;
    Socket s;
    String filename;
    OutputStream out;

    public ThreadedSending(int startCounter, int endCounter, Socket s, String filename, OutputStream out) {
        this.startCounter = startCounter;
        this.endCounter = endCounter;
        this.s = s;
        this.filename = filename;
        this.out = out;
    }

    public void run() {
        long startTime = System.currentTimeMillis();
        try {

            BufferedInputStream bis = null;
            FileInputStream file = null;
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(filename);
            dos.writeInt(startCounter);
            dos.writeInt(endCounter);
            for (int i = startCounter; i < endCounter; i++) {
                String fname = filename + "." + i + ".splitPart";
                file = new FileInputStream(fname);
                bis = new BufferedInputStream(file);

                dos.writeInt((int) file.getChannel().size());
                System.out.println("Filesize : " + (int) file.getChannel().size());
                sendBytes(bis, out, (int) file.getChannel().size());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThreadedSending.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ThreadedSending.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Thread Time : " + (System.currentTimeMillis() - startTime) / 1000);

    }

    private static void sendBytes(BufferedInputStream in, OutputStream out, int filesize) throws Exception {

        byte[] data = new byte[filesize];
        int bytes = 0;
        int c = in.read(data, 0, filesize);
        out.write(data, 0, filesize);
        out.flush();
    }
}
