/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectToPeers;


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

    List<String> ipList = new ArrayList<>();
    final static int ServerPort = 8000;
    List<ServerConnection> serverConnectionList=new ArrayList<>();

    public List<String> getIpList() {
        return ipList;
    }

    public static int getServerPort() {
        return ServerPort;
    }

    public List<ServerConnection> getServerConnectionList() {
        return serverConnectionList;
    }
    
    

    
    
    public ConnectAllPeers() {

        ipList.add("10.10.11.111");
        ipList.add("10.10.12.46");
        //ipList.add("10.10.12.163");

    }

    public List<ServerConnection> connectToServer() throws IOException {
        
        String ip;
        
        for (String ipList1 : ipList) {
            ip = ipList1;
            Socket s = new Socket(ip, ServerPort);
            //System.out.println(s);
            ServerConnection serv_conn=new ServerConnection(ip, ServerPort, s);
            //System.out.println(serv_conn);
            
            serverConnectionList.add(serv_conn);
        }
        serverConnectionList.stream().forEach((serverConnectionList1) -> {
            System.out.println(serverConnectionList1.getS());
        });
        return serverConnectionList;
    }

}
