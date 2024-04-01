package gui;
//stores infos and mostly value returners
public class Reservation {
    private static int reservationCounter = 0;

    private int reservationNumber;
    private String name;
    private String date;
    private String time;
    private int adults;
    private int children;

    private static final double adultPrice = 500.0;
    private static final double childPrice = 300.0;

    public Reservation(String name, String date, String time, int adults, int children) {
        this.reservationNumber = ++reservationCounter;
        this.name = name;
        this.date = date;
        this.time = time;
        this.adults = adults;
        this.children = children;
    }

    public double calculateSubtotal() {
        return adults * adultPrice + children * childPrice;
    }

    public double calculateSubtotalReturn() {
        return calculateSubtotal();
    }

    public Object[] toTableRow() {
        return new Object[]{date, time, name, adults, children, calculateSubtotalReturn()};
    }

    public Object[] toTableRowWithNumber() {
        return new Object[]{reservationNumber, date, time, name, adults, children, calculateSubtotalReturn()};
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

}
