package za.ac.cput.Domain.Registrations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registrationId;

    private String registrationNumber;
    private LocalDate registrationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties({"registration", "vehicleDisc", "ticket", "applicant"})
    private Vehicle vehicle;

    public Registration() {
    }

    private Registration(Builder builder) {
        this.registrationId = builder.registrationId;
        this.registrationNumber = builder.registrationNumber;
        this.registrationDate = builder.registrationDate;
        this.vehicle = builder.vehicle;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", registrationDate=" + registrationDate +
                ", vehicleId=" + (vehicle != null ? vehicle.getVehicleID() : null) +
                '}';
    }

    public static class Builder {
        private int registrationId;
        private String registrationNumber;
        private LocalDate registrationDate;
        private Vehicle vehicle;

        public Builder setRegistrationId(int registrationId) {
            this.registrationId = registrationId;
            return this;
        }

        public Builder setRegistrationNumber(String registrationNumber) {
            this.registrationNumber = registrationNumber;
            return this;
        }

        public Builder setRegistrationDate(LocalDate registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public Builder setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder copy(Registration registration) {
            this.registrationId = registration.registrationId;
            this.registrationNumber = registration.registrationNumber;
            this.registrationDate = registration.registrationDate;
            this.vehicle = registration.vehicle;
            return this;
        }

        public Registration build() {
            return new Registration(this);
        }
    }
}
