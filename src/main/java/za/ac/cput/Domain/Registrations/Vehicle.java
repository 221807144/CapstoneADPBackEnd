package za.ac.cput.Domain.Registrations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Ticket;

import java.util.List;

@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehicleID;

    private String vehicleName;
    private String vehicleType;
    private String vehicleModel;
    private String vehicleYear;
    private String vehicleColor;

    @Column(unique = true, nullable = false)
    private String licensePlate;

    private String engineNumber;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String vehicleImage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_disc_id")
    @JsonIgnoreProperties({"vehicle", "payment"})
    private VehicleDisc vehicleDisc;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"vehicle", "payment"})
    private List<Ticket> ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "vehicle", "testAppointment", "contact", "address", "bookings", "license", "learners"})
    private Applicant applicant;

    public Vehicle() {
    }

    public Vehicle(Builder builder) {
        this.vehicleID = builder.vehicleID;
        this.vehicleName = builder.vehicleName;
        this.vehicleType = builder.vehicleType;
        this.vehicleModel = builder.vehicleModel;
        this.vehicleYear = builder.vehicleYear;
        this.vehicleColor = builder.vehicleColor;
        this.vehicleImage = builder.vehicleImage;
        this.vehicleDisc = builder.vehicleDisc;
        this.licensePlate = builder.licensePlate;
        this.engineNumber = builder.engineNumber;
        this.ticket = builder.ticket;
        this.applicant = builder.applicant;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public VehicleDisc getVehicleDisc() {
        return vehicleDisc;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleID=" + vehicleID +
                ", vehicleName='" + vehicleName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", vehicleModel='" + vehicleModel + '\'' +
                ", vehicleYear='" + vehicleYear + '\'' +
                ", vehicleColor='" + vehicleColor + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", engineNumber='" + engineNumber + '\'' +
                ", applicantId=" + (applicant != null ? applicant.getUserId() : null) +
                '}';
    }

    public static class Builder {
        private int vehicleID;
        private String vehicleName;
        private String vehicleType;
        private String vehicleModel;
        private String vehicleYear;
        private String vehicleColor;
        private VehicleDisc vehicleDisc;
        private List<Ticket> ticket;
        private Applicant applicant;
        private String licensePlate;
        private String engineNumber;
        private String vehicleImage;

        public Builder setVehicleID(int vehicleID) {
            this.vehicleID = vehicleID;
            return this;
        }

        public Builder setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
            return this;
        }

        public Builder setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder setVehicleModel(String vehicleModel) {
            this.vehicleModel = vehicleModel;
            return this;
        }

        public Builder setVehicleYear(String vehicleYear) {
            this.vehicleYear = vehicleYear;
            return this;
        }

        public Builder setVehicleColor(String vehicleColor) {
            this.vehicleColor = vehicleColor;
            return this;
        }

        public Builder setVehicleDisc(VehicleDisc vehicleDisc) {
            this.vehicleDisc = vehicleDisc;
            return this;
        }

        public Builder setTicket(List<Ticket> ticket) {
            this.ticket = ticket;
            return this;
        }

        public Builder setApplicant(Applicant applicant) {
            this.applicant = applicant;
            return this;
        }

        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setEngineNumber(String engineNumber) {
            this.engineNumber = engineNumber;
            return this;
        }

        public Builder setVehicleImage(String vehicleImage) {
            this.vehicleImage = vehicleImage;
            return this;
        }

        public Builder copy(Vehicle vehicle) {
            this.vehicleID = vehicle.vehicleID;
            this.vehicleName = vehicle.vehicleName;
            this.vehicleType = vehicle.vehicleType;
            this.vehicleModel = vehicle.vehicleModel;
            this.vehicleYear = vehicle.vehicleYear;
            this.vehicleColor = vehicle.vehicleColor;
            this.vehicleDisc = vehicle.vehicleDisc;
            this.licensePlate = vehicle.licensePlate;
            this.engineNumber = vehicle.engineNumber;
            this.ticket = vehicle.ticket;
            this.applicant = vehicle.applicant;
            this.vehicleImage = vehicle.vehicleImage;
            return this;
        }

        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
