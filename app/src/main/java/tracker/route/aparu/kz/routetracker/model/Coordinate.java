package tracker.route.aparu.kz.routetracker.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity
public class Coordinate {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ForeignKey(entity = Record.class, parentColumns = "id", childColumns = "userId", onDelete = CASCADE)
    private long recordId;
    private double latitude;
    private double longitude;
    private Date date;

    public Coordinate() {
    }

    public Coordinate(Location myLocation, long recordId) {
        this.latitude = myLocation.getLatitude();
        this.longitude = myLocation.getLongitude();
        this.recordId = recordId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Coordinate{" + "id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", date=" + date + '}';
    }
}
