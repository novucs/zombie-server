package net.novucs.zombieserver.level;

import java.util.Objects;

public class ItemType {

    public static final ItemType GOLD = new ItemType("GOLD", "<img src=\\\"../assets/gold.png\\\">");
    public static final ItemType KEY = new ItemType("KEY", "<img src=\\\"../assets/key.png\\\">");
    public static final ItemType CHAINSAW = new ItemType("CHAINSAW", "<img src=\\\"../assets/chainsaw.png\\\">");
    public static final ItemType DAISY = new ItemType("DAISY", "<img src=\\\"../assets/daisy.png\\\">");

    private final String name;
    private final String html;

    public ItemType(String name, String html) {
        this.name = name;
        this.html = html;
    }

    public String getName() {
        return name;
    }

    public String getHtml() {
        return html;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemType itemType = (ItemType) o;
        return Objects.equals(name, itemType.name) &&
                Objects.equals(html, itemType.html);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, html);
    }

    @Override
    public String toString() {
        return "ItemType{" +
                "name='" + name + '\'' +
                ", html='" + html + '\'' +
                '}';
    }
}
