package za.ac.cput.Domain.bookings;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.payment.Payment;

import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VehicleDisc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discId;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @JsonManagedReference  // manages serialization to Payment
//  @JsonIgnoreProperties({"vehicleDisc"}) // do NOT ignore payment itself
    private Payment payment;

    @OneToOne(mappedBy = "vehicleDisc")
    @JsonBackReference
    private Vehicle vehicle;
    // prevents Vehicle → VehicleDisc → Vehicle loop
//    @JsonIgnoreProperties({"vehicleDisc"}) // prevents loop VehicleDisc → Vehicle → VehicleDisc


    public VehicleDisc() {
    }
    public VehicleDisc(Builder builder) {
        this.discId = builder.discId;
        this.issueDate = builder.issueDate;
        this.expiryDate = builder.expiryDate;
        this.payment = builder.payment;
        this.status = builder.status;
        this.vehicle = builder.vehicle;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Vehicle getVehicle() {
        return vehicle;
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

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "VehicleDisc{" +
                "discId=" + discId +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                ", status='" + status + '\'' +
                ", payment=" + payment +
                ", vehicle=" + vehicle +
                '}';
    }

    public static class Builder {
        private Long discId;
        private LocalDate issueDate;
        private LocalDate expiryDate;
        private Payment payment;
        private String status;
        private Vehicle vehicle;

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

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder copy(VehicleDisc vehicleDisc) {
            this.discId = vehicleDisc.getDiscId();
            this.issueDate = vehicleDisc.getIssueDate();
            this.expiryDate = vehicleDisc.getExpiryDate();
            this.payment = vehicleDisc.getPayment();
            this.status = vehicleDisc.getStatus();
            this.vehicle = vehicleDisc.getVehicle();
            return this;
        }

        public VehicleDisc build() {
            return new VehicleDisc(this);
        }
    }
}
