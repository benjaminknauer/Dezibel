package de.dezibel.io;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Operates as an Adapter for the XStream libary to realize save and load.
 *
 * @author Tobias, Richard
 */
public class XStreamAdapter {

    /**
     * Saves the given data to an .xml file
     *
     * @param db The data of the database to save
     */
    public void save(List[] db) {
        XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
        xstream.alias("List", List[].class);
        File f = new File("save.xml");
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream fos = null;
        PrintWriter writer = null;
        try {
            f.createNewFile();
            fos = new FileOutputStream(f);
            writer = new PrintWriter(new OutputStreamWriter(fos, "ISO-8859-1"), true);
            // Write Header for the save file for proper encoding
            writer.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            xstream.toXML(db, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    fos = null;
                }
            }
        }
    }

    /**
     * Loads the data for the database, if there is a save file
     *
     * @return List[] which holds the data for the database or null, if the file
     * doesnt exist
     */
    public LinkedList[] load() {
        File f = new File("save.xml");
        if (f.exists()) {
            XStream xstream = new XStream(new DomDriver("ISO-8859-1"));
            xstream.alias("List", List[].class);
            return (LinkedList[]) xstream.fromXML(f);
        }
        return null;
    }
}
