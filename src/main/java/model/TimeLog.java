package model;

import java.sql.Time;

public class TimeLog {
    private int id;
    private int user_id;
    private int activity_id;
    private Time start_at;
    private Time end_at;
    private Time duration;
    private int status;


    public TimeLog() {
        super();
    }

    public TimeLog(int user_id, int activity_id, Time start_at, Time end_at, Time duration, int status) {
        this.user_id = user_id;
        this.activity_id = activity_id;
        this.start_at = start_at;
        this.end_at = end_at;
        this.duration = duration;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public Time getStart_at() {
        return start_at;
    }

    public Time getEnd_at() {
        return end_at;
    }

    public Time getDuration() {
        return duration;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public void setStart_at(Time start_at) {
        this.start_at = start_at;
    }

    public void setEnd_at(Time end_at) {
        this.end_at = end_at;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
