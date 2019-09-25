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
        List<ServerConnection> serverConnectionList=new ArrayList<ServerConnection>();
        ConnectAllPeers c=new ConnectAllPeers();
        serverConnectionList=c.connectToServer();
//        for(int i=0;i<serverConnectionList.size();i++)
//        {
//            System.out.println(serverConnectionList.get(i).getS());
//        }
    }
    
}
