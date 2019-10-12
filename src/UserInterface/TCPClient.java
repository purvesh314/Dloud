/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

/**
 *
 * @author purvesh
 */
import ConnectToPeers.ServerConnection;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import SplitMergeFiles.Split;
import java.nio.file.Path;
import SendingReceiving.SendData;
import java.util.StringTokenizer;
import SendingReceiving.Clean;
import FileData.DatabaseConnection;
import FileData.FileInfo;
import FileData.ServerFile;
import java.util.ArrayList;


public class TCPClient extends JFrame implements ActionListener, MouseListener {

    JPanel panel;
    JLabel title, subT, msg, error, servFiles;
    Font font, labelfont;
    JTextField txt;
    JButton up, down;
    String dirName;
    Socket clientSocket;
    InputStream inFromServer;
    OutputStream outToServer;
    BufferedInputStream bis;
    PrintWriter pw;
    String name, file, path;
    String hostAddr;
    int portNumber;
    int c;
    int size = 9022386;
    JList<String> filelist;
    String[] names = new String[10000];
    int len; // number of files on the server retrieved

    String selectedItem;

    //------File Splitting variables--------------------
    private static final String dir = "./";
    private static final String suffix = ".splitPart";
    private static Integer count = 0;
    
    java.util.List<ServerConnection> serverConnectionList;
    //--------------------------------------------------

    public TCPClient(String dir, java.util.List<ServerConnection> serverConnectionList) {
        super("TCP CLIENT");

        // set dirName to the one that's entered by the user
        dirName = dir;
        this.serverConnectionList=serverConnectionList;
//        // set hostAddr to the one that's passed by the user
//        hostAddr = host;
//
//        // set portNumber to the one that's passed by the user
//        portNumber = port;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(null);

        font = new Font("Roboto", Font.BOLD, 60);
        title = new JLabel("TCP CLIENT");
        title.setFont(font);
        title.setBounds(300, 50, 400, 50);
        panel.add(title);

        labelfont = new Font("Roboto", Font.PLAIN, 20);
        subT = new JLabel("Enter File Name :");
        subT.setFont(labelfont);
        subT.setBounds(100, 450, 200, 50);
        panel.add(subT);

        txt = new JTextField();
        txt.setBounds(400, 450, 500, 50);
        panel.add(txt);

        up = new JButton("Upload");
        up.setBounds(250, 550, 200, 50);
        panel.add(up);

        down = new JButton("Download");
        down.setBounds(550, 550, 200, 50);
        panel.add(down);

        error = new JLabel("");
        error.setFont(labelfont);
        error.setBounds(200, 650, 600, 50);
        panel.add(error);

        up.addActionListener(this);
        down.addActionListener(this);

        try {
            clientSocket = new Socket("192.168.43.212", 8000);
            inFromServer = clientSocket.getInputStream();
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            outToServer = clientSocket.getOutputStream();
            ObjectInputStream oin = new ObjectInputStream(inFromServer);
//            String s = (String) oin.readObject();
//            System.out.println(s);
//            java.util.List<FileInfo> filelist=new ArrayList<>();
            

            len = Integer.parseInt((String) oin.readObject());
            System.out.println(len);

            String[] temp_names = new String[len];

            for (int i = 0; i < len; i++) {
//                ServerFile filename = (ServerFile) oin.readObject();
                String filename = (String) oin.readObject();
                System.out.println("In for");
                System.out.println(filename);
                
                names[i] = filename;
                temp_names[i] = filename;
            }

            // sort the array of strings that's going to get displayed in the scrollpane
            Arrays.sort(temp_names);

            servFiles = new JLabel("Files in the Server Directory :");
            servFiles.setBounds(350, 125, 400, 50);
            panel.add(servFiles);

            filelist = new JList<>(temp_names);
            JScrollPane scroll = new JScrollPane(filelist);
            scroll.setBounds(300, 200, 400, 200);

            panel.add(scroll);
            filelist.addMouseListener(this);

        } catch (Exception exc) {
            System.out.println("Exception: " + exc.getMessage());
            error.setText("Exception:" + exc.getMessage());
            error.setBounds(300, 125, 600, 50);
            panel.revalidate();
        }

        getContentPane().add(panel);
    }

    @Override
    public void mouseClicked(MouseEvent click) {
        if (click.getClickCount() == 2) {
            selectedItem = (String) filelist.getSelectedValue();
            txt.setText(selectedItem);
            panel.revalidate();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == up) {
            try {
                name = txt.getText();

                FileInputStream file = null;
                BufferedInputStream bis = null;

                boolean fileExists = true;
                path = dirName + name;

                try {
                    file = new FileInputStream(path);
                    bis = new BufferedInputStream(file);
                } catch (FileNotFoundException excep) {
                    fileExists = false;
                    System.out.println("FileNotFoundException:" + excep.getMessage());
                    error.setText("FileNotFoundException:" + excep.getMessage());
                    panel.revalidate();
                }

                //Calling File Splitting
                try {
                    System.out.println("Here"+dirName + "Path : "+path);
                    Split sp=new Split();
                    System.out.println("After");
                    java.util.List<Path> splitFile12 = sp.splitFile(path,name, 50);
                    SendData sd=new SendData(name, serverConnectionList);
                    sd.sendData();
                    Clean c1=new Clean(name);
                    c1.cleanup();
                    DatabaseConnection dc=new DatabaseConnection();
                    dc.uploadData(name);
                    
                    
                } catch (IOException e) {
                    System.out.println(e);
                }

                if (fileExists) {
                    // send file name to server
                    pw.println(name);

                    System.out.println("Upload begins");
                    error.setText("Upload begins");
                    panel.revalidate();

                    // send file data to server
                    //sendBytes(bis, outToServer);
                    System.out.println("Completed");
                    error.setText("Completed");
                    panel.revalidate();

                    boolean exists = false;
                    for (int i = 0; i < len; i++) {
                        if (names[i].equals(name)) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        names[len] = name;
                        len++;
                    }

                    String[] temp_names = new String[len];
                    for (int i = 0; i < len; i++) {
                        temp_names[i] = names[i];
                    }

                    // sort the array of strings that's going to get displayed in the scrollpane
                    Arrays.sort(temp_names);

                    // update the contents of the list in scroll pane
                    filelist.setListData(temp_names);

                    // close all file buffers
                    bis.close();
                    file.close();
                    outToServer.close();
                }
            } catch (Exception exc) {
                System.out.println("Exception: " + exc.getMessage());
                error.setText("Exception:" + exc.getMessage());
                panel.revalidate();
            }
        } else if (event.getSource() == down) {
            try {
                File directory = new File(dirName);

                if (!directory.exists()) {
                    directory.mkdir();
                }
                boolean complete = true;
                byte[] data = new byte[size];
                name = txt.getText();
                file = new String("*" + name + "*");
                pw.println(file); //lets the server know which file is to be downloaded

                ObjectInputStream oin = new ObjectInputStream(inFromServer);
                String s = (String) oin.readObject();

                if (s.equals("Success")) {
                    File f = new File(directory, name);
                    FileOutputStream fileOut = new FileOutputStream(f);
                    DataOutputStream dataOut = new DataOutputStream(fileOut);

                    //empty file case
                    while (complete) {
                        c = inFromServer.read(data, 0, data.length);
                        if (c == -1) {
                            complete = false;
                            System.out.println("Completed");
                            error.setText("Completed");
                            panel.revalidate();

                        } else {
                            dataOut.write(data, 0, c);
                            dataOut.flush();
                        }
                    }
                    fileOut.close();
                } else {
                    System.out.println("Requested file not found on the server.");
                    error.setText("Requested file not found on the server.");
                    panel.revalidate();
                }
            } catch (IOException | ClassNotFoundException exc) {
                System.out.println("Exception: " + exc.getMessage());
                error.setText("Exception:" + exc.getMessage());
                panel.revalidate();
            }
        }
    }

    

	//-----------File Splitting Begins----------------------
	//-----------File Splitting Ends------------------------
}
