package muntaserahmed.ricerooms;

public class Room {

    int number;
    int floor;
    int vacant;

    public Room(int floor, int number, int vacant) {
        this.floor = floor;
        this.number = number;
        this.vacant = vacant;
    }

    public String toString() {
        return this.floor + " - " + this.number;
    }

}
