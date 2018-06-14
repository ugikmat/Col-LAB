package id.poros.filkom.col_lab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventContent {

    public static final List<EventItem> ITEMS = new ArrayList<EventItem>();

    public static final Map<String, EventItem> ITEM_MAP = new HashMap<String, EventItem>();

    public static class EventItem {
        public final String id;
        public final String name;
        public final String details;

        public EventItem(String id, String name, String details) {
            this.id = id;
            this.name = name;
            this.details = details;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    //TODO: Remove Later
    private static final int COUNT = 3;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createEventItem(i));
        }
    }

    private static void addItem(EventItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static EventItem createEventItem(int position) {
        return new EventItem(String.valueOf(position), "Event " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
}
