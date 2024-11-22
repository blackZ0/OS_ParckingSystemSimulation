import java.util.concurrent.Semaphore;

public class ParkingLot {
    private final Semaphore parkingSpots;
    private int totalCarsServed = 0;

    public ParkingLot(int spots) {
        parkingSpots = new Semaphore(spots);
    }

    public boolean tryToPark() throws InterruptedException {
        return parkingSpots.tryAcquire();
    }

    public void leaveParking() {
        parkingSpots.release();
    }

    public synchronized void incrementCarsServed() {
        totalCarsServed++;
    }

    public int getTotalCarsServed() {
        return totalCarsServed;
    }

    public int getAvailableSpots() {
        return parkingSpots.availablePermits();
    }
}
