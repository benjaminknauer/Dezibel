package de.dezibel.control;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import de.dezibel.ErrorCode;
import de.dezibel.data.Database;
import de.dezibel.data.Label;
import de.dezibel.data.News;
import de.dezibel.data.User;

/**
 * Controls the news functions.
 * @author Pascal
 */
public class NewsControl {

    private int maxNumberOfNews = 15;

    /**
     * Creates a new News with the given, title and text. Creator will be the
     * author of the news.
     *
     * @param creator Author of the news
     * @param title Title of the news
     * @param text text of the news
     * @return <code>ErrorCode<code>
     */
    public ErrorCode createNews(Object creator, String title, String text) {
        if (creator == null) {
            creator = Database.getInstance().getLoggedInUser();
        } else if (creator instanceof User) {
            Database.getInstance().addNews(title, text, (User) creator);
            return ErrorCode.SUCCESS;
        } else if(creator instanceof Label){
            Database.getInstance().addNews(title, text, (Label) creator);
            return ErrorCode.SUCCESS;
        }

        return ErrorCode.NEWS_CREATION_ERROR;
    }

    /**
     * search in the news from favourised labels and users and returns the
     * news in a <code>LinkedList</code>.
     *
     * @return a list that contains the actual news
     */
    public LinkedList<de.dezibel.data.News> searchForNews() {
        HashSet<de.dezibel.data.News> ret = new HashSet<de.dezibel.data.News>();
        User current = Database.getInstance().getLoggedInUser();
        User user;
        Label label;
        de.dezibel.data.News news;

        ListIterator<User> iterUser = current.getFavorizedUsers().listIterator();
        ListIterator<Label> iterLabel = current.getFavorizedLabels().listIterator();
        ListIterator<de.dezibel.data.News> newsIter;

        while (iterUser.hasNext()) {
            user = iterUser.next();
            newsIter = user.getNews().listIterator();

            while (newsIter.hasNext()) {
                news = newsIter.next();
                ret.add(news);
            }
        }

        while (iterLabel.hasNext()) {
            label = iterLabel.next();
            newsIter = label.getNews().listIterator();

            while (newsIter.hasNext()) {
                news = newsIter.next();
                ret.add(news);
            }
        }
        
        LinkedList<de.dezibel.data.News> retList = new LinkedList<de.dezibel.data.News>();
        Iterator<de.dezibel.data.News> hashIter;
        hashIter = ret.iterator();
        
        while(hashIter.hasNext()){
        	news = hashIter.next();
        	retList.add(news);
        }
        
        Collections.sort(retList, new NewsDateComparator());
//        while (retList.size() > maxNumberOfNews) {
//            retList.removeLast();
//        }

        return retList;
    }
}
