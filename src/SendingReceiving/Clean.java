/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SendingReceiving;

import java.io.*;
import java.util.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Clean{

    String filename;

    public Clean(String filename) {
        this.filename = filename;
    }
    
    

    public void cleanup() throws IOException {
        File folder = new File(".");
        for (File file : folder.listFiles()) {
            if (file.getName().endsWith("splitPart")) {
                file.delete();
            }
        }
    }
}


