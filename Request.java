public class Request {
    private final int sourceFloor;
    private final int destinationFloor;

    public Request(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
    }

    public getSourceFloor() {
        return sourceFloor;
    }

    public getDestinationFloor() {
        return destinationFloor;
    }
}
