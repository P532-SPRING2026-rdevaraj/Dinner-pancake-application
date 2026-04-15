import java.util.Iterator;

public class Waitress {

    private final MenuComponent allMenus;

    public Waitress(MenuComponent allMenus) {
        this.allMenus = allMenus;
    }

    public void printMenu() {
        allMenus.print();
    }

    // Existing: delegates to the composite's own printVegetarian() recursion
    public void printVegetarianMenu() {
        allMenus.printVegetarian();
    }

    /**
     * printVegetarianMenu1 — manual recursive traversal.
     * The Waitress walks the composite tree itself: when it meets a Menu node it
     * prints a header and recurses into its children; when it meets a MenuItem it
     * checks isVegetarian() and prints if true.
     * No external Iterator object is used — the traversal logic lives here.
     */
    public void printVegetarianMenu1() {
        System.out.println("\n--- VEGETARIAN MENU (manual traversal) ---");
        printVegetarianMenu1Helper(allMenus);
    }

    private void printVegetarianMenu1Helper(MenuComponent component) {
        if (component instanceof Menu) {
            System.out.println("\n" + component.getName() + ", " + component.getDescription());
            System.out.println("-------------------");
            for (MenuComponent child : ((Menu) component).getChildren()) {
                printVegetarianMenu1Helper(child);
            }
        } else if (component instanceof MenuItem) {
            if (component.isVegetarian()) {
                component.print();
            }
        }
    }

    /**
     * printVegetarianMenu2 — same goal, rewritten with CompositeIterator.
     * createIterator() on the root returns a CompositeIterator that walks the
     * entire tree depth-first via a stack. The Waitress only needs a single
     * while-loop; the traversal complexity is hidden inside the iterator.
     * Menu nodes throw UnsupportedOperationException for isVegetarian(), so we
     * skip them with a try/catch — only leaf MenuItems are tested.
     */
    public void printVegetarianMenu2() {
        System.out.println("\n--- VEGETARIAN MENU (composite iterator) ---");
        Iterator<MenuComponent> iterator = allMenus.createIterator();
        while (iterator.hasNext()) {
            MenuComponent component = iterator.next();
            try {
                if (component.isVegetarian()) {
                    component.print();
                }
            } catch (UnsupportedOperationException ignored) {
                // Menu nodes don't support isVegetarian(); skip them
            }
        }
    }
}
