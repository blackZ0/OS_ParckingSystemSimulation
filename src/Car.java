import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Car implements Runnable {
    private int id;
    private int enterTime;
    private int exitTime;
    private Gate gate;

    private ParkingLot parkLot;

    public Car(int id, int enterTime, int exitTime, Gate gate) {
        this.id = id;
        this.exitTime = exitTime;
        this.gate = gate;
        this.enterTime = enterTime;
    }

    @Override
    public void run() {
            gate.processCar(parkLot);
    }
    public int getArrivalTime(){
        return enterTime;
    }

    public int getId(){
        return id;
    }

    public int getParkingDuration(){
        return exitTime;
    }
}
