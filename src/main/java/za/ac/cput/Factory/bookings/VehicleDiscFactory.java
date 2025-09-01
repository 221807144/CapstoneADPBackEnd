package za.ac.cput.Factory.bookings;

import za.ac.cput.Domain.bookings.VehicleDisc;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;

public class VehicleDiscFactory {
    public static VehicleDisc createVehicleDisc(
            LocalDate issueDate,
            LocalDate expiryDate
//            Vehicle vehicle
    ){

    if(!Helper.isDiscValid(issueDate, expiryDate))
        return null;

 return new VehicleDisc.Builder()
         .setIssueDate(issueDate)
         .setExpiryDate(expiryDate)
         .build();
    }
}

