import java.util.*;

class Passenger {
    String name;
    int age;
    String gender;
    int seatNumber;

    Passenger(String name, int age, String gender, int seatNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.seatNumber = seatNumber;
    }
}

class Bus {
    String busNumber;
    String route;
    int totalSeats;
    int availableSeats;
    double fare;
    List<Passenger> passengers;

    Bus(String busNumber, String route, int totalSeats, double fare) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare = fare;
        this.passengers = new ArrayList<>();
    }

    void displayAvailableSeats() {
        System.out.println("\nAvailable Seats (" + availableSeats + "/" + totalSeats + "):");
        for (int i = 1; i <= totalSeats; i++) {
            boolean booked = false;
            for (Passenger p : passengers) {
                if (p.seatNumber == i) {
                    booked = true;
                    break;
                }
            }
            if (!booked) System.out.print(i + " ");
        }
        System.out.println();
    }

    void bookSeat(Scanner sc) {
        if (availableSeats == 0) {
            System.out.println("\nSorry, all seats are booked!");
            return;
        }
        displayAvailableSeats();
        System.out.print("Enter seat number to book: ");
        int seatNo = sc.nextInt();
        if (seatNo < 1 || seatNo > totalSeats) {
            System.out.println("Invalid seat number.");
            return;
        }
        for (Passenger p : passengers) {
            if (p.seatNumber == seatNo) {
                System.out.println("Seat already booked!");
                return;
            }
        }
        sc.nextLine(); // consume newline
        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();

        Passenger passenger = new Passenger(name, age, gender, seatNo);
        passengers.add(passenger);
        availableSeats--;
        System.out.println("\n✅ Seat booked successfully!");
        System.out.println("Ticket Details:");
        System.out.println("Bus: " + busNumber + " | Route: " + route + " | Seat: " + seatNo);
        System.out.println("Passenger: " + name + ", Age: " + age + ", Gender: " + gender);
        System.out.println("Fare: $" + fare);
    }

    void cancelSeat(Scanner sc) {
        System.out.print("Enter seat number to cancel: ");
        int seatNo = sc.nextInt();
        Passenger toRemove = null;
        for (Passenger p : passengers) {
            if (p.seatNumber == seatNo) {
                toRemove = p;
                break;
            }
        }
        if (toRemove != null) {
            passengers.remove(toRemove);
            availableSeats++;
            System.out.println("✅ Seat " + seatNo + " cancelled successfully!");
        } else {
            System.out.println("Seat not found or not booked.");
        }
    }

    void showPassengers() {
        if (passengers.isEmpty()) {
            System.out.println("No passengers booked yet.");
            return;
        }
        System.out.println("\nPassengers on Bus " + busNumber + " (" + route + "):");
        System.out.println("+-------+----------------------+-----+--------+");
        System.out.println("| Seat  | Name                 | Age | Gender |");
        System.out.println("+-------+----------------------+-----+--------+");
        for (Passenger p : passengers) {
            System.out.printf("| %-5d | %-20s | %-3d | %-6s |\n", p.seatNumber, p.name, p.age, p.gender);
        }
        System.out.println("+-------+----------------------+-----+--------+");
    }

    double calculateRevenue() {
        return passengers.size() * fare; // correct revenue calculation
    }
}

public class BusReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("B101", "City A -> City B", 10, 25.0));
        buses.add(new Bus("B102", "City C -> City D", 8, 30.0));
        buses.add(new Bus("B103", "City E -> City F", 12, 20.0));

        char choice;
        do {
            System.out.println("\n==========================================");
            System.out.println("       BUS RESERVATION SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Show All Buses");
            System.out.println("2. Book Seat");
            System.out.println("3. Cancel Seat");
            System.out.println("4. Show Passengers");
            System.out.println("5. Show Bus Revenue");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int option = sc.nextInt();

            sc.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.println("\nAvailable Buses:");
                    System.out.println("+--------+------------------+---------+----------------+");
                    System.out.println("| Bus No | Route            | Fare($) | AvailableSeats |");
                    System.out.println("+--------+------------------+---------+----------------+");
                    for (Bus bus : buses) {
                        System.out.printf("| %-6s | %-16s | %-7.2f | %-14d |\n",
                                bus.busNumber, bus.route, bus.fare, bus.availableSeats);
                    }
                    System.out.println("+--------+------------------+---------+----------------+");
                    break;
                case 2:
                    System.out.print("Enter Bus Number to book: ");
                    String busNoBook = sc.nextLine();
                    Bus busToBook = null;
                    for (Bus b : buses) if (b.busNumber.equalsIgnoreCase(busNoBook)) busToBook = b;
                    if (busToBook != null) busToBook.bookSeat(sc);
                    else System.out.println("Invalid Bus Number.");
                    break;
                case 3:
                    System.out.print("Enter Bus Number to cancel: ");
                    String busNoCancel = sc.nextLine();
                    Bus busToCancel = null;
                    for (Bus b : buses) if (b.busNumber.equalsIgnoreCase(busNoCancel)) busToCancel = b;
                    if (busToCancel != null) busToCancel.cancelSeat(sc);
                    else System.out.println("Invalid Bus Number.");
                    break;
                case 4:
                    System.out.print("Enter Bus Number to show passengers: ");
                    String busNoShow = sc.nextLine();
                    Bus busToShow = null;
                    for (Bus b : buses) if (b.busNumber.equalsIgnoreCase(busNoShow)) busToShow = b;
                    if (busToShow != null) busToShow.showPassengers();
                    else System.out.println("Invalid Bus Number.");
                    break;
                case 5:
                    System.out.print("Enter Bus Number to show revenue: ");
                    String busNoRevenue = sc.nextLine();
                    Bus busToRevenue = null;
                    for (Bus b : buses) if (b.busNumber.equalsIgnoreCase(busNoRevenue)) busToRevenue = b;
                    if (busToRevenue != null)
                        System.out.printf("Total Revenue for Bus %s: $%.2f\n", busToRevenue.busNumber, busToRevenue.calculateRevenue());
                    else System.out.println("Invalid Bus Number.");
                    break;
                case 6:
                    System.out.println("Exiting... Thank you for using the Bus Reservation System.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }

            System.out.print("\nDo you want to continue? (Y/N): ");
            choice = sc.next().charAt(0);

        } while (choice == 'Y' || choice == 'y');

        sc.close();
    }
}
