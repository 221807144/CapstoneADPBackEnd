package za.ac.cput.Domain.Registrations;
 /*Vehicle POJO class
     Author: Sibahle shange (222529571)*/

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Payment;
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


@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "vehicle_disc_id")
    private VehicleDisc vehicleDisc;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Add cascade here too
    @JoinColumn(name = "vehicle_id")
    private List<Ticket> ticket;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties({"user", "vehicle"})
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "applicant_id", nullable = false)

    @JsonIgnoreProperties({"applicant"})
    private Applicant applicant;
    //    @OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
//    private List<Ticket> ticket;
//
    public Vehicle() {
    }

    public Vehicle(Builder builder) {
        this.vehicleID = builder.vehicleID;
        this.vehicleName = builder.vehicleName;
        this.vehicleType = builder.vehicleType;
        this.vehicleModel = builder.vehicleModel;
        this.vehicleYear = builder.vehicleYear;
        this.vehicleColor = builder.vehicleColor;
        this.vehicleDisc = builder.vehicleDisc;
        this.licensePlate = builder.licensePlate;
        this.engineNumber = builder.engineNumber;
        this.ticket = builder.ticket;
        this.payment = builder.payment;
        this.applicant = builder.applicant;

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

    public Payment getPayment() {
        return payment;
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
                ", vehicleDisc=" + vehicleDisc +
                ", ticket=" + ticket +
                ", payment=" + payment +
                ", applicant=" + applicant +
                '}';
    }

    public static class Builder{
        private int vehicleID;
        private String vehicleName;
        private String vehicleType;
        private String vehicleModel;
        private String vehicleYear;
        private String vehicleColor;
        private VehicleDisc vehicleDisc;
        private List<Ticket> ticket;
        private Payment payment;
        private Applicant applicant;
        private String licensePlate;
        private String engineNumber;

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

        public Builder setPayment(Payment payment) {
            this.payment = payment;
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
            this.payment = vehicle.payment;
            this.applicant = vehicle.applicant;

            return this;
        }
        public Vehicle build() {
            return new Vehicle(this);
        }
    }
}
