package muntaserahmed.ricerooms;

public class Room {

    String floor;
    int number;
    int vacant;

    public Room(String floor, int number, int vacant) {
        this.floor = floor;
        this.number = number;
        this.vacant = vacant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;

        Room that = (Room) o;

        String numberString = number+"";
        String thatNumberString = that.number+"";
        if (numberString != null ? !numberString.equals(thatNumberString) : thatNumberString != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        String numberString = number+"";
        return numberString != null ? numberString.hashCode() : 0;
    }

    public String toString() {
        return this.floor + " - " + this.number + " - " + this.vacant;
    }

}
