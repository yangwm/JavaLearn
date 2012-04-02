/**
 * 
 */
package tedneward.things.collection;

import java.util.Collection;
import java.util.Comparator;

/**
 * Extend the Collections API SortedCollection
 * 
 * @author yangwm Jul 4, 2010 12:21:00 PM
 */
public interface SortedCollection<E> extends Collection<E> {
    public Comparator<E> getComparator();
    public void setComparator(Comparator<E> comp);
}

