import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final int id;
    private final int capacity;
    private int currentLoad;
    private int currentFloor;
    private Direction currentDirection;
    private final List<Request> requests;

    public Elevator(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.currentLoad = 0;
        this.currentFloor = 1;
        this.currentDirection = Direction.UP;
        this.requests = new ArrayList<>();
    }

    public synchronized void addRequest(Request request) {
        currentLoad++;
        requests.add(request);
        System.out.println("Elevator " + id + " added request: " + request);
        notifyAll();
    }

    private synchronized void processRequests() {
        while (!requests.isEmpty()) {
            Request request = requests.remove(0);
            processRequest(request);
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processRequest(Request request) {
        if (currentFloor == request.getSourceFloor()) {
            move(currentFloor, request.getDestinationFloor());
        } else {
            move(currentFloor, request.getSourceFloor());
            move(request.getSourceFloor(), request.getDestinationFloor());
        }
        currentLoad--;
    }

    private void move(int sourceFloor, int destinationFloor) {
        currentFloor = sourceFloor;
        if (currentFloor < destinationFloor) {
            currentDirection = Direction.UP;
            while (currentFloor <= destinationFloor) {
                System.out.println("Elevator " + id + " reached floor " + currentFloor);
                currentFloor++;
                try {
                    Thread.sleep(1000); // 1 sec to reach 1 floor
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            currentDirection = Direction.DOWN;
            while (currentFloor >= destinationFloor) {
                System.out.println("Elevator " + id + " reached floor " + currentFloor);
                currentFloor--;
                try {
                    Thread.sleep(1000); // 1 sec to reach 1 floor
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {
        processRequests();
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public int getCapacity() {
        return capacity;
    }
}
