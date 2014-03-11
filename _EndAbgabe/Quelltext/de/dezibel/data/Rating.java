package de.dezibel.data;

/**
 * This class represents a rating of a mediaobject made by a user.
 *
 * @author Alexander Trahe, Benjamin Knauer
 */
public class Rating {

    private int points;

    /**
     * Constructor of the class Rating.
     *
     * @param points Ratingvalue: Has to be a value between 1 and 5.
     */
    public Rating(int points) {
        setPoints(points);
    }

    public int getPoints() {
        return points;
    }

    /**
     * Method to set the value of points.
     *
     * @param points Ratingvalue: Has to be a value between 1 and 5.
     * @pre points has value between 1 and 5
     * @post self.points has a value between 1 and 5
     */
    public void setPoints(int points) {
        if (points >= 1 && points <= 5) {
            this.points = points;
        } else {
            throw new IllegalArgumentException("points have to be in range of 1 to 5");
        }
    }
}
