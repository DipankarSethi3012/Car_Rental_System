import java.util.*;
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePrice;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePrice, boolean isAvailable) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return  model;
    }

   public double calculatePrice(int days) {
        return basePrice * days;
   }

   public boolean isAvailable() {
        return  isAvailable;
   }

   public void rent() {
        isAvailable = false;
   }

   public void returnCar() {
        isAvailable = true;
   }

}

class Customer {
    private String customerId;
    private String name;
    private int age;
    private String number;

    Customer(String customerId, String name, int age, String number) {
        this.customerId = customerId;
        this.name = name;
        this.age = age;
        this.number = number;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getNumber() {
        return number;
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
        return  car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomers(Customer customer) {
        customers.add(customer);
    }

    public void rentCar( Car car, Customer customer, int days) {
        if(car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Sorry, car is not available for rent");
        }
    }

    public void returnCar(Car car) {

        Rental rentalToRemove = null;

        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
//                car.returnCar();
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully");
            car.returnCar();
        } else {
            System.out.println("car wasn't returned");
        }
    }

    public void menu() {
        Scanner in = new Scanner(System.in);

        while(true) {
            System.out.println("**** Welcome to Friends Car Rental System ****");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");

            System.out.println();
            System.out.print("Enter your choice: ");
            int choice = in.nextInt();
            in.nextLine();

            if(choice == 1) {
                System.out.println("You are renting a car");

                System.out.print("Enter your name : ");
                String name = in.next();

                System.out.println();
                System.out.print("Enter the name of id Proof: ");
                String id = in.next();

                System.out.print("Enter your Age: ");
                int age = in.nextInt();

                if(age < 18) {
                    System.out.println("You can't rent a car, you are younger than  18");
                    System.exit(1);
                }
                System.out.print("Enter your mobile number: ");
                String number = in.next();

                System.out.println("\n ** Available Cars **\n");

                for(Car car : cars) {
                    if(car.isAvailable()) {
                        System.out.println(car.getCarId() +" " + car.getBrand() +" " + car.getModel());
                    }
                }

                System.out.print("\n Enter the car Id you wanna rent: ");
                String carId = in.next();

                System.out.print("\n Enter number of days you want to rent a car: ");
                int rentalDays = in.nextInt();

                String customerID = "Cus" + customers.size() + 1;

                Customer newCustomer = new Customer(customerID, name, age, number);
                customers.add(newCustomer);

                 Car selectedCar = null;

                 for(Car car : cars) {
                     if(car.getCarId().equals(carId) && car.isAvailable()) {
                         selectedCar = car;
                         break;
                     }
                 }

                 if(selectedCar != null) {
                      double totalPrice = selectedCar.calculatePrice(rentalDays);
                     System.out.println("\n Rental Information == \n");
                     System.out.println("Customer ID :" + newCustomer.getCustomerId());
                     System.out.println("Customer Name :"+ newCustomer.getName());
                     System.out.println("Customer Mobile Number :" + newCustomer.getNumber());
                     System.out.println("Car :" + selectedCar.getBrand() +" " + selectedCar.getModel());
                     System.out.println("Rental days :" + rentalDays);
                     System.out.println("Money to pay Rs. %.2f: "+ totalPrice);

                     System.out.println("Confirm Rental (Y/N)");
                     String confirm = in.next();

                     //equalsIgnoreCase method checks equality without if matches to any of upper and lower case
                    if(confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car rented successfully");
                    }
                    else {
                        System.out.println("\n Car rental cancelled");
                    }
                 } else {
                     System.out.println("\n Invalid car selection or car not availabe for rent");
                 }


            } else if (choice == 2) {
                System.out.println("\n ** Return a car **\n");
                System.out.println("Enter the car Id you want to return :");

                String carId = in.next();

                Car carToReturn = null;

                for(Car car : cars) {
                    if(car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if(carToReturn != null) {
                    Customer customer = null;
                    for(Rental rental : rentals) {
                        if(rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if(customer != null) {
                        returnCar(carToReturn);
                        System.out.println("car returned successfully by :" + customer.getName() );
                    } else {
                        System.out.println("car isn't rented or information is missing");
                    }
                } else {
                    System.out.println("Car isn't rented or wrong car id");
                }
            } else if(choice == 3) {
                System.out.println("Thanks for choosing Friends Car Rental System");
                break;
            } else {
                System.out.println("Invalid choice Made");
            }
        }
    }

}
public class Main {
    public static void main(String[] args) {
        System.out.println("Friends Car Rental System, Welcomes you");

        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car c1 = new Car("C001", "Toyta", "Camry", 1600, true);
        Car c2 = new Car("C002","Honda", "Accord", 1700, true);
        Car c3 = new Car("C003", "Tata", "Nexon", 2500, true);

        rentalSystem.addCar(c1);
        rentalSystem.addCar(c2);
        rentalSystem.addCar(c3);

        rentalSystem.menu();

    }
}