import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * NullIterator — returned by leaf MenuItems.
 * hasNext() is always false, so the CompositeIterator never tries to descend into a leaf.
 */
public class NullIterator implements Iterator<MenuComponent> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public MenuComponent next() {
        throw new NoSuchElementException();
    }
}
