/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectToPeers;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import ConnectToPeers.ServerConnection;
/**
 *
 * @author tecomp
 */



public class ConnectAllPeers {

    static List<String> ipList = new ArrayList<String>();
    final static int ServerPort = 8000;

    public ConnectAllPeers() {

        ipList.add("10.10.11.32");
        ipList.add("10.10.10.13");
        //ipList.add("10.10.12.163");

    }

    public static List<ServerConnection> connectToServer() throws IOException {
        
        String ip;
        List<ServerConnection> serverConnectionList=new ArrayList<ServerConnection>();
        for(int i=0;i<ipList.size();i++)
        {
            ip=ipList.get(i);
            Socket s = new Socket(ip, ServerPort);
            System.out.println(s);
            ServerConnection serv_conn=new ServerConnection(ip, ServerPort, s);
            System.out.println(serv_conn);
            serverConnectionList.add(serv_conn);
        }
        for(int i=0;i<serverConnectionList.size();i++)
        {
            System.out.println(serverConnectionList.get(i).getS());
        }
        return serverConnectionList;
    }

}
