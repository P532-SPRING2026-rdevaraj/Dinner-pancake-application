import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * CompositeIterator — external iterator that traverses the entire
 * MenuComponent tree depth-first using an explicit stack of iterators.
 *
 * When next() yields a Menu node it pushes that menu's own iterator onto
 * the stack so its children are visited on subsequent calls.
 * Leaf MenuItems return a NullIterator from createIterator(), so the stack
 * never tries to descend into them.
 */
public class CompositeIterator implements Iterator<MenuComponent> {

    private final Stack<Iterator<MenuComponent>> stack = new Stack<>();

    public CompositeIterator(Iterator<MenuComponent> iterator) {
        stack.push(iterator);
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty()) {
            return false;
        }
        Iterator<MenuComponent> top = stack.peek();
        if (!top.hasNext()) {
            stack.pop();
            return hasNext();   // recurse to skip exhausted iterators
        }
        return true;
    }

    @Override
    public MenuComponent next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        MenuComponent component = stack.peek().next();
        // Push the component's own iterator so its children are visited next
        stack.push(component.createIterator());
        return component;
    }
}
