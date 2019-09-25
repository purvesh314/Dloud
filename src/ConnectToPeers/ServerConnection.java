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

    static String ip = "";
    static int port = 0;
    static Socket s;
    static DataInputStream dis;
    static DataOutputStream dos;

    public static void setIp(String ip) {
        ServerConnection.ip = ip;
    }

    public static void setPort(int port) {
        ServerConnection.port = port;
    }

    public static void setS(Socket s) {
        ServerConnection.s = s;
    }

    public static void setDis(DataInputStream dis) {
        ServerConnection.dis = dis;
    }

    public static void setDos(DataOutputStream dos) {
        ServerConnection.dos = dos;
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }

    public static Socket getS() {
        return s;
    }

    public static DataInputStream getDis() {
        return dis;
    }

    public static DataOutputStream getDos() {
        return dos;
    }

    public ServerConnection(String ip, int port, Socket s) throws IOException {
        ServerConnection.ip = ip;
        ServerConnection.port = port;
        ServerConnection.s = s;
        ServerConnection.dis = new DataInputStream(s.getInputStream());
        ServerConnection.dos = new DataOutputStream(s.getOutputStream());

    }

    public ServerConnection() {

    }

}
