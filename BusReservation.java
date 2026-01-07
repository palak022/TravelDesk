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

    void showBusDetails() {
        System.out.printf("| %-6s | %-20s | ₹%-6.0f | %-5d |\n",
                busNumber, route, fare, availableSeats);
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
            System.out.println("❌ All seats are booked.");
            return;
        }

        displayAvailableSeats();
        System.out.print("Enter seat number to book: ");
        int seatNo = sc.nextInt();

        if (seatNo < 1 || seatNo > totalSeats) {
            System.out.println("❌ Invalid seat number.");
            return;
        }

        for (Passenger p : passengers) {
            if (p.seatNumber == seatNo) {
                System.out.println("❌ Seat already booked.");
                return;
            }
        }

        sc.nextLine(); // buffer clear
        System.out.print("Enter Passenger Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Gender: ");
        String gender = sc.nextLine();

        passengers.add(new Passenger(name, age, gender, seatNo));
        availableSeats--;

        System.out.println("\n✅ Booking Confirmed!");
        System.out.println("Bus      : " + busNumber);
        System.out.println("Route    : " + route);
        System.out.println("Seat No  : " + seatNo);
        System.out.println("Fare     : ₹" + fare);
    }

    void cancelSeat(Scanner sc) {
        displayAvailableSeats();
        System.out.print("Enter seat number to cancel: ");
        int seatNo = sc.nextInt();

        Passenger removePassenger = null;
        for (Passenger p : passengers) {
            if (p.seatNumber == seatNo) {
                removePassenger = p;
                break;
            }
        }

        if (removePassenger != null) {
            passengers.remove(removePassenger);
            availableSeats++;
            System.out.println("✅ Seat " + seatNo + " cancelled successfully.");
        } else {
            System.out.println("❌ Seat not booked.");
        }
    }

    void showPassengers() {
        if (passengers.isEmpty()) {
            System.out.println("No passengers booked yet.");
            return;
        }

        System.out.println("\nPassengers – Bus " + busNumber + " (" + route + ")");
        System.out.println("+-------+----------------------+-----+--------+");
        System.out.println("| Seat  | Name                 | Age | Gender |");
        System.out.println("+-------+----------------------+-----+--------+");

        for (Passenger p : passengers) {
            System.out.printf("| %-5d | %-20s | %-3d | %-6s |\n",
                    p.seatNumber, p.name, p.age, p.gender);
        }
        System.out.println("+-------+----------------------+-----+--------+");
    }

    double calculateRevenue() {
        return passengers.size() * fare;
    }
}

public class BusReservation {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Bus> buses = new ArrayList<>();

        buses.add(new Bus("TD101", "Indore → Bhopal", 10, 450));
        buses.add(new Bus("TD102", "Delhi → Jaipur", 8, 650));
        buses.add(new Bus("TD103", "Mumbai → Pune", 12, 350));

        char choice;

        do {
            System.out.println("\n============================================");
            System.out.println("     TRAVELDESK – BUS RESERVATION SYSTEM");
            System.out.println("============================================");
            System.out.println("1. Show All Buses");
            System.out.println("2. Book Seat");
            System.out.println("3. Cancel Seat");
            System.out.println("4. Show Passengers");
            System.out.println("5. Show Bus Revenue");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int option = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (option) {

                case 1:
                    System.out.println("+--------+----------------------+--------+-------+");
                    System.out.println("| Bus No | Route                | Fare   | Seats |");
                    System.out.println("+--------+----------------------+--------+-------+");
                    for (Bus b : buses) b.showBusDetails();
                    System.out.println("+--------+----------------------+--------+-------+");
                    break;

                case 2:
                    System.out.print("Enter Bus Number: ");
                    String bookBusNo = sc.nextLine();

                    Bus bookBus = null;
                    for (Bus b : buses) {
                        if (b.busNumber.equalsIgnoreCase(bookBusNo)) {
                            bookBus = b;
                            break;
                        }
                    }

                    if (bookBus != null)
                        bookBus.bookSeat(sc);
                    else
                        System.out.println("❌ Invalid Bus Number.");
                    break;

                case 3:
                    System.out.print("Enter Bus Number: ");
                    String cancelBusNo = sc.nextLine();

                    Bus cancelBus = null;
                    for (Bus b : buses) {
                        if (b.busNumber.equalsIgnoreCase(cancelBusNo)) {
                            cancelBus = b;
                            break;
                        }
                    }

                    if (cancelBus != null)
                        cancelBus.cancelSeat(sc);
                    else
                        System.out.println("❌ Invalid Bus Number.");
                    break;

                case 4:
                    System.out.print("Enter Bus Number: ");
                    String showBusNo = sc.nextLine();

                    Bus showBus = null;
                    for (Bus b : buses) {
                        if (b.busNumber.equalsIgnoreCase(showBusNo)) {
                            showBus = b;
                            break;
                        }
                    }

                    if (showBus != null)
                        showBus.showPassengers();
                    else
                        System.out.println("❌ Invalid Bus Number.");
                    break;

                case 5:
                    System.out.print("Enter Bus Number: ");
                    String revenueBusNo = sc.nextLine();

                    Bus revenueBus = null;
                    for (Bus b : buses) {
                        if (b.busNumber.equalsIgnoreCase(revenueBusNo)) {
                            revenueBus = b;
                            break;
                        }
                    }

                    if (revenueBus != null)
                        System.out.println("Total Revenue: ₹" + revenueBus.calculateRevenue());
                    else
                        System.out.println("❌ Invalid Bus Number.");
                    break;

                case 6:
                    System.out.println("Thank you for using TravelDesk!");
                    System.exit(0);

                default:
                    System.out.println("❌ Invalid choice.");
            }

            System.out.print("\nDo you want to continue? (Y/N): ");
            choice = sc.next().charAt(0);

        } while (choice == 'Y' || choice == 'y');

        sc.close();
    }
}
