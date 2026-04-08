import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MenuGUI extends JFrame {

    private PancakeHouseMenu pancakeHouseMenu;
    private DinerMenu        dinerMenu;
    private CafeMenu         cafeMenu;
    private Waitress         waitress;

    // ── colour palette ───────────────────────────────────────────────────────
    private static final Color BG           = new Color(245, 240, 230);
    private static final Color HEADER_BG    = new Color(60,  40,  20);
    private static final Color HEADER_FG    = new Color(255, 230, 160);
    private static final Color PANCAKE_BG   = new Color(255, 248, 220);
    private static final Color DINER_BG     = new Color(220, 235, 255);
    private static final Color CAFE_BG      = new Color(220, 255, 225);   // green tint for café
    private static final Color PANEL_BORDER = new Color(139, 90,  43);
    private static final Color VEG_COLOR    = new Color(40, 140,  40);
    private static final Color PRICE_COLOR  = new Color(160,  40,  40);
    private static final Color BTN_BG       = new Color(80,  50,  20);
    private static final Color BTN_FG       = Color.WHITE;

    public MenuGUI() {
        pancakeHouseMenu = new PancakeHouseMenu();
        dinerMenu        = new DinerMenu();
        cafeMenu         = new CafeMenu();
        waitress         = new Waitress(pancakeHouseMenu, dinerMenu, cafeMenu);

        setTitle("Objectville Restaurant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG);

        add(buildHeader(),   BorderLayout.NORTH);
        add(buildMenuArea(), BorderLayout.CENTER);
        add(buildButtonBar(), BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(1100, 600));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── header ───────────────────────────────────────────────────────────────
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

    // ── three side-by-side menu panels ───────────────────────────────────────
    private JPanel buildMenuArea() {
        JPanel area = new JPanel(new GridLayout(1, 3, 12, 0));
        area.setBackground(BG);
        area.setBorder(BorderFactory.createEmptyBorder(10, 14, 6, 14));

        area.add(buildSingleMenuPanel("Objectville Pancake House",
                new PancakeHouseMenuIterator(pancakeHouseMenu.getMenuItems()),
                PANCAKE_BG));

        area.add(buildSingleMenuPanel("Objectville Diner",
                new DinerMenuIterator(dinerMenu.getMenuItems()),
                DINER_BG));

        area.add(buildSingleMenuPanel("Objectville Café",
                new CafeMenuIterator(cafeMenu.getItems()),
                CAFE_BG));

        return area;
    }

    private JPanel buildSingleMenuPanel(String title, MenuIterator iterator, Color bg) {
        JPanel outer = new JPanel(new BorderLayout(0, 6));
        outer.setBackground(bg);
        outer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PANEL_BORDER, 2, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)));

        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 18));
        lbl.setForeground(HEADER_BG);
        lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, PANEL_BORDER));
        outer.add(lbl, BorderLayout.NORTH);

        JPanel items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.Y_AXIS));
        items.setBackground(bg);

        while (iterator.hasNext()) {
            items.add(buildItemRow(iterator.next(), bg));
            items.add(Box.createVerticalStrut(4));
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

    private JPanel buildItemRow(MenuItem item, Color bg) {
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

    // ── button bar ───────────────────────────────────────────────────────────
    private JPanel buildButtonBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 8));
        bar.setBackground(HEADER_BG);

        bar.add(makeButton("Task 1 – Separate menus", e -> {
            pancakeHouseMenu.printMenu();
            dinerMenu.printMenu();
            System.out.println("\n--- OBJECTVILLE CAFÉ MENU ---");
            for (MenuItem item : cafeMenu.getItems().values()) {
                System.out.println(item);
            }
        }));

        bar.add(makeButton("Task 2 – Combined (no pattern)", e ->
                waitress.printMenuNoPattern()));

        bar.add(makeButton("Task 3 – Iterator Pattern", e ->
                waitress.printMenu()));

        bar.add(makeButton("Vegetarian Only", e ->
                waitress.printVegetarianMenu()));

        bar.add(makeButton("Alternating Diner Menu", e -> {
            // print both schedules so the user can see both sets clearly
            waitress.printAlternatingDinerMenu(true);   // Mon/Wed/Fri/Sun
            waitress.printAlternatingDinerMenu(false);  // Tue/Thu/Sat
            System.out.println("\n>> Today's schedule: "
                    + AlternatingDinerMenuIterator.currentScheduleLabel());
        }));

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

    // ── main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        PancakeHouseMenu pancakeMenu = new PancakeHouseMenu();
        DinerMenu        dinerMenu   = new DinerMenu();
        CafeMenu         cafeMenu    = new CafeMenu();

        // ── Task 1: print each menu separately ──────────────────────────────
        System.out.println("========== TASK 1: Individual Menus (no pattern) ==========");
        pancakeMenu.printMenu();
        dinerMenu.printMenu();
        System.out.println("\n--- OBJECTVILLE CAFÉ MENU ---");
        for (MenuItem item : cafeMenu.getItems().values()) System.out.println(item);

        // ── Task 2: combined without iterator ───────────────────────────────
        Waitress waitress = new Waitress(pancakeMenu, dinerMenu, cafeMenu);
        waitress.printMenuNoPattern();

        // ── Task 3: combined with iterator pattern ───────────────────────────
        waitress.printMenu();
        waitress.printVegetarianMenu();

        // ── Launch GUI ───────────────────────────────────────────────────────
        SwingUtilities.invokeLater(MenuGUI::new);
    }
}
