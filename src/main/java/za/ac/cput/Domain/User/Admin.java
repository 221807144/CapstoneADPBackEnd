package za.ac.cput.Domain.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import za.ac.cput.Domain.bookings.Bookings;
import za.ac.cput.Domain.contact.Address;
import za.ac.cput.Domain.contact.Contact;
import za.ac.cput.Domain.payment.Payment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "admin_id")
    @JsonIgnoreProperties({"user", "vehicleDisc"})
    private List<Payment> payments = new ArrayList<>();

    public Admin() {
    }

    private Admin(Builder builder) {
        this.userId = builder.userId;
        this.idNumber = builder.idNumber;
        this.birthDate = builder.birthDate;
        this.address = builder.address;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.contact = builder.contact;
        this.password = builder.password;
        this.bookings = builder.bookings;
        this.role = builder.role;
        this.payments = builder.payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "userId=" + userId +
                ", idNumber='" + idNumber + '\'' +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", paymentsCount=" + (payments != null ? payments.size() : 0) +
                '}';
    }

    public static class Builder {
        private int userId;
        private String idNumber;
        private LocalDate birthDate;
        private Address address;
        private String firstName;
        private String lastName;
        private Contact contact;
        private String password;
        private Bookings bookings;
        private Role role;
        private List<Payment> payments = new ArrayList<>();

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

        public Builder setBookings(Bookings bookings) {
            this.bookings = bookings;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setPayments(List<Payment> payments) {
            this.payments = payments;
            return this;
        }

        public Builder copy(Admin admin) {
            this.userId = admin.userId;
            this.idNumber = admin.idNumber;
            this.birthDate = admin.birthDate;
            this.address = admin.address;
            this.firstName = admin.firstName;
            this.lastName = admin.lastName;
            this.contact = admin.contact;
            this.password = admin.password;
            this.bookings = admin.bookings;
            this.role = admin.role;
            this.payments = admin.payments;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
