import java.util.*;

public class Restaurant {

    List<MenuItem> menu;
    Queue<Order> kitchenQueue;
    Map<Integer,Order> orders;
    Map<Integer, Order> completedOrders;

    public Restaurant() {
        this.menu = new ArrayList<>();
        this.kitchenQueue = new LinkedList<>();
        this.orders = new HashMap<>();
        this.completedOrders = new LinkedHashMap<>();
    }

    private int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                }
                System.out.println("Invalid input. Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private int readIntBetweenRange(Scanner scanner, String message, int start, int end) {
        while (true) {
            System.out.println(message);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= start && value <= end) {
                    return value;
                }
                System.out.println("Invalid input. Please enter a number in the range [" + start + "->" + end + "]");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double readPositiveDouble(Scanner scanner, String message) {
        while (true) {
            System.out.println(message);
            try {
                double value = Double.parseDouble(scanner.nextLine());
                if (value > 0) {
                    return value;
                }
                System.out.println("Invalid input. Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private String readNonEmptyString(Scanner scanner, String message) {
        while(true) {
            System.out.println(message);
            String value = scanner.nextLine();
            if(!value.trim().isEmpty()) {
                return value;
            }
            System.out.println("The string cannot be empty.");
        }
    }

    public void addMenuItem(Scanner scanner) {
        String itemName = readNonEmptyString(scanner, "Enter the item's name: ");

        double price = readPositiveDouble(scanner, "Enter the price of the item: ");

        String category = readNonEmptyString(scanner, "Enter the category of the item: ");

        MenuItem menuItem = new MenuItem(itemName, price, category);
        this.menu.add(menuItem);
        System.out.println("Item added to menu Successfully");
    }

    public void removeMenuItem(Scanner scanner) {
        if (menu.isEmpty()) {
            System.out.println("No items in the menu to remove.");
        } else {
            int id = readPositiveInt(scanner, "Enter the ID of the item.");

            Iterator<MenuItem> iterator = menu.iterator();
            while (iterator.hasNext()) {
                MenuItem item = iterator.next();

                if (item.id() == id) {
                    iterator.remove();
                    System.out.println("Item removed from menu successfully");
                    return;
                }
            }

            System.out.println("Item not found in the menu.");
        }
    }

    public void displayMenu() {
        if (menu.isEmpty()) {
            System.out.println("No items in the menu yet.");
        } else {
            System.out.println("Menu:");
            menu.forEach(item -> System.out.println("\t" + item));
        }
    }

    public void searchMenuItem(Scanner scanner) {
        int id = readPositiveInt(scanner, "Enter the ID of the item: ");
        for (MenuItem item : menu) {
            if (item.id() == id) {
                System.out.println("Item found!");
                System.out.println(item);
                return;
            }
        }
        System.out.println("Item not found in the menu.");
    }

    public void createOrder(Scanner scanner) {
        String customerName = readNonEmptyString(scanner, "Enter the name of the customer: ");
        Order order = new Order(customerName);
        orders.put(order.getOrderId(), order);
        System.out.println("Order created successfully.\tOrder ID: " + order.getOrderId());
    }

    public void addItemToOrder(Scanner scanner) {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else if (menu.isEmpty()) {
            System.out.println("No items in the menu to add.");
        } else {
            int orderId = readPositiveInt(scanner, "Enter the order ID: ");

            if (orders.containsKey(orderId)) {
                Order order = orders.get(orderId);
                int itemId = readPositiveInt(scanner, "Enter the item ID: ");
                MenuItem menuItem = null;
                for (MenuItem item : menu) {
                    if (item.id() == itemId) {
                        menuItem = item;
                        break;
                    }
                }
                if (menuItem == null) {
                    System.out.println("Item not found in the menu.");
                } else {
                    int quantity = readPositiveInt(scanner, "Enter the quantity: ");
                    order.addItem(new OrderItem(menuItem, quantity));
                }
            } else {
                System.out.println("Order not found.");
            }
        }
    }

    public void removeItemFromOrder(Scanner scanner) {
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
        } else {
            int orderId = readPositiveInt(scanner, "Enter the order ID: ");

            if (orders.containsKey(orderId)) {
                Order order = orders.get(orderId);
                int itemId = readPositiveInt(scanner, "Enter the item ID: ");

                for (OrderItem orderItem : order.getOrderItems()) {
                    if (orderItem.menuItem().id() == itemId) {
                        order.removeItem(orderItem);
                        return;
                    }
                }
                System.out.println("Item not found in order.");
            } else {
                System.out.println("Order not found.");
            }
        }
    }

    public void displayOrder(Scanner scanner) {
        int orderId = readPositiveInt(scanner, "Enter the order ID: ");

        if (orders.containsKey(orderId)) {
            Order order = orders.get(orderId);
            order.displayOrder();
        } else {
            System.out.println("Order not found.");
        }
    }

    public void addOrderToKitchenQueue(Scanner scanner) {
        int orderId = readPositiveInt(scanner, "Enter the order ID: ");

        if (orders.containsKey(orderId)) {
            Order order = orders.get(orderId);
            boolean isSuccessful = order.prepareOrder();
            if (isSuccessful) {
                kitchenQueue.add(order);
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    public void processNextOrder() {
        if (kitchenQueue.isEmpty()) {
            System.out.println("No orders in the kitchen queue to process.");
        } else {
            Order order = kitchenQueue.poll();
            boolean isSuccessful = order.completeOrder();
            if (isSuccessful) {
                completedOrders.put(order.getOrderId(), order);
            }
        }
    }

    public void searchOrder(Scanner scanner) {
        int orderId = readPositiveInt(scanner, "Enter the order ID: ");

        if (orders.containsKey(orderId)) {
            orders.get(orderId).displayOrder();
        } else {
            System.out.println("Order not found.");
        }
    }

    public void checkOrderStatus(Scanner scanner) {
        int orderId = readPositiveInt(scanner, "Enter the order ID: ");

        if (orders.containsKey(orderId)) {
            System.out.println("Order Status: " + orders.get(orderId).getOrderStatus());
        } else {
            System.out.println("Order not found.");
        }
    }

    public void displayCompletedOrders() {
        if (completedOrders.isEmpty()) {
            System.out.println("No orders have been completed yet.");
        } else {
            int index = 1;
            for (Map.Entry<Integer, Order> entry : completedOrders.entrySet()) {
                System.out.println(index++ + ":");
                entry.getValue().displayOrder();
                System.out.println();
            }
        }
    }

    public void cancelOrder(Scanner scanner) {
        int orderId = readPositiveInt(scanner, "Enter the order ID: ");

        if (orders.containsKey(orderId)) {
            orders.get(orderId).cancelOrder();
        } else {
            System.out.println("Order not found.");
        }
    }

    public void calculateTotal(Scanner scanner) {
        int orderId = readPositiveInt(scanner, "Enter the order ID: ");

        if (orders.containsKey(orderId)) {
            System.out.println("Total: " + orders.get(orderId).calculateTotal());
        } else {
            System.out.println("Order not found.");
        }
    }

    public void printAppMenu() {
        System.out.println("========Restaurant Order Manager========");
        System.out.println("| 1. Add Menu Item                     |");
        System.out.println("| 2. Remove Menu Item                  |");
        System.out.println("| 3. Display Menu                      |");
        System.out.println("| 4. Search Menu Item                  |");
        System.out.println("| 5. Create Order                      |");
        System.out.println("| 6. Add Item to Order                 |");
        System.out.println("| 7. Remove Item from Order            |");
        System.out.println("| 8. Display Order                     |");
        System.out.println("| 9. Add Order to Kitchen Queue        |");
        System.out.println("| 10. Process Next Order               |");
        System.out.println("| 11. Search Order                     |");
        System.out.println("| 12. Check Order Status               |");
        System.out.println("| 13. Display Completed Orders         |");
        System.out.println("| 14. Cancel Order                     |");
        System.out.println("| 15. Calculate Total                  |");
        System.out.println("| 16. Exit                             |");
        System.out.println("========================================");
    }

    public void run() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        do {
            printAppMenu();

            choice = readIntBetweenRange(scanner, "Enter your choice of service: ", 1, 16);

            switch (choice) {
                case 1 -> addMenuItem(scanner);
                case 2 -> removeMenuItem(scanner);
                case 3 -> displayMenu();
                case 4 -> searchMenuItem(scanner);
                case 5 -> createOrder(scanner);
                case 6 -> addItemToOrder(scanner);
                case 7 -> removeItemFromOrder(scanner);
                case 8 -> displayOrder(scanner);
                case 9 -> addOrderToKitchenQueue(scanner);
                case 10 -> processNextOrder();
                case 11 -> searchOrder(scanner);
                case 12 -> checkOrderStatus(scanner);
                case 13 -> displayCompletedOrders();
                case 14 -> cancelOrder(scanner);
                case 15 -> calculateTotal(scanner);
                case 16 -> System.out.println("Thank you for using the Restaurant Order Manager.");
            }
        } while (choice != 16);
    }
}
