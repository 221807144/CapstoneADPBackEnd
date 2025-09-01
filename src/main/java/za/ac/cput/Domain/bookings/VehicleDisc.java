package za.ac.cput.Domain.bookings;

/*VehicleDisc POJO class
Author: Sibahle shange (222529571)*/

import jakarta.persistence.*;
import za.ac.cput.Domain.Registrations.Vehicle;

import java.time.LocalDate;
@Entity
public class VehicleDisc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discId;
    private LocalDate issueDate;
    private LocalDate expiryDate;
//
//    @OneToOne(mappedBy = "vehicleDisc")
//    private Vehicle vehicle;

    public VehicleDisc() {
    }

    public VehicleDisc(Builder builder) {
        this.discId = builder.discId;
        this.issueDate = builder.issueDate;
        this.expiryDate = builder.expiryDate;

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
                "discId='" + discId + '\'' +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                '}';
    }
    
    public static class Builder {
        private Long discId;
        private LocalDate issueDate;
        private LocalDate expiryDate;

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
        public Builder copy(VehicleDisc vehicleDisc) {
            this.discId = vehicleDisc.getDiscId();
            this.issueDate = vehicleDisc.getIssueDate();
            this.expiryDate = vehicleDisc.getExpiryDate();
            return this;
        }

        public VehicleDisc build() {
            return new VehicleDisc(this);
        }
    }
}
