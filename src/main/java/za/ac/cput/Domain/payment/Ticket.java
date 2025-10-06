package za.ac.cput.Domain.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import za.ac.cput.Domain.Registrations.Vehicle;

import java.time.LocalDate;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ticketId;

    private double ticketAmount;
    private LocalDate issueDate;
    private String status;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    @JsonIgnoreProperties({"ticket", "vehicleDisc", "applicant"})
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties({"payment", "vehicle", "ticket"})
    private Payment payment;

    protected Ticket() {
    }

    private Ticket(Builder builder) {
        this.ticketId = builder.ticketId;
        this.ticketAmount = builder.ticketAmount;
        this.issueDate = builder.issueDate;
        this.status = builder.status;
        this.payment = builder.payment;
        this.ticketType = builder.ticketType;
        this.vehicle = builder.vehicle;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public String getStatus() {
        return status;
    }

    public double getTicketAmount() {
        return ticketAmount;
    }

    public int getTicketId() {
        return ticketId;
    }

    public Payment getPayment() {
        return payment;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public static class Builder {
        private int ticketId;
        private double ticketAmount;
        private LocalDate issueDate;
        private String status;
        private Payment payment;
        private TicketType ticketType;
        private Vehicle vehicle;

        public Builder setIssueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setTicketId(int ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder setPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public Builder setTicketType(TicketType ticketType) {
            this.ticketType = ticketType;
            this.ticketAmount = ticketType.getFineAmount();
            return this;
        }

        public Builder setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder copy(Ticket ticket) {
            this.ticketId = ticket.ticketId;
            this.ticketAmount = ticket.ticketAmount;
            this.issueDate = ticket.issueDate;
            this.status = ticket.status;
            this.payment = ticket.payment;
            this.ticketType = ticket.ticketType;
            this.vehicle = ticket.vehicle;
            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", ticketAmount=" + ticketAmount +
                ", issueDate=" + issueDate +
                ", status='" + status + '\'' +
                ", ticketType=" + ticketType +
                ", paymentId=" + (payment != null ? payment.getPaymentId() : null) +
                ", vehicleId=" + (vehicle != null ? vehicle.getVehicleID() : null) +
                '}';
    }

    public enum TicketType {
        SPEEDING_1_10_KMH(500),
        SPEEDING_30_PLUS_KMH(2500),
        DRUNK_DRIVING(2000),
        RED_LIGHT(1500),
        NO_LICENSE(1500),
        PHONE_WHILE_DRIVING(1500),
        NO_SEATBELT(500),
        RECKLESS_DRIVING(2500);

        private final double fineAmount;

        TicketType(double fineAmount) {
            this.fineAmount = fineAmount;
        }

        public double getFineAmount() {
            return fineAmount;
        }
    }
}
