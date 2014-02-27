package de.dezibel.io;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
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
     * @param db The data of the database to save
     */
    public void save(List[] db) {
        XStream xstream = new XStream(new DomDriver("Cp1252"));
        xstream.alias("List", List[].class);
        String xml = xstream.toXML(db);
        File f = new File("save.xml");
        if (f.exists()) {
            f.delete();
        }
        BufferedWriter bw = null;
        try {
            f.createNewFile();
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(xml);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bw = null;
                }
            }
        }
    }

    /**
     * Loads the data for the database, if there is a save file
     * @return List[] which holds the data for the database or null,
     *          if the file doesnt exist
     */
    public LinkedList[] load() {
        File f = new File("save.xml");
        if (f.exists()) {
            XStream xstream = new XStream(new DomDriver("Cp1252"));
            xstream.alias("List", List[].class);
            return (LinkedList[]) xstream.fromXML(f);
        }
        return null;
    }
}
