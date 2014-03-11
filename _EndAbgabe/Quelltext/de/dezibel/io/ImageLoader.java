package de.dezibel.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

/**
 * Loads Images and manages their uploads.
 * 
 * @author Tobias, Richard
 */
public class ImageLoader
{
    private static final String destinationPath = "./Uploads/Img/";

    private final FileMover fm;
    
    /**
     * constructor
     */
    public ImageLoader() {
        this.fm = new FileMover();
    }
    
    /**
     * Uploads the image given by the path to our system structure and 
     * renames it if needed
     * @param path Path of the image to copy
     * @return The new path of the copied image. If the path isnt valid, 
     *          the returned String is empty
     */
    public String upload(String path) {
        File f = new File(path);
        if(f.exists()) 
            return fm.moveFile(path, destinationPath);
        return "";
    }
    
    /**
     * Loads an image from the given path
     * @param path The path of the image to load
     * @return The image or null if the path is invalid
     */
    public Image getImage(String path) {
    	File file = new File(path);
        if(file.exists())
            return Toolkit.getDefaultToolkit().getImage(path);
        return null;
    }
}