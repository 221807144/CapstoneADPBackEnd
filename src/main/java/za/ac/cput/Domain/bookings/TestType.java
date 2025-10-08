package za.ac.cput.Domain.bookings;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TestType {
    @JsonProperty("DRIVERSLICENSETEST")
    DRIVERSLICENSETEST,
    @JsonProperty("LEARNERSLICENSETEST")
    LEARNERSLICENSETEST,
}
