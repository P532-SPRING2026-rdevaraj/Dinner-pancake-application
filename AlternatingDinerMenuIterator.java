import java.time.DayOfWeek;
import java.time.LocalDate;


public class AlternatingDinerMenuIterator implements MenuIterator {
    MenuItem[] items;
    int        position;
    int        step = 2;       

    public AlternatingDinerMenuIterator(MenuItem[] items) {
        this.items = items;
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        boolean evenDay = (today == DayOfWeek.MONDAY
                        || today == DayOfWeek.WEDNESDAY
                        || today == DayOfWeek.FRIDAY
                        || today == DayOfWeek.SUNDAY);
        position = evenDay ? 0 : 1;   
    }

    public AlternatingDinerMenuIterator(MenuItem[] items, boolean evenDays) {
        this.items = items;
        position   = evenDays ? 0 : 1;
    }

    @Override
    public boolean hasNext() {
        return position < items.length && items[position] != null;
    }

    @Override
    public MenuItem next() {
        MenuItem item = items[position];
        position += step;
        return item;
    }

    public static String currentScheduleLabel() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        boolean evenDay = (today == DayOfWeek.MONDAY
                        || today == DayOfWeek.WEDNESDAY
                        || today == DayOfWeek.FRIDAY
                        || today == DayOfWeek.SUNDAY);
        return evenDay
                ? "Mon / Wed / Fri / Sun menu (today: " + today + ")"
                : "Tue / Thu / Sat menu (today: " + today + ")";
    }
}
