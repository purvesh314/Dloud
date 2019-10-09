/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectToPeers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author tecomp
 */
public class ServerConnection {

    String ip = "";
    int port = 0;
    Socket s;
    OutputStream dos;
    InputStream dis;



    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Socket getS() {
        return s;
    }

    public void setDos(OutputStream dos) {
        this.dos = dos;
    }

    public void setDis(InputStream dis) {
        this.dis = dis;
    }
    
    

    
    public ServerConnection(String ip, int port, Socket s) throws IOException {
        this.ip = ip;
        this.port = port;
        this.s = s;
        this.dis=s.getInputStream();
        this.dos=s.getOutputStream();
    }

    public OutputStream getDos() {
        return dos;
    }

    public InputStream getDis() {
        return dis;
    }

    public ServerConnection() {

    }

}
