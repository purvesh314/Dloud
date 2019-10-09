/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SplitMergeFiles;

/**
 *
 * @author tecomp
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.FileChannel;

public class Merge {

    public static void mergeFile(final String filename, final int numberOfParts) {
        String dir = "./";
        String suffix = ".splitPart";
        String iF = "split_test";

        String oF = "combine_output.mp4";

        try {
            FileOutputStream output = new FileOutputStream(new File(oF));
            WritableByteChannel targetChannel = output.getChannel();
            Integer counter = 0;
            while (true) {
                // if(counter==6)
                // 	break;
                String fname = iF.concat(counter.toString());
                System.out.println(fname);
                fname = fname.concat(suffix);
                System.out.println(fname);
                try ( //Path fname = Paths.get(dir + counter + suffix);
                        FileInputStream input = new FileInputStream(fname);
                        FileChannel inputChannel = input.getChannel()) {
                    //if(!inputChannel)
                    //	break;
                    inputChannel.transferTo(0, inputChannel.size(), targetChannel);
                    counter++;
                }
            }

            // targetChannel.close();
            // output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
