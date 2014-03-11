package de.dezibel.control;

import de.dezibel.data.Label;
import java.util.Comparator;

/**
 * Comparator for the name of labels.
 * 
 * @author Tobias
 */
public class LabelNameComparator implements Comparator<Label> {
    
    /**
     * Compares the two given labels
     * @param label1 Label
     * @param label2 Label
     * @return 0, if the two label names are the same.
     *          postive if label1>label2.
     *          negative if label1<label2.
     */
    @Override
    public int compare(Label label1, Label label2) {
        if(label1 == null && label2 == null)
            return 0;
        else if(label1 == null)
            return -1;
        else if(label2 == null)
            return 1;
        return label1.getName().compareTo(label2.getName());
    }
}