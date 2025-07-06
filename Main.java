import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String carBrand;
    private String carModel;
    private String carColor;
    private double basePerDay;
    private boolean isAvailable;

    public Car(String carId, String carBrand, String carModel, String carColor, double basePerDay) {
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carColor = carColor;
        this.basePerDay = basePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

    public double calculatePrice(int days) {
        return basePerDay * days;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
            System.out.println("Car rented successfully.");
        } else {
            System.out.println("Sorry, this car is not available.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;

        for (Rental rental : rentals) {
            if (rental.getCar().equals(car)) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully.");
        } else {
            System.out.println("This car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== CAR RENTAL SYSTEM ====");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            if (choice == 1) {
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getCarBrand() + " - " + car.getCarColor() + " - " + car.getCarModel());
                    }
                }

                System.out.print("Enter the Car ID you want to rent: ");
                String carId = scanner.nextLine();

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar == null) {
                    System.out.println("Invalid Car ID or car not available.");
                } else {
                    System.out.print("Enter number of days to rent: ");
                    int days = scanner.nextInt();
                    scanner.nextLine();

                    Customer customer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(customer);

                    double price = selectedCar.calculatePrice(days);

                    System.out.println("\n== Rental Information ==");

                    System.out.println("Customer Name: " + customer.getName());
                    System.out.println("Customer ID: " + customer.getCustomerId());
                    System.out.println("Car: " + selectedCar.getCarBrand() + " " + selectedCar.getCarModel());
                    System.out.println("Rental Days: " + days);
                    System.out.println("Total Price: Rs " + price);
                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, customer, days);
                    } else {
                        System.out.println("Rental cancelled.");
                    }
                }
            } else if (choice == 2) {
                System.out.print("Enter the Car ID you want to return: ");
                String carId = scanner.nextLine();

                Car rentedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equalsIgnoreCase(carId) && car.isAvailable()) {
                        rentedCar = car;
                        break;
                    }
                }

                if (rentedCar == null) {
                    System.out.println("Invalid Car ID or this car is not currently rented.");
                } else {
                    returnCar(rentedCar);
                }
            } else if (choice == 3) {
                System.out.println("Exiting... Thank you!");
                scanner.close();
                return;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem system = new CarRentalSystem();

        // input data
        system.addCar(new Car("C001", "Toyota", "Corolla", "White", 1500.0));
        system.addCar(new Car("C002", "Honda", "Civic", "Black", 1800.0));
        system.addCar(new Car("C003", "Ford", "Focus", "Red", 1600.0));

        system.menu();
    }
}

