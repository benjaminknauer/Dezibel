package de.dezibel.io;

import java.io.File;

/**
 * Loads media files and manages their uploads.
 * 
 * @author Tobias, Richard
 */
public class MediumLoader {

    private static final String destinationPath = "./Uploads/Media/";
   
    private final FileMover fm;
    
    /**
     * Constructor
     */
    public MediumLoader() {
        this.fm = new FileMover();
    }
    /**
     * Uploads the file given by the path to our system structure and 
     * renames it if needed
     * @param path Path of the file to copy
     * @return The new path of the copied file. If the path isnt valid, 
     *          the returned String is empty
     */
    public String upload(String path) {
        File f = new File(path);
        if(f.exists())
            return fm.moveFile(path, destinationPath);
        return "";
    }

    /**
     * Return the file for the given path
     * @param path Path of the file to load
     * @return The loaded file
     */
    public File getFile(String path) {
        if(path == null || path.equals(""))
            return null;
        return new File(path);
    }

}
