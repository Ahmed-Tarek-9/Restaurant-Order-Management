import java.util.*;

public class Restaurant {

    ArrayList<MenuItem> menu;
    LinkedList<Order> kitchenQueue;
    HashMap<Integer,Order> orders;
    LinkedHashMap<Integer, Order> completedOrders;

    public Restaurant() {
        this.menu = new ArrayList<>();
        this.kitchenQueue = new LinkedList<>();
        this.orders = new HashMap<>();
        this.completedOrders = new LinkedHashMap<>();
    }

    public void addMenuItem(Scanner scanner) {
        String itemName = "";
        do {
            System.out.println("Enter the item name: ");
            itemName = scanner.nextLine();
        } while (itemName.trim().isEmpty());

        double price = 0.0;
        do {
            if (price < 0.0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Enter the price of the item: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                price = 0.0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (price <= 0.0);

        String category = "";
        do {
            System.out.println("Enter the category of the item: ");
            category = scanner.nextLine();
        } while (category.trim().isEmpty());

        MenuItem menuItem = new MenuItem(itemName, price, category);
        this.menu.add(menuItem);
        System.out.println("Item added to menu Successfully");
    }

    public void removeMenuItem(Scanner scanner) {
        if (menu.isEmpty()) {
            System.out.println("No items in the menu to remove.");
        } else {
            int id = 0;
            do {
                if (id < 0) {
                    System.out.println("Invalid input. Please enter a positive number.");
                }
                System.out.println("Enter the item id: ");
                try {
                    id = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    id = 0;
                    System.out.println("Invalid input. Please enter a number.");
                }
            } while (id <= 0);

            for (MenuItem item : menu) {
                if (item.getId() == id) {
                    menu.remove(item);
                    System.out.println("Item removed from menu Successfully");
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
            for (MenuItem menuItem : menu) {
                System.out.println("\t" + menuItem);
            }
        }
    }

    public void searchMenuItem(Scanner scanner) {
        int id = 0;
        do {
            if (id < 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Enter the item id: ");
            try {
                id = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                id = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (id <= 0);
        for (MenuItem item : menu) {
            if (item.getId() == id) {
                System.out.println("Item found!");
                System.out.println(item);
                return;
            }
        }
        System.out.println("Item not found in the menu.");
    }

    public void createOrder(Scanner scanner) {
        String customerName = "";
        do {
            System.out.println("Enter the customer name: ");
            customerName = scanner.nextLine();
        } while (customerName.trim().isEmpty());

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
            int orderId = 1;
            do {
                if (orderId <= 0) {
                    System.out.println("Invalid input. Please enter a positive number.");
                }
                System.out.println("Enter the order ID: ");
                try {
                    orderId = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    orderId = 0;
                    System.out.println("Invalid input. Please enter a number.");
                }
            } while (orderId <= 0);

            if (orders.containsKey(orderId)) {
                Order order = orders.get(orderId);
                int itemId = 1;
                do {
                    if (itemId <= 0) {
                        System.out.println("Invalid input. Please enter a positive number.");
                    }
                    System.out.println("Enter the item ID: ");
                    try {
                        itemId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        itemId = 0;
                        System.out.println("Invalid input. Please enter a number.");
                    }
                } while (itemId <= 0);
                MenuItem menuItem = null;
                for (MenuItem item : menu) {
                    if (item.getId() == itemId) {
                        menuItem = item;
                        break;
                    }
                }
                if (menuItem == null) {
                    System.out.println("Item not found in the menu.");
                } else {
                    int quantity = 1;
                    do {
                        if (quantity <= 0) {
                            System.out.println("Invalid input. Please enter a positive number.");
                        }
                        System.out.println("Enter the quantity: ");
                        try {
                            quantity = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            quantity = 0;
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    } while (quantity <= 0);
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
            int orderId = 1;
            do {
                if (orderId <= 0) {
                    System.out.println("Invalid input. Please enter a positive number.");
                }
                System.out.println("Enter the order ID: ");
                try {
                    orderId = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    orderId = 0;
                    System.out.println("Invalid input. Please enter a number.");
                }
            } while (orderId <= 0);

            if (orders.containsKey(orderId)) {
                Order order = orders.get(orderId);
                int itemId = 1;
                do {
                    if (itemId <= 0) {
                        System.out.println("Invalid input. Please enter a positive number.");
                    }
                    System.out.println("Enter the item ID: ");
                    try {
                        itemId = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        itemId = 0;
                        System.out.println("Invalid input. Please enter a number.");
                    }
                } while (itemId <= 0);

                for (OrderItem orderItem : order.getOrderItems()) {
                    if (orderItem.getMenuItem().getId() == itemId) {
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
        int orderId = 1;
        do {
            if (orderId <= 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Please enter the order ID: ");
            try {
                orderId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                orderId = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (orderId <= 0);

        if (orders.containsKey(orderId)) {
            Order order = orders.get(orderId);
            order.displayOrder();
        } else {
            System.out.println("Order not found.");
        }
    }

    public void addOrderToKitchenQueue(Scanner scanner) {
        int orderId = 1;
        do {
            if (orderId <= 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Please enter the order ID: ");
            try {
                orderId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                orderId = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (orderId <= 0);

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
        int orderId = 1;
        do {
            if (orderId <= 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Please enter the order ID: ");
            try {
                orderId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                orderId = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (orderId <= 0);

        if (orders.containsKey(orderId)) {
            orders.get(orderId).displayOrder();
        } else {
            System.out.println("Order not found.");
        }
    }

    public void checkOrderStatus(Scanner scanner) {
        int orderId = 1;
        do {
            if (orderId <= 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Please enter the order ID: ");
            try {
                orderId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                orderId = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (orderId <= 0);

        if (orders.containsKey(orderId)) {
            System.out.println("Order Status: " + orders.get(orderId).getOrderStatus());
        } else {
            System.out.println("Order not found.");
        }
    }

    public void displayCompletedOrders(Scanner scanner) {
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
        int orderId = 1;
        do {
            if (orderId <= 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Please enter the order ID: ");
            try {
                orderId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                orderId = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (orderId <= 0);

        if (orders.containsKey(orderId)) {
            orders.get(orderId).cancelOrder();
        } else {
            System.out.println("Order not found.");
        }
    }

    public void calculateTotal(Scanner scanner) {
        int orderId = 1;
        do {
            if (orderId <= 0) {
                System.out.println("Invalid input. Please enter a positive number.");
            }
            System.out.println("Please enter the order ID: ");
            try {
                orderId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                orderId = 0;
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (orderId <= 0);

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
        int choice = 1;
        Scanner scanner = new Scanner(System.in);
        do {
            printAppMenu();

            do {
                if (choice < 1 || choice > 16) {
                    System.out.println("Invalid choice. Please try again.");
                }
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    choice = 0;
                    System.out.println("Invalid input. Please enter a number.");
                }
            } while (choice < 1 || choice > 16);

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
                case 13 -> displayCompletedOrders(scanner);
                case 14 -> cancelOrder(scanner);
                case 15 -> calculateTotal(scanner);
                case 16 -> System.out.println("Thank you for using the Restaurant Order Manager.");
            }
        } while (choice != 16);
    }
}
