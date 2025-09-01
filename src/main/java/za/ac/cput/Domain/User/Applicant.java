package za.ac.cput.Domain.User;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("APPLICANT")
public class Applicant extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "license_id")
    private License license;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
    private List<Vehicle> vehicle;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestAppointment> testAppointment;
    @Enumerated(EnumType.STRING)
    protected Status status;
//    @JsonManagedReference

    private String reason;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public Applicant() { }

    private Applicant(Builder builder) {
        this.userId = builder.userId;
        this.idNumber = builder.idNumber;
        this.birthDate = builder.birthDate;
        this.address = builder.address;
        this.license = builder.license;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.contact = builder.contact;
        this.bookings = builder.bookings;
        this.password = builder.password;
        this.role = builder.role;
        this.vehicle = builder.vehicle;
        this.status = builder.status;
        this.testAppointment = builder.testAppointment;
        this.reason = builder.reason;
    }

    // --- Getters ---
    public License getLicense() { return license; }
    public List<Vehicle> getVehicle() { return vehicle; }
    public Status getStatus() { return status; }
    public String getReason() { return reason; }

    public List<TestAppointment> getTestAppointment() {
        return testAppointment;
    }

    // --- Setters (needed for AdminService updates) ---
    public void setStatus(Status status) { this.status = status; }
    public void setReason(String reason) { this.reason = reason; }

    @Override
    public String toString() {
        return "Applicant{" +
                "license=" + license +
                ", vehicle=" + vehicle +
                ", testAppointment=" + testAppointment +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", userId=" + userId +
                ", idNumber='" + idNumber + '\'' +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contact=" + contact +
                ", password='" + password + '\'' +
                ", address=" + address +
                ", bookings=" + bookings +
                ", role=" + role +
                '}';
    }

    // --- Builder ---
    public static class Builder {
        private int userId;
        private String idNumber;
        private LocalDate birthDate;
        private Address address;
        private License license;
        private String firstName;
        private String lastName;
        private Contact contact;
        private String password;
        private Bookings bookings;
        private Role role;
        private List<Vehicle> vehicle;
        private Status status;
        private List<TestAppointment> testAppointment;
        private String reason;

        public Builder setUserId(int userId) { this.userId = userId; return this; }
        public Builder setIdNumber(String idNumber) { this.idNumber = idNumber; return this; }
        public Builder setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; return this; }
        public Builder setAddress(Address address) { this.address = address; return this; }
        public Builder setLicense(License license) { this.license = license; return this; }
        public Builder setFirstName(String firstName) { this.firstName = firstName; return this; }
        public Builder setLastName(String lastName) { this.lastName = lastName; return this; }
        public Builder setContact(Contact contact) { this.contact = contact; return this; }
        public Builder setPassword(String password) { this.password = password; return this; }
        public Builder setBookings(Bookings bookings) { this.bookings = bookings; return this; }
        public Builder setRole(Role role) { this.role = role; return this; }
        public Builder setVehicle(List<Vehicle> vehicle) { this.vehicle = vehicle; return this; }
        public Builder setStatus(Status status) { this.status = status; return this; }
        public Builder setTestAppointment(List<TestAppointment> testAppointment) { this.testAppointment = testAppointment; return this; }
        public Builder setReason(String reason) { this.reason = reason; return this; }

        public Builder copy(Applicant applicant) {
            this.userId = applicant.userId;
            this.idNumber = applicant.idNumber;
            this.birthDate = applicant.birthDate;
            this.address = applicant.address;
            this.license = applicant.license;
            this.firstName = applicant.firstName;
            this.lastName = applicant.lastName;
            this.contact = applicant.contact;
            this.bookings = applicant.getBookings();
            this.password = applicant.password;
            this.role = applicant.role;
            this.vehicle = applicant.vehicle;
            this.status = applicant.status;
            this.testAppointment = applicant.testAppointment;
            this.reason = applicant.reason;
            return this;
        }

        public Applicant build() { return new Applicant(this); }
    }
}
