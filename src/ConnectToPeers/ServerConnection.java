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

/**
 *
 * @author tecomp
 */
public class ServerConnection {

    String ip = "";
    int port = 0;
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

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

    public DataInputStream getDis() {
        return dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public ServerConnection(String ip, int port, Socket s) throws IOException {
        this.ip = ip;
        this.port = port;
        this.s = s;
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());

    }

    public ServerConnection() {

    }

}
