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
        int startPoint,endPoint;
        startPoint=0;
        endPoint=3;
        for (int i = 0; i < serverConnectionList.size(); i++) {
            s=serverConnectionList.get(i);
            Thread t=new ThreadedSending(startPoint, endPoint, s.getS(), filename, s.getDos());
            t.start();
            startPoint+=2;
            endPoint+=2;
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
        try {

            BufferedInputStream bis=null;
            FileInputStream file=null; 
            for (int i = startCounter; i < endCounter; i++) {
                String fname = filename + "." + i + ".splitPart";
                file = new FileInputStream(fname);
                bis = new BufferedInputStream(file);
                sendBytes(bis, out);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ThreadedSending.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ThreadedSending.class.getName()).log(Level.SEVERE, null, ex);
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
