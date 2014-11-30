package muntaserahmed.ricerooms;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

public class Room implements Parcelable {

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
        String flag = "";

        if (this.vacant == 1) {
            flag = "<font color='#27ae60'>Available</font>";
        } else {
            flag = "<font color='#c0392b'>Occupied</font>";
        }

        return this.floor + ", Room " + this.number + " - " + Html.fromHtml(flag);
    }

    public Room(Parcel in) {
        floor = in.readString();
        number = in.readInt();
        vacant = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(floor);
        out.writeInt(number);
        out.writeInt(vacant);
    }

    public static final Parcelable.Creator<Room> CREATOR
            = new Parcelable.Creator<Room>() {
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

}
