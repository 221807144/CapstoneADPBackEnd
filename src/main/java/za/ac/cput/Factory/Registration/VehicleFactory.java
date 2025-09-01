package za.ac.cput.Factory.Registration;

import za.ac.cput.Domain.Registrations.Vehicle;
import za.ac.cput.Domain.User.Applicant;
import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Domain.payment.Payment;
import za.ac.cput.Domain.payment.Ticket;
import za.ac.cput.Util.Helper;

import java.util.List;

public class VehicleFactory {
 public static Vehicle createvehicle (
     String vehicleName,
     String vehicleType,
     String vehicleModel,
     String vehicleYear,
     String vehicleColor,
     String licensePlate,
     String engineNumber,
     VehicleDisc vehicleDisc,
     List<Ticket> ticket,
     Payment payment,
     Applicant applicant
 ) {
    if(Helper.isNullOrEmpty(vehicleName) || Helper.isNullOrEmpty(vehicleType) || Helper.isNullOrEmpty(vehicleModel)||
    Helper.isNullOrEmpty(vehicleYear) || Helper.isNullOrEmpty(vehicleColor) || Helper.isNullOrEmpty(licensePlate)|| vehicleDisc == null || payment == null || applicant == null
    )
        return null;
    return new Vehicle.Builder()
            .setVehicleName(vehicleName)
            .setVehicleType(vehicleType)
            .setVehicleModel(vehicleModel)
            .setVehicleYear(vehicleYear)
            .setVehicleColor(vehicleColor)
            .setLicensePlate(licensePlate)
            .setEngineNumber(engineNumber)
            .setVehicleDisc(vehicleDisc)
            .setTicket(ticket)
            .setPayment(payment)
            .setApplicant(applicant)
            .build();
 }
}
