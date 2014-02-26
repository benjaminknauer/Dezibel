import java.util.ArrayList;

public interface Commentable {

	public abstract void comment(Comment comment);

	public abstract ArrayList<Comment> getComments();

}
