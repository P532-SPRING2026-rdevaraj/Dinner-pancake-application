// Task 2 & 3 – Waitress combines all three menus
// Task 2: printMenuNoPattern() – loops through each menu's raw data structure
// Task 3: printMenu()          – uses the Iterator pattern uniformly (works for Array, ArrayList, HashMap)
public class Waitress {
    PancakeHouseMenu pancakeHouseMenu;
    DinerMenu        dinerMenu;
    CafeMenu         cafeMenu;

    public Waitress(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu, CafeMenu cafeMenu) {
        this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu        = dinerMenu;
        this.cafeMenu         = cafeMenu;
    }

    // ── Task 2: combined print WITHOUT iterator pattern ──────────────────────
    public void printMenuNoPattern() {
        System.out.println("\n======= COMBINED MENU (Task 2 – no pattern) =======");

        System.out.println("\n--- OBJECTVILLE PANCAKE HOUSE ---");
        java.util.ArrayList<MenuItem> breakfastItems = pancakeHouseMenu.getMenuItems();
        for (int i = 0; i < breakfastItems.size(); i++) {
            System.out.println(breakfastItems.get(i));
        }

        System.out.println("\n--- OBJECTVILLE DINER ---");
        MenuItem[] lunchItems = dinerMenu.getMenuItems();
        for (int i = 0; i < lunchItems.length; i++) {
            if (lunchItems[i] != null) {
                System.out.println(lunchItems[i]);
            }
        }

        System.out.println("\n--- OBJECTVILLE CAFÉ ---");
        for (MenuItem item : cafeMenu.getItems().values()) {
            System.out.println(item);
        }
    }

    // ── Task 3: combined print WITH iterator pattern ─────────────────────────
    // The key benefit: same printMenu(iterator) helper works for ALL three menus
    // regardless of whether the backing store is an Array, ArrayList, or HashMap.
    public void printMenu() {
        System.out.println("\n======= COMBINED MENU (Task 3 – Iterator Pattern) =======");

        MenuIterator pancakeIterator = new PancakeHouseMenuIterator(pancakeHouseMenu.getMenuItems());
        MenuIterator dinerIterator   = new DinerMenuIterator(dinerMenu.getMenuItems());
        MenuIterator cafeIterator    = new CafeMenuIterator(cafeMenu.getItems());

        System.out.println("\n--- OBJECTVILLE PANCAKE HOUSE ---");
        printMenu(pancakeIterator);

        System.out.println("\n--- OBJECTVILLE DINER ---");
        printMenu(dinerIterator);

        System.out.println("\n--- OBJECTVILLE CAFÉ ---");
        printMenu(cafeIterator);
    }

    // Single private helper – works with ANY MenuIterator (Array, ArrayList, or HashMap)
    private void printMenu(MenuIterator iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    // Print only vegetarian items using the iterator pattern
    public void printVegetarianMenu() {
        System.out.println("\n======= VEGETARIAN MENU (Iterator Pattern) =======");

        MenuIterator pancakeIterator = new PancakeHouseMenuIterator(pancakeHouseMenu.getMenuItems());
        MenuIterator dinerIterator   = new DinerMenuIterator(dinerMenu.getMenuItems());
        MenuIterator cafeIterator    = new CafeMenuIterator(cafeMenu.getItems());

        System.out.println("\n--- PANCAKE HOUSE (vegetarian) ---");
        printVegetarianItems(pancakeIterator);

        System.out.println("\n--- DINER (vegetarian) ---");
        printVegetarianItems(dinerIterator);

        System.out.println("\n--- CAFÉ (vegetarian) ---");
        printVegetarianItems(cafeIterator);
    }

    private void printVegetarianItems(MenuIterator iterator) {
        while (iterator.hasNext()) {
            MenuItem item = iterator.next();
            if (item.isVegetarian()) {
                System.out.println(item);
            }
        }
    }

    // ── Alternating Diner menu (today's schedule) ────────────────────────────
    public void printAlternatingDinerMenu() {
        System.out.println("\n======= ALTERNATING DINER MENU =======");
        System.out.println("Showing: " + AlternatingDinerMenuIterator.currentScheduleLabel());

        MenuIterator iterator = new AlternatingDinerMenuIterator(dinerMenu.getMenuItems());
        printMenu(iterator);
    }

    // Overload: force even (Mon/Wed/Fri/Sun) or odd (Tue/Thu/Sat) for display
    public void printAlternatingDinerMenu(boolean evenDays) {
        String label = evenDays ? "Mon / Wed / Fri / Sun" : "Tue / Thu / Sat";
        System.out.println("\n--- DINER: " + label + " ---");
        MenuIterator iterator = new AlternatingDinerMenuIterator(dinerMenu.getMenuItems(), evenDays);
        printMenu(iterator);
    }
}
