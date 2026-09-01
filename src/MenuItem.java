public record MenuItem (int id, String name, double price, String category) {

    private static int nextId = 1;

    public MenuItem(String name, double price, String category) {
        this(nextId++, name, price, category);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\tName: " + name + "\tPrice: " + price + "\tCategory: " + category;
    }
}
