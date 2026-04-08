// Task 1 – Diner uses an Array (no pattern)
public class DinerMenu {
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public DinerMenu() {
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("Vegetarian BLT",
                "(Fakin') Bacon with lettuce & tomato on whole wheat",
                true, 2.99);
        addItem("BLT",
                "Bacon with lettuce & tomato on whole wheat",
                false, 2.99);
        addItem("Soup of the Day",
                "A bowl of the soup of the day, with a side of potato salad",
                false, 3.29);
        addItem("Hot Dog",
                "A hot dog, with saurkraut, topped with cheese",
                false, 3.05);
        addItem("Steamed Veggies and Broccoli",
                "A medley of steamed vegetables with rice",
                true, 3.99);
        addItem("Pasta",
                "Spaghetti with marinara sauce, and a slice of sourdough bread",
                true, 3.89);
    }

    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem item = new MenuItem(name, description, vegetarian, price);
        if (numberOfItems >= MAX_ITEMS) {
            System.err.println("Sorry, menu is full! Can't add item: " + name);
        } else {
            menuItems[numberOfItems] = item;
            numberOfItems++;
        }
    }

    public MenuItem[] getMenuItems() {
        return menuItems;
    }

    // Task 1 – print without pattern
    public void printMenu() {
        System.out.println("\n--- OBJECTVILLE DINER MENU ---");
        for (int i = 0; i < numberOfItems; i++) {
            System.out.println(menuItems[i]);
        }
    }
}
