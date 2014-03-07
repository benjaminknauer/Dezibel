package de.dezibel.control;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import de.dezibel.ErrorCode;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.News;
import de.dezibel.data.User;

/**
 * 
 * @author Pascal
 *
 */
public class NewsControl {
	
	private int maxNumberOfNews = 15;
	
	public NewsControl() {
	
	}
	
	/**
	 * Creates a new News with the given, title and text.
	 * Creator will be the author of the news.
	 * 
	 * @param creator Author of the news
	 * @param title Title of the news
	 * @param text text of the news
	 * @return <code>ErrorCode<code>
	 */
	public ErrorCode createNews(User creator,String title,String text){
		if(creator == null)
			creator = Database.getInstance().getLoggedInUser();
		
		News ne = new News(title, text, creator);
		creator.addNews(ne);
		return ErrorCode.SUCCESS;
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
