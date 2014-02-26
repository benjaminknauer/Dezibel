public class Label implements Lockable {

	private String companyDetails;

	private String name;

	public Label(User manager, String name) {

	}

	public void addArtist(User artist) {

	}

	public void addManager(User manager) {

	}

	public void follow(User fan) {

	}


	/**
	 * @see Lockable#lock()
	 */
	public void lock() {

	}


	/**
	 * @see Lockable#lock(java.lang.String)
	 */
	public void lock(String text) {

	}


	/**
	 * @see Lockable#unlock()
	 */
	public void unlock() {

	}


	/**
	 * @see Lockable#isLocked()
	 */
	public boolean isLocked() {
		return false;
	}


	/**
	 * @see Lockable#getLockText()
	 */
	public String getLockText() {
		return null;
	}

}
