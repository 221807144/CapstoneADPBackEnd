package za.ac.cput.Domain.User;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

   // private String idNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact contact;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "address_id")
//    private Address address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "license_id")
    private License license;


    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"applicant", "hibernateLazyInitializer", "handler"})
    private List<Vehicle> vehicle;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"applicant"})
    private List<TestAppointment> testAppointment;


    @Enumerated(EnumType.STRING)
    private Status status;

    private String reason;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public Applicant() {}

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
        this.testAppointment = builder.testAppointment;
        this.status = builder.status;
        this.reason = builder.reason;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public License getLicense() {
        return license;
    }

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public List<TestAppointment> getTestAppointment() {
        return testAppointment;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "address=" + address +
                ", idNumber='" + idNumber + '\'' +
                ", birthDate=" + birthDate +
                ", license=" + license +
                ", vehicle=" + vehicle +
                ", testAppointment=" + testAppointment +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                '}';
    }

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
        private List<TestAppointment> testAppointment;
        private Status status;
        private String reason;

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setIdNumber(String idNumber) {
            this.idNumber = idNumber;
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder setLicense(License license) {
            this.license = license;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setContact(Contact contact) {
            this.contact = contact;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setBookings(Bookings bookings) {
            this.bookings = bookings;
            return this;
        }

        public Builder setVehicle(List<Vehicle> vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder setTestAppointment(List<TestAppointment> testAppointment) {
            this.testAppointment = testAppointment;
            return this;
        }


        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

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
            this.testAppointment = applicant.testAppointment;
            this.status = applicant.status;
            this.reason = applicant.reason;
            return this;
        }

        public Applicant build() {
            return new Applicant(this);
        }
    }
}
