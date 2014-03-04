package de.dezibel.data;

/**
 * Interface which stores functionality to lock and unlock contents which
 * implement it.
 *
 * @author
 */
public interface Lockable {

    /**
     * Locks the content, so no user unless the admin can access it.
     */
    public abstract void lock();

    /**
     * Locks the content, so no user unless the admin can access it and shows a
     * locktext to inform about the lock.
     *
     * @param text text which informs about the lock
     */
    public abstract void lock(String text);

    /**
     * Unlocks the content, so every user associated with it can access it again
     * as he used to do before.
     */
    public abstract void unlock();

    /**
     * Checks if the content is locked.
     *
     * @return <code>true</code> if its locked, <code>false</code> otherwise
     */
    public abstract boolean isLocked();

    public abstract String getLockText();

}
