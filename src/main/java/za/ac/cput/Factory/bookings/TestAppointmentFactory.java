package za.ac.cput.Factory.bookings;

import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.TestAppointment;
import za.ac.cput.Domain.bookings.TestType;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Util.Helper;

import java.time.LocalDate; //updated
import java.time.LocalTime;

public class TestAppointmentFactory {

    public static TestAppointment createTestAppointment(
            String testAddress,
            String testVenue,
            LocalDate testDate,
            boolean testResult,
            String licenseCode,
            TestType testType,     // ✅ stays here
            LocalTime testTime,
            double testAmount,
            Payment payment,
            Applicant applicant// ✅ now included
    ) {

        // Basic validations
        if (Helper.isNullOrEmpty(testAddress) ||
                Helper.isNullOrEmpty(testVenue) ||
                Helper.isNullOrEmpty(licenseCode) ||
                testType == null ||
                testAmount <= 0) {
            return null;
        }

        if (testDate.isBefore(LocalDate.now())) {
            return null; // Appointment cannot be in the past
        }

        // Build and return the appointment
        return new TestAppointment.Builder()
                .setTestAddress(testAddress)
                .setTestVenue(testVenue)
                .setTestDate(testDate)
                .setTestResult(testResult)
                .setLicenseCode(licenseCode)
                .setTestype(testType)
                .setTestTime(testTime)
                .setTestAmount(testAmount)
                .setPayment(payment)
                .setApplicant(applicant)// ✅ dynamic amount
                .build();
    }
}