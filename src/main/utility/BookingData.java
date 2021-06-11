package main.utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class BookingData {

    private final StringProperty bookingNumber;
    private final StringProperty employeeId;
    private final StringProperty timestamp;
    private final StringProperty bookingDate;
    private final StringProperty deskNumber;
    private final StringProperty bookingStatus;


    public BookingData(String bookingNumber, String employeeId, String timestamp, String bookingDate, String deskNumber, String bookingStatus){
        this.bookingNumber = new SimpleStringProperty(bookingNumber);
        this.employeeId = new SimpleStringProperty(employeeId);
        this.timestamp = new SimpleStringProperty(timestamp);
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

    public String getEmployeeId() {
        return employeeId.get();
    }

    public StringProperty employeeIdProperty() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId.set(employeeId);
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp.set(timestamp);
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
