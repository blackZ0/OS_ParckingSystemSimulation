import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParkingSimulation{
    public static void main(String[] args) {
        ParkingLot parkingLot = new ParkingLot(4);

        List<Car> gate1Cars = new ArrayList<>();
        List<Car> gate2Cars = new ArrayList<>();
        List<Car> gate3Cars = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                int gate = Integer.parseInt(parts[0].split(" ")[1]);
                int carId = Integer.parseInt(parts[1].split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkingDuration = Integer.parseInt(parts[3].split(" ")[1]);

                Car car = new Car(carId, parkingDuration, parkingLot);
                car.setArrivalTime(arrivalTime);

                switch (gate) {
                    case 1 -> gate1Cars.add(car);
                    case 2 -> gate2Cars.add(car);
                    case 3 -> gate3Cars.add(car);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gate gate1 = new Gate(gate1Cars);
        Gate gate2 = new Gate(gate2Cars);
        Gate gate3 = new Gate(gate3Cars);

        gate1.start();
        gate2.start();
        gate3.start();

        try {
            gate1.join();
            gate2.join();
            gate3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Total cars served: " + parkingLot.getTotalCarsServed());
    }
}
