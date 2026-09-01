import java.util.ArrayList;
import java.util.List;

public class Order {

    private final int orderId;
    private final String customerName;
    private final List<OrderItem> orderItems;
    private OrderStatus orderStatus;
    private static int nextId = 1;

    public Order(String customerName) {
        this.customerName = customerName;
        this.orderId = nextId;
        nextId++;
        this.orderItems = new ArrayList<>();
        this.orderStatus = OrderStatus.PENDING;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getOrderItems() {
        return List.copyOf(orderItems);
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    private boolean isEditable() {
        return this.orderStatus == OrderStatus.PENDING || this.orderStatus == OrderStatus.IN_KITCHEN;
    }

    public void addItem(OrderItem orderItem) {
        if (isEditable()) {
            this.orderItems.add(orderItem);
            System.out.println("Item added to order: " + orderItem.menuItem().name() + " (" + orderItem.quantity() + "x)");
        } else {
            System.out.println("Cannot add items to completed or cancelled order.");
        }
    }

    public void removeItem(OrderItem orderItem) {
        if (isEditable()) {
            if(orderItems.contains(orderItem)) {
                this.orderItems.remove(orderItem);
                System.out.println("Item removed from order: " + orderItem.menuItem().name() + " (" + orderItem.quantity() + "x)");
            } else {
                System.out.println("Order does not contain this item.");
            }
        } else {
            System.out.println("Cannot remove items from completed or cancelled order.");
        }
    }

    public double calculateTotal() {
        return orderItems.stream().mapToDouble(OrderItem::calculateSubTotal).sum();
    }

    public void displayOrder() {
        System.out.println("Customer: " + this.customerName + "\nOrder ID: " + this.orderId + "\nStatus: " + this.orderStatus + "\nItems:");
        if (orderItems.isEmpty()) {
            System.out.println("\tNo items in order yet.");
        } else {
            for (OrderItem orderItem : this.orderItems) {
                System.out.println("\t" + orderItem);
            }
            orderItems.forEach(item -> System.out.println("\t" + item));
        }
        System.out.println("Total: " + this.calculateTotal());
    }

    public boolean prepareOrder() {
        if (this.orderStatus == OrderStatus.PENDING) {
            this.orderStatus = OrderStatus.IN_KITCHEN;
            System.out.println("Order is being prepared.");
            return true;
        } else {
            System.out.println("Order cannot be prepared as the order is " + this.orderStatus);
            return false;
        }
    }

    public boolean completeOrder() {
        if (this.orderStatus == OrderStatus.IN_KITCHEN) {
            this.orderStatus = OrderStatus.COMPLETED;
            System.out.println("Order is completed.");
            return true;
        } else {
            System.out.println("Order cannot be completed as the order is " + this.orderStatus);
            return false;
        }
    }

    public boolean cancelOrder() {
        if (this.orderStatus == OrderStatus.COMPLETED) {
            System.out.println("Order cannot be cancelled as the order is already completed");
            return false;
        } else {
            this.orderStatus = OrderStatus.CANCELLED;
            System.out.println("Order is cancelled.");
            return true;
        }
    }
}
