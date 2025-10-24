package za.ac.cput.Factory.User;

import jakarta.persistence.Id;
import za.ac.cput.Domain.User.License;
import za.ac.cput.Util.Helper;

import java.time.LocalDate;

public class LicenseFactory {

public static License createLicense(String licenseCode, LocalDate issueDate, LocalDate expiryDate){


    if (Helper.isNullOrEmpty(licenseCode) || issueDate == null || expiryDate == null) {
        return null;
    }

    return new License.Builder()
            .setLicenseCode(licenseCode)
            .setIssueDate(issueDate)
            .setExpiryDate(expiryDate)
            .build();
}
}
