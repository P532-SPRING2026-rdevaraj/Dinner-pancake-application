import java.util.ArrayList;

// Task 3 – Concrete iterator for the ArrayList-backed PancakeHouseMenu
public class PancakeHouseMenuIterator implements MenuIterator {
    ArrayList<MenuItem> items;
    int position = 0;

    public PancakeHouseMenuIterator(ArrayList<MenuItem> items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return position < items.size();
    }

    @Override
    public MenuItem next() {
        return items.get(position++);
    }
}
