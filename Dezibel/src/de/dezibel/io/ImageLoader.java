package de.dezibel.io;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.HashMap;

/**
 * Wird aufgerufen um ein Bild aus einem Verzeichnis zu laden
 * und es zur�ck zu geben
 * 
 * @author Tobias Wiedau 
 * @version 
 */

public class ImageLoader
{
    /**
     * L�dt ein Bild aus einer Datei
     * @param path Der Pfad des Bildes
     * @return Das Bild
     */
	private static ImageLoader instance;
	private HashMap<String, Image> cache;
	
	private ImageLoader()
	{
		cache = new HashMap<String, Image>();
	}
	
	public static ImageLoader getInstance()
	{
		if(instance != null)
			return instance;
		return instance = new ImageLoader();
	}
	
    public Image getImageFromFile(String path)
    {
    	File file = new File(path);
    	String absolute = file.getAbsolutePath();
    	
    	Image img = cache.get(absolute);
    	if(img != null)
    	{
    		System.out.println("ImageLoader: returned cached image '"+absolute+"'");
    		return img;
    	}
    	
    	img = Toolkit.getDefaultToolkit().getImage(absolute);
    	if(img != null)
    	{
    		cache.put(absolute, img);
    		System.out.println("ImageLoader: loaded image '"+absolute+"'");
    	}
        return img;
    }
    public void invalidateCache()
    {
    	cache.clear();
    }
}