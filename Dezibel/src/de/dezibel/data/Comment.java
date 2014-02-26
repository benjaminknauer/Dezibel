import java.util.Date;
import java.util.ArrayList;

public class Comment implements Commentable {

	private String text;

	private Date creationDate;

	private Commentable commentable;

	public Comment(String text, Commentable commentable, User author) {

	}


	/**
	 * @see Commentable#comment(Comment)
	 */
	public void comment(Comment comment) {

	}


	/**
	 * @see Commentable#getComments()
	 */
	public ArrayList<Comment> getComments() {
		return null;
	}

}
