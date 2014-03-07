package de.dezibel.control;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.User;

/**
 * 
 * @author Pascal
 *
 */
public class News {
	
	private int maxNumberOfNews = 15;
	
	public News() {
		
	}
	/**
	 * search in the news from favourised labels and users and returns the 
	 * 15 newest news.
	 * @return a list that contains the actual news
	 */
	public LinkedList<de.dezibel.data.News> searchForNews(){
		LinkedList<de.dezibel.data.News> ret = new LinkedList<de.dezibel.data.News>();
		User current = Database.getInstance().getLoggedInUser();
		User user;
		Label label;
		de.dezibel.data.News news;

		ListIterator<User> 	iterUser = current.getFavorizedUsers().listIterator();
		ListIterator<Label> iterLabel = current.getFavorizedLabels().listIterator();
		ListIterator<de.dezibel.data.News> newsIter;
		
		while(iterUser.hasNext())
		{
			user = iterUser.next();
			newsIter = user.getNews().listIterator();
			
			while(newsIter.hasNext()){
				news = newsIter.next();
				ret.push(news);
			}
		}
		
		while(iterLabel.hasNext())
		{
			label = iterLabel.next();
			newsIter = label.getNews().listIterator();
			
			while(newsIter.hasNext()){
				news = newsIter.next();
				ret.push(news);
			}
		}
		
		Collections.sort(ret,new NewsDateComparator());
		while(ret.size() > maxNumberOfNews)
			ret.removeLast();
		
		return ret;
	}
}
