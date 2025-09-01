package za.ac.cput.Util;

import org.apache.commons.validator.routines.EmailValidator;
import za.ac.cput.Domain.payment.Payment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Helper {
    // Validate if a string is null or empty
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Validate if an ID number is valid (example: must be 13 digits)
    public static boolean isValidIdNumber(Long idNumber) {
        return idNumber != null && String.valueOf(idNumber).length() == 13;
    }

    // Validate if a date is in the past
    public static boolean isDateInPast(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
    // Validate if a date is in the future
    private static final AtomicInteger idCounter = new AtomicInteger(1000); // Start from 1000
    public static int generateUniqueId() {
        return idCounter.getAndIncrement();
    }
    // Validate email using Apache Commons Validator
    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    public static LocalDate getDateOfBirth(String IdentityNumber) {
        int Year = Integer.parseInt(IdentityNumber.substring(0, 2));
        int Month = Integer.parseInt(IdentityNumber.substring(2, 4));
        int Day = Integer.parseInt(IdentityNumber.substring(4,6));
        LocalDate date = LocalDate.of(Year, Month, Day);
        return date;

    }
    // Validate contact number
    public static boolean isValidContactNumber(String contactNumber) {
        if (contactNumber == null || contactNumber.length() != 10) {
            return false;
        }
        // Check if it starts with '0' and contains only digits
        return contactNumber.startsWith("0") && contactNumber.matches("\\d+");
    }

    // Validate postal code
    public static boolean isValidPostalCode(String postalCode) {
        if (postalCode == null || postalCode.length() != 4) {
            return false;
        }
        return postalCode.matches("\\d+");
    }

    // cheking vevihle disc expiry date

    // Method to check if expiry date is within 5 years from issue date
    public static boolean isDiscValid(LocalDate issueDate, LocalDate expiryDate) {
        if (issueDate == null || expiryDate == null) {
            System.out.println("Issue date or expiry date cannot be null.");
            return false;
        }

        long yearsBetween = ChronoUnit.YEARS.between(issueDate, expiryDate);

        if (yearsBetween > 5) {
            System.out.println("Your vehicle disc has expired.");
            return false;
        }
        return true;
    }

    public static Boolean isAmountValid(double amount) {
        return true? !(amount == 0) && isNullOrEmpty(String.valueOf(amount)): false;
    }

    public static Payment.PaymentMethod getPaymentMethod(String paymentMethod) {
        isNullOrEmpty(paymentMethod);
        switch(paymentMethod) {
            case "Cash":
                return Payment.PaymentMethod.Cash;
            case "Card":
                return Payment.PaymentMethod.Card;
            default:
                return null;
        }
    }

    public static Payment.PaymentType getPaymentType(String paymentType) {
        isNullOrEmpty(paymentType);
        switch(paymentType) {
            case "Ticket":
                return Payment.PaymentType.Ticket;
            case "Disc":
                return Payment.PaymentType.Disc;
            case "Booking":
                return Payment.PaymentType.Booking;
            default:
                return null;
        }
    }

}
