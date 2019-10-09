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
import java.nio.file.*;
import java.nio.channels.*;
import java.util.*;
import java.io.*;

public class Split {

    private static final String dir = "";
    private static final String suffix = ".splitPart";
    private static Integer count = 0;
//    private static String fn="";

    /**
     * Split a file into multiples files.
     *
     * @param fileName Name of file to be split.
     * @param mBperSplit maximum number of MB per file.
     * @throws IOException
     */
    public static List<Path> splitFile(final String fileName, final String fn, final int mBperSplit) throws IOException {

        if (mBperSplit <= 0) {
            throw new IllegalArgumentException("mBperSplit must be more than zero");
        }

        List<Path> partFiles = new ArrayList<>();
        final long sourceSize = Files.size(Paths.get(fileName));
        final long bytesPerSplit = 1024L * 1024L * mBperSplit;
        final long numSplits = sourceSize / bytesPerSplit;
        final long remainingBytes = sourceSize % bytesPerSplit;
        int position = 0;
        System.out.println("Filename : " + fileName);
        try (RandomAccessFile sourceFile = new RandomAccessFile(fileName, "r");
                FileChannel sourceChannel = sourceFile.getChannel()) {

            for (; position < numSplits; position++) {
                //write multipart files.
                writePartToFile(bytesPerSplit, position * bytesPerSplit, sourceChannel, partFiles, fn);
            }

            if (remainingBytes > 0) {
                writePartToFile(remainingBytes, position * bytesPerSplit, sourceChannel, partFiles, fn);
            }
        }
        return partFiles;
    }

    private static void writePartToFile(long byteSize, long position, FileChannel sourceChannel, List<Path> partFiles, String fn) throws IOException {
        System.out.println("Directory : " + dir + " " + suffix);
        Path fileName = Paths.get(dir + fn + "." + count + suffix);
        System.out.println("Filename : " + fileName);
        try (RandomAccessFile toFile = new RandomAccessFile(fileName.toFile(), "rw");
                FileChannel toChannel = toFile.getChannel()) {
            sourceChannel.position(position);
            toChannel.transferFrom(sourceChannel, 0, byteSize);
        }
        partFiles.add(fileName);
        count++;
    }

}

//        Calling
//        try
//        {
//            splitFile("The.Wolf.of.Wall.Street.mp4",200);
//        }
//        catch(IOException e)
//        {
//            System.out.println(e);
//        }
