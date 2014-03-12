package de.dezibel.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Moves files given by their origin path to the given 
 * destination path and renames them to the current timestamp
 * 
 * @author Tobias, Richard
 */
public class FileMover {
    
    /**
     * Moves a File to the given destination and renames the copied file to the
     * current timestamp
     * @param origin The origin path of the file to copy
     * @param destination The destination directory
     * @return The path of the copied file including the new name.
     *          If copying or renaming fails the returned path is empty
     */
    public String moveFile(String origin, String destination) {
        InputStream inStream;
        OutputStream outStream;

        try {
            File originFile = new File(origin);
            File destinationFile = new File(destination + new Long(System.currentTimeMillis()).toString());
            destinationFile.getParentFile().mkdirs();
            
            inStream = new FileInputStream(originFile);
            outStream = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) 
                outStream.write(buffer, 0, length);

            inStream.close();
            outStream.close();
            
            return destinationFile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
