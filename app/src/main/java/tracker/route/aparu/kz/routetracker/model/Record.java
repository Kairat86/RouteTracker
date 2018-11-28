package tracker.route.aparu.kz.routetracker.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Record {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;
    private int pointNumber;

    public Record() {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(int pointNumber) {
        this.pointNumber = pointNumber;
    }

    @Override
    public String toString() {
        return "Record{" + "date=" + date + ", pointNumber=" + pointNumber + '}';
    }
}
