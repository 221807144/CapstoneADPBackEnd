package za.ac.cput.Domain.payment;

//Thando Robert Tinto - 221482210

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import za.ac.cput.Domain.Registrations.Vehicle;

import java.time.LocalDate;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ticketId;
    private double ticketAmount;
    private LocalDate issueDate;
    private String status;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id") // This should match the mappedBy in Vehicle
    @JsonIgnore
    private Vehicle vehicle;
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    protected Ticket(){}

    private Ticket(Builder builder) {
        this.ticketId = builder.ticketId;
        this.ticketAmount = builder.ticketAmount;
        this.issueDate = builder.issueDate;
        this.status = builder.status;
        this.payment = builder.payment;
        this.ticketType = builder.ticketType;
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
    public Vehicle getVehicle() { return vehicle; }


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
            this.ticketAmount = ticketType.getFineAmount();  // Auto-set amount from enum
            return this;
        }

        public Builder  setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder copy(Builder builder) {
            this.ticketId = builder.ticketId;
            this.ticketAmount = builder.ticketAmount;
            this.issueDate = builder.issueDate;
            this.status = builder.status;
            this.payment = builder.payment;

            return this;
        }

        public Ticket build() {
            return new Ticket(this);
        }
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "issueDate=" + issueDate +
                ", ticketId=" + ticketId +
                ", ticketAmount=" + ticketAmount +
                ", status='" + status + '\'' +
                ", payment=" + payment + '\'' +
                ", ticketType=" + ticketType +
                ", vehicle=" + vehicle + '\'' +
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
