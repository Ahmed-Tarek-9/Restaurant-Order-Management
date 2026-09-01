public record OrderItem (MenuItem menuItem, int quantity) {

    public double calculateSubTotal() {
        return menuItem.price() * quantity;
    }

    @Override
    public String toString() {
        return menuItem.id() + " - " + menuItem.name() + " (" + quantity + "x)\tSubtotal: " + calculateSubTotal();
    }
}
