/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectToPeers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tecomp
 */



public class ConnectAllPeers {

    List<String> ipList = new ArrayList<String>();
    final static int ServerPort = 8000;

    public ConnectAllPeers() {

        ipList.add("10.10.13.102");
        ipList.add("10.10.13.55");
        ipList.add("10.10.12.163");

    }

    public void connectToServer() {
        
        String ip;
        for(int i=0;i<ipList.size();i++)
        {
            ip=ipList.get(i);
            
        }
    }

}
