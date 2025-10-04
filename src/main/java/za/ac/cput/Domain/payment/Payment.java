package za.ac.cput.Domain.payment;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import za.ac.cput.Domain.User.User;
import za.ac.cput.Domain.bookings.VehicleDisc;

import java.time.LocalDate;

/*
Thando Tinto
221482210
*/

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String paymentDetails;
    private double paymentAmount;
    private LocalDate paymentDate;
    private String cardholderName;
    private long cardNumber;
    private LocalDate expiryDate;
    private short cvv;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "vehicles", "payments"})
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @JsonIgnoreProperties({"payment", "vehicleDisc"})
    @OneToOne(mappedBy = "payment")
    private VehicleDisc vehicleDisc;

    public Payment() {
    }
    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.paymentType = builder.paymentType;
        this.paymentMethod = builder.paymentMethod;
        this.paymentDetails = builder.paymentDetails;
        this.paymentAmount = builder.paymentAmount;
        this.paymentDate = builder.paymentDate;
        this.cardholderName = builder.cardholderName;
        this.cardNumber = builder.cardNumber;
        this.expiryDate = builder.expiryDate;
        this.cvv = builder.cvv;
        this.user = builder.user;
        this.vehicleDisc = builder.vehicleDisc;
    }

    public VehicleDisc getVehicleDisc() {
        return vehicleDisc;
    }

    public User getUser() {
        return user;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public short getCvv() {
        return cvv;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "paymentId=" + paymentId +
                ", paymentType=" + paymentType +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentDetails='" + paymentDetails + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", paymentDate=" + paymentDate +
                ", cardName='" + cardholderName + '\'' +
                ", cardNumber=" + cardNumber +
                ", cardDate=" + expiryDate +
                ", cvv=" + cvv +
                ", user=" + user +
                '}';
    }

    public static class Builder {
        private int paymentId;
        private PaymentType paymentType;
        private PaymentMethod paymentMethod;
        private String paymentDetails;
        private double paymentAmount;
        private LocalDate paymentDate;
        private String cardholderName;
        private long cardNumber;
        private LocalDate expiryDate;
        private short cvv;
        private User user;
        private VehicleDisc vehicleDisc;

        public Builder setPaymentType(PaymentType paymentType) {
            this.paymentType = paymentType;
            this.paymentDetails = paymentType.getPaymentDetails();
            return this;
        }

        public Builder setVehicleDisc(VehicleDisc vehicleDisc) {
            this.vehicleDisc = vehicleDisc;
            return this;
        }

        public Builder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder setPaymentAmount(double paymentAmount) {
            this.paymentAmount = paymentAmount;
            return this;
        }

        public Builder setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setExpiryDate(LocalDate expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder setCardholderName(String cardholderName) {
            this.cardholderName = cardholderName;
            return this;
        }

        public Builder setCardNumber(long cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setCvv(short cvv) {
            this.cvv = cvv;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentId = payment.paymentId;
            this.paymentType = payment.paymentType;
            this.paymentMethod = payment.paymentMethod;
            this.paymentDetails = payment.paymentDetails;
            this.paymentAmount = payment.paymentAmount;
            this.paymentDate = payment.paymentDate;
            this.cardholderName = payment.cardholderName;
            this.cardNumber = payment.cardNumber;
            this.cvv = payment.cvv;
            this.expiryDate = payment.expiryDate;
            this.user = payment.user;
            this.vehicleDisc = payment.vehicleDisc;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }

    public enum PaymentType {
        Ticket("Payment for ticket."),
        Booking("Payment for booking."),
        Disc("Payment for vehicle disc."),;

        private final String PaymentDetails;

        PaymentType(String paymentDetails) {
            PaymentDetails = paymentDetails;
        }

        public String getPaymentDetails() {
            return PaymentDetails;
        }

    }
    public enum PaymentMethod{
        Card,
        Cash
    }
}



