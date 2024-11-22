import java.util.concurrent.ThreadLocalRandom;

public class Gate implements Runnable {
   private final String gateId;
    private final Semaphore gateSemaphore; // Ensures one car accesses the gate at a time
    private final Queue<Car> carsQueue; // Queue to manage cars arriving at the gate
    private int servedCarsCount; // Keeps track of the total number of cars served by this gate

    // Constructor
    public Gate(String gateId) {
        this.gateId = gateId;
        this.gateSemaphore = new Semaphore(1); // Only one car can use the gate at a time
        this.carsQueue = new LinkedList<>();
        this.servedCarsCount = 0;
    }

    // Adds a car to the gate's queue
    public synchronized void addCar(Car car) {
        carsQueue.add(car);
    }

    // Allows a car to enter the parking lot through the gate
    public void processCar(ParkingLot parkingLot) {
        while (!carsQueue.isEmpty()) {
            Car car = carsQueue.poll();
            if (car != null) {
                new Thread(() -> handleCar(car, parkingLot)).start();
            }
        }
    }

    // Handles a single car's behavior at the gate
    private void handleCar(Car car, ParkingLot parkingLot) {
        try {
            // Simulate arrival at the gate
            Thread.sleep(car.getArrivalTime() * 1000L);
            System.out.println(car.getId() + " from " + gateId + " arrived at time " + car.getArrivalTime());

            // Acquire the gate semaphore (only one car at a time)
            gateSemaphore.acquire();
            try {
                System.out.println(car.getId() + " from " + gateId + " is at the gate.");

                // Try to park the car
                parkingLot.parkCar(car.getId());

                // Simulate parking duration
                Thread.sleep(car.getParkingDuration() * 1000L);

                // Car leaves the parking lot
                parkingLot.leaveCar(car.getId());

                // Update served cars count
                incrementServedCars();
            } finally {
                gateSemaphore.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Car handling interrupted: " + car.getId());
        }
    }

    // Increment the count of served cars
    private synchronized void incrementServedCars() {
        servedCarsCount++;
    }

    // Get the total number of cars served by this gate
    public int getServedCarsCount() {
        return servedCarsCount;
    }

    // Get the gate's ID
    public String getGateId() {
        return gateId;
    }
}
