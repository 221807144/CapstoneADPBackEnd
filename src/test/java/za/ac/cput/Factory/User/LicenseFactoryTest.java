package za.ac.cput.Factory.User;

import org.junit.jupiter.api.Test;
import za.ac.cput.Domain.User.License;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LicenseFactoryTest {

    @Test
    void testCreateLicense_ValidInput() {

        String licenseCode = "B";
        LocalDate issueDate = LocalDate.of(2022, 5, 1);
        LocalDate expiryDate = LocalDate.of(2027, 5, 1);

        License license = LicenseFactory.createLicense(licenseCode, issueDate, expiryDate);

        assertNotNull(license);
        assertEquals(licenseCode, license.getLicenseCode());
        assertEquals(issueDate.toString(), license.getIssueDate().toString());
        assertEquals(expiryDate.toString(), license.getExpiryDate().toString());
        assertTrue(license.getLicenseId() > 0);

        System.out.println("License created: " + license);
    }

    @Test
    void testCreateLicense_InvalidInput() {
        LocalDate issueDate = LocalDate.of(2022, 5, 1);
        LocalDate expiryDate = LocalDate.of(2027, 5, 1);

        assertNull(LicenseFactory.createLicense(null, issueDate, expiryDate));
        assertNull(LicenseFactory.createLicense("", issueDate, expiryDate));
        assertNull(LicenseFactory.createLicense("B", null, expiryDate));
        assertNull(LicenseFactory.createLicense("B", issueDate, null));
    }
}



