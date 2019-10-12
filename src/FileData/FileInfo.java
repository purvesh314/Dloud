/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileData;

import java.io.Serializable;

/**
 *
 * @author purvesh
 */
public class FileInfo implements Serializable{
    
    String filename;
    String owner;

    public FileInfo(String filename, String owner) {
        this.filename = filename;
        this.owner = owner;
    }

    public String getFilename() {
        return filename;
    }

    public String getOwner() {
        return owner;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    
    
}
