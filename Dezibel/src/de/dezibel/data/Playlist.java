import java.util.ArrayList;

public class Playlist implements Commentable {

	private String titel;

	private Comment comment;

	public Playlist(Medium medium, String titel) {

	}

	public void move(int currentPos, int newPos) {

	}

	public int size() {
		return 0;
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
