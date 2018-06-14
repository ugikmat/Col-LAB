package id.poros.filkom.col_lab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaContent {

    public static final List<AgendaItem> ITEMS = new ArrayList<AgendaItem>();

    public static final Map<String, AgendaItem> ITEM_MAP = new HashMap<String, AgendaItem>();

    public static class AgendaItem {
        public final String id;
        public final String name;
        public final String details;

        public AgendaItem(String id, String name, String details) {
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
            addItem(createAgendaItem(i));
        }
    }

    private static void addItem(AgendaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static AgendaItem createAgendaItem(int position) {
        return new AgendaItem(String.valueOf(position), "Agenda " + position, makeDetails(position));
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
