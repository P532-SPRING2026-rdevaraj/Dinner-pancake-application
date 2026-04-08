import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuGUI extends JFrame {

    private final MenuComponent allMenus;

    private static final Color BG           = new Color(245, 240, 230);
    private static final Color HEADER_BG    = new Color(60,  40,  20);
    private static final Color HEADER_FG    = new Color(255, 230, 160);
    private static final Color PANEL_BORDER = new Color(139, 90,  43);
    private static final Color VEG_COLOR    = new Color(40, 140,  40);
    private static final Color PRICE_COLOR  = new Color(160,  40,  40);
    private static final Color BTN_BG       = new Color(80,  50,  20);
    private static final Color BTN_FG       = Color.WHITE;
    private static final Color[] PANEL_COLORS = {
        new Color(255, 248, 220),
        new Color(220, 235, 255),
        new Color(220, 255, 225)
    };

    public MenuGUI(MenuComponent allMenus) {
        this.allMenus = allMenus;

        setTitle("Objectville Restaurant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG);

        add(buildHeader(),    BorderLayout.NORTH);
        add(buildMenuArea(),  BorderLayout.CENTER);
        add(buildButtonBar(), BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(1100, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(HEADER_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        JLabel title = new JLabel("Objectville Restaurant Group", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
        title.setForeground(HEADER_FG);

        JLabel sub = new JLabel("Pancake House  ·  Diner  ·  Café", SwingConstants.CENTER);
        sub.setFont(new Font("Serif", Font.ITALIC, 14));
        sub.setForeground(new Color(200, 180, 120));

        panel.add(title, BorderLayout.CENTER);
        panel.add(sub,   BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildMenuArea() {
        ArrayList<MenuComponent> subMenus = ((Menu) allMenus).getChildren();

        JPanel area = new JPanel(new GridLayout(1, subMenus.size(), 12, 0));
        area.setBackground(BG);
        area.setBorder(BorderFactory.createEmptyBorder(10, 14, 6, 14));

        for (int i = 0; i < subMenus.size(); i++) {
            Color bg = PANEL_COLORS[i % PANEL_COLORS.length];
            area.add(buildMenuPanel(subMenus.get(i), bg));
        }

        return area;
    }

    private JPanel buildMenuPanel(MenuComponent menu, Color bg) {
        JPanel outer = new JPanel(new BorderLayout(0, 6));
        outer.setBackground(bg);
        outer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PANEL_BORDER, 2, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        JLabel lbl = new JLabel(menu.getName(), SwingConstants.CENTER);
        lbl.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
        lbl.setForeground(HEADER_BG);
        lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PANEL_BORDER));
        outer.add(lbl, BorderLayout.NORTH);

        JPanel items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
        items.setBackground(bg);

        for (MenuComponent child : ((Menu) menu).getChildren()) {
            if (child instanceof MenuItem) {
                items.add(buildItemRow(child, bg));
                items.add(Box.createVerticalStrut(4));
            }
        }

        JScrollPane scroll = new JScrollPane(items,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(bg);
        scroll.getViewport().setBackground(bg);
        outer.add(scroll, BorderLayout.CENTER);

        JLabel legend = new JLabel("(v) = vegetarian", SwingConstants.RIGHT);
        legend.setFont(new Font("SansSerif", Font.ITALIC, 11));
        legend.setForeground(VEG_COLOR);
        outer.add(legend, BorderLayout.SOUTH);

        return outer;
    }

    private JPanel buildItemRow(MenuComponent item, Color bg) {
        JPanel row = new JPanel(new BorderLayout(6, 0));
        row.setBackground(bg);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        namePanel.setBackground(bg);

        JLabel name = new JLabel(item.getName());
        name.setFont(new Font("SansSerif", Font.BOLD, 13));
        namePanel.add(name);

        if (item.isVegetarian()) {
            JLabel veg = new JLabel(" (v)");
            veg.setFont(new Font("SansSerif", Font.BOLD, 12));
            veg.setForeground(VEG_COLOR);
            namePanel.add(veg);
        }

        JLabel price = new JLabel(String.format("$%.2f", item.getPrice()));
        price.setFont(new Font("SansSerif", Font.BOLD, 13));
        price.setForeground(PRICE_COLOR);

        JLabel desc = new JLabel("<html><i>" + item.getDescription() + "</i></html>");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 11));
        desc.setForeground(new Color(80, 80, 80));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(bg);
        top.add(namePanel, BorderLayout.WEST);
        top.add(price,     BorderLayout.EAST);

        row.add(top,  BorderLayout.NORTH);
        row.add(desc, BorderLayout.CENTER);

        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 200, 185)),
                BorderFactory.createEmptyBorder(4, 2, 4, 2)));

        return row;
    }

    private JPanel buildButtonBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 8));
        bar.setBackground(HEADER_BG);

        bar.add(makeButton("Print All Menus (Composite)", e -> new Waitress(allMenus).printMenu()));

        return bar;
    }

    private JButton makeButton(String label, ActionListener action) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(BTN_BG);
        btn.setForeground(BTN_FG);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(HEADER_FG, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)));
        btn.addActionListener(action);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast");
        MenuComponent dinerMenu        = new Menu("DINER MENU",         "Lunch");
        MenuComponent cafeMenu         = new Menu("CAFE MENU",          "Dinner");
        MenuComponent allMenus         = new Menu("ALL MENUS",          "All menus combined");

        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);

        pancakeHouseMenu.add(new MenuItem("K&B's Pancake Breakfast",
                "Pancakes with scrambled eggs, and toast", true, 2.99));
        pancakeHouseMenu.add(new MenuItem("Regular Pancake Breakfast",
                "Pancakes with fried eggs, sausage", false, 2.99));
        pancakeHouseMenu.add(new MenuItem("Blueberry Pancakes",
                "Pancakes made with fresh blueberries, and blueberry syrup", true, 3.49));
        pancakeHouseMenu.add(new MenuItem("Waffles",
                "Waffles, with your choice of blueberries or strawberries", true, 3.59));

        dinerMenu.add(new MenuItem("Vegetarian BLT",
                "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99));
        dinerMenu.add(new MenuItem("BLT",
                "Bacon with lettuce & tomato on whole wheat", false, 2.99));
        dinerMenu.add(new MenuItem("Soup of the Day",
                "A bowl of the soup of the day, with a side of potato salad", false, 3.29));
        dinerMenu.add(new MenuItem("Hot Dog",
                "A hot dog, with saurkraut, topped with cheese", false, 3.05));
        dinerMenu.add(new MenuItem("Steamed Veggies and Broccoli",
                "A medley of steamed vegetables with rice", true, 3.99));
        dinerMenu.add(new MenuItem("Pasta",
                "Spaghetti with marinara sauce, and a slice of sourdough bread", true, 3.89));

        cafeMenu.add(new MenuItem("Veggie Burger and Air Fries",
                "Veggie burger on a whole wheat bun, lettuce, tomato, and fries", true, 3.99));
        cafeMenu.add(new MenuItem("Soup of the Day",
                "A cup of the soup of the day, with a side salad", false, 3.69));
        cafeMenu.add(new MenuItem("Burrito",
                "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29));

        new Waitress(allMenus).printMenu();

        SwingUtilities.invokeLater(() -> new MenuGUI(allMenus));
    }
}
