package za.ac.cput.Domain.bookings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.payment.Payment;

import java.time.LocalDate;

@Entity
public class VehicleDisc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discId;

    private LocalDate issueDate;
    private LocalDate expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties({"vehicleDisc", "user"})
    private Payment payment;

    @OneToOne(mappedBy = "vehicleDisc")
    @JsonIgnoreProperties({"vehicleDisc", "ticket", "applicant"})
    private Vehicle vehicle;

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleDisc() {
    }

    public VehicleDisc(Builder builder) {
        this.discId = builder.discId;
        this.issueDate = builder.issueDate;
        this.expiryDate = builder.expiryDate;
        this.payment = builder.payment;
    }

    public Long getDiscId() {
        return discId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return "VehicleDisc{" +
                "discId=" + discId +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }

    public static class Builder {
        private Long discId;
        private LocalDate issueDate;
        private LocalDate expiryDate;
        private Payment payment;

        public Builder setDiscId(Long discId) {
            this.discId = discId;
            return this;
        }

        public Builder setIssueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder setExpiryDate(LocalDate expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Builder copy(VehicleDisc vehicleDisc) {
            this.discId = vehicleDisc.getDiscId();
            this.issueDate = vehicleDisc.getIssueDate();
            this.expiryDate = vehicleDisc.getExpiryDate();
            this.payment = vehicleDisc.getPayment();
            return this;
        }

        public VehicleDisc build() {
            return new VehicleDisc(this);
        }
    }
}
