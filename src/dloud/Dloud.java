/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dloud;

import ConnectToPeers.ConnectAllPeers;
import ConnectToPeers.ServerConnection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import UserInterface.TCPClient;
/**
 *
 * @author tecomp
 */
public class Dloud {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        List<ServerConnection> serverConnectionList = new ArrayList<ServerConnection>();
        ConnectAllPeers c = new ConnectAllPeers();
        serverConnectionList = c.connectToServer();
        for (int i = 0; i < serverConnectionList.size(); i++) {
            System.out.println(serverConnectionList.get(i).getS());
        }

        TCPClient tcp = new TCPClient("./",serverConnectionList);
            tcp.setSize(1000, 900);
            tcp.setVisible(true);
        
//        if (args.length >= 3) {
//            TCPClient tcp = new TCPClient(args[0], args[1], Integer.parseInt(args[2]));
//            tcp.setSize(1000, 900);
//            tcp.setVisible(true);
//        } else if (args.length == 2) {
//            TCPClient tcp = new TCPClient(args[0], args[1], 3333);
//            tcp.setSize(1000, 900);
//            tcp.setVisible(true);
//        } else if (args.length == 1) {
//            TCPClient tcp = new TCPClient(args[0], "localhost", 3333);
//            tcp.setSize(1000, 900);
//            tcp.setVisible(true);
//        } else {
//            System.out.println("Please enter the client directory address as first argument while running from command line.");
//        }
    }

}
