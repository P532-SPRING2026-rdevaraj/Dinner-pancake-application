import java.util.HashMap;
import java.util.Map;

// Merging with Objectville Café – uses a HashMap
public class CafeMenu {
    HashMap<String, MenuItem> menuItems = new HashMap<>();

    public CafeMenu() {
        addItem("Veggie Burger and Air Fries",
                "Veggie burger on a whole wheat bun, lettuce, tomato, and fries",
                true, 3.99);
        addItem("Soup of the Day",
                "A cup of the soup of the day, with a side salad",
                false, 3.69);
        addItem("Burrito",
                "A large burrito, with whole pinto beans, salsa, guacamole",
                true, 4.29);
    }

    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.put(menuItem.getName(), menuItem);   // key = name, value = MenuItem
    }

    // Returns the map so the iterator can wrap it
    public Map<String, MenuItem> getItems() {
        return menuItems;
    }
}
