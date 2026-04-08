import java.util.Iterator;
import java.util.Map;

// HashMap.values() gives a Collection – its built-in Iterator wraps it cleanly
public class CafeMenuIterator implements MenuIterator {
    Iterator<MenuItem> iterator;

    public CafeMenuIterator(Map<String, MenuItem> items) {
        iterator = items.values().iterator();   // HashMap supports Iterator via values()
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public MenuItem next() {
        return iterator.next();
    }
}
