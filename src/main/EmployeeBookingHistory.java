package main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeBookingHistory {
    private final StringProperty bookingNumber;
    private final StringProperty bookingCreationDate;
    private final StringProperty bookingDate;
    private final StringProperty deskNumber;
    private final StringProperty bookingStatus;

    public EmployeeBookingHistory(String bookingNumber,String bookingCreationDate, String bookingDate, String deskNumber, String bookingStatus){
        this.bookingNumber = new SimpleStringProperty(bookingNumber);
        this.bookingCreationDate = new SimpleStringProperty(bookingCreationDate);
        this.bookingDate = new SimpleStringProperty(bookingDate);
        this.deskNumber = new SimpleStringProperty(deskNumber);
        this.bookingStatus = new SimpleStringProperty(bookingStatus);
    }

    public String getBookingNumber() {
        return bookingNumber.get();
    }

    public StringProperty bookingNumberProperty() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber.set(bookingNumber);
    }

    public String getBookingCreationDate() {
        return bookingCreationDate.get();
    }

    public StringProperty bookingCreationDateProperty() {
        return bookingCreationDate;
    }

    public void setBookingCreationDate(String bookingCreationDate) {
        this.bookingCreationDate.set(bookingCreationDate);
    }

    public String getBookingDate() {
        return bookingDate.get();
    }

    public StringProperty bookingDateProperty() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate.set(bookingDate);
    }

    public String getDeskNumber() {
        return deskNumber.get();
    }

    public StringProperty deskNumberProperty() {
        return deskNumber;
    }

    public void setDeskNumber(String deskNumber) {
        this.deskNumber.set(deskNumber);
    }

    public String getBookingStatus() {
        return bookingStatus.get();
    }

    public StringProperty bookingStatusProperty() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus.set(bookingStatus);
    }
}
