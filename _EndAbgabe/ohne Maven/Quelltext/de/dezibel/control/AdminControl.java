package de.dezibel.control;

import de.dezibel.data.Lockable;

/**
 * Controls the admin functionality.
 * @author Richard
 */
public class AdminControl {
    
    /**
     * Locks the submitted entity with an optional lock text.
     * @param l The entity to lock
     * @param lockText The lock text
     */
    public void lock(Lockable l, String lockText) {
        if (lockText == null || lockText.isEmpty()) {
            l.lock();
        } else {
            l.lock(lockText);
        }
    }
    
    /**
     * Unlocks the submitted entity.
     * @param l The entity to unlock
     */
    public void unlock(Lockable l) {
        l.unlock();
    }
    
}
