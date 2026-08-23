import java.util.ArrayList;

public class Order {

    private int orderId;
    private String customerName;
    private ArrayList<OrderItem> orderItems;
    private double total;
    private OrderStatus orderStatus;
    private static int nextId = 1;

    public Order(String customerName) {
        this.customerName = customerName;
        this.orderId = nextId;
        nextId++;
        this.orderItems = new ArrayList<>();
        this.total = 0.0;
        this.orderStatus = OrderStatus.PENDING;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void addItem(OrderItem orderItem) {
        if (this.orderStatus == OrderStatus.PENDING || this.orderStatus == OrderStatus.IN_KITCHEN) {
            this.orderItems.add(orderItem);
            this.total += orderItem.calculateSubTotal();
            System.out.println("Item added to order: " + orderItem.getMenuItem().getName() + " (" + orderItem.getQuantity() + "x)");
        } else {
            System.out.println("Cannot add items to completed or cancelled order.");
        }
    }

    public void removeItem(OrderItem orderItem) {
        if (this.orderStatus == OrderStatus.PENDING || this.orderStatus == OrderStatus.IN_KITCHEN) {
            if(orderItems.contains(orderItem)) {
                this.orderItems.remove(orderItem);
                this.total -= orderItem.calculateSubTotal();
                System.out.println("Item removed from order: " + orderItem.getMenuItem().getName() + " (" + orderItem.getQuantity() + "x)");
            } else {
                System.out.println("Order does not contain this item.");
            }
        } else {
            System.out.println("Cannot remove items from completed or cancelled order.");
        }
    }

    public double calculateTotal() {
        return this.total;
    }

    public void displayOrder() {
        System.out.println("Customer: " + this.customerName + "\nOrder ID: " + this.orderId + "\nStatus: " + this.orderStatus + "\nItems:");
        if (orderItems.isEmpty()) {
            System.out.println("\tNo items in order yet.");
        } else {
            for (OrderItem orderItem : this.orderItems) {
                System.out.println("\t" + orderItem);
            }
        }
        System.out.println("Total: " + this.total);
    }

    public boolean prepareOrder() {
        boolean isSuccessful = false;
        if (this.orderStatus == OrderStatus.PENDING) {
            this.orderStatus = OrderStatus.IN_KITCHEN;
            System.out.println("Order is being prepared.");
            isSuccessful = true;
        } else {
            System.out.println("Order cannot be prepared as the order is " + this.orderStatus);
        }
        return isSuccessful;
    }

    public boolean completeOrder() {
        boolean isSuccessful = false;
        if (this.orderStatus == OrderStatus.IN_KITCHEN) {
            this.orderStatus = OrderStatus.COMPLETED;
            System.out.println("Order is completed.");
            isSuccessful = true;
        } else {
            System.out.println("Order cannot be completed as the order is " + this.orderStatus);
        }
        return isSuccessful;
    }

    public boolean cancelOrder() {
        boolean isSuccessful = false;
        if (this.orderStatus == OrderStatus.COMPLETED) {
            System.out.println("Order cannot be cancelled as the order is already completed");
            isSuccessful = true;
        } else {
            this.orderStatus = OrderStatus.CANCELLED;
            System.out.println("Order is cancelled.");
        }
        return isSuccessful;
    }
}
