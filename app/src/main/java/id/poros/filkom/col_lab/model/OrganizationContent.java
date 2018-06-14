package id.poros.filkom.col_lab.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrganizationContent {

    public static final List<OrganizationItem> ITEMS = new ArrayList<OrganizationItem>();

    public static final Map<String, OrganizationItem> ITEM_MAP = new HashMap<String, OrganizationItem>();

    public static class OrganizationItem {
        public final String id;
        public final String name;
        public final String details;

        public OrganizationItem(String id, String name, String details) {
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
            addItem(createOrganizationItem(i));
        }
    }

    private static void addItem(OrganizationItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static OrganizationItem createOrganizationItem(int position) {
        return new OrganizationItem(String.valueOf(position), "Organization " + position, makeDetails(position));
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
