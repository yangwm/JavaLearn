package basics.learning.interfaces;

public interface TimeClient {
    public void setTime(int hour, int minute, int second);
    public void setDate(int day, int month, int year);
    public void setDateAndTime(int day, int month, int year,
                               int hour, int minute, int second);
}