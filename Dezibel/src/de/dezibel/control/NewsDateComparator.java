package de.dezibel.control;

import java.util.Comparator;


public class NewsDateComparator implements Comparator<de.dezibel.data.News> {

	@Override
	/**
	 * Compares to News depending on their creation date.
	 */
	public int compare(de.dezibel.data.News o1, de.dezibel.data.News o2) {
		return (o1.getCreationDate().compareTo(o2.getCreationDate()));
	}

}
