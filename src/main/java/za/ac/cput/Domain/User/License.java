package za.ac.cput.Domain.User;

import jakarta.persistence.*;

import java.time.LocalDate;


/*Emihle Thole
  221755349
  18/05/18*/
@Entity
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long licenseId; // Changed to Long
    private String licenseCode;
    private LocalDate issueDate;
    private LocalDate expiryDate;


    public License() {
    }



    public License(Builder builder) {
        this.licenseId = builder.licenseId;
        this.licenseCode = builder.licenseCode;
        this.issueDate = builder.issueDate;
        this.expiryDate = builder.expiryDate;


    }

    public Long getLicenseId() {
        return licenseId;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }


    @Override
    public String toString() {
        return "License{" +
                "licenseId=" + licenseId +
                ", licenseCode='" + licenseCode + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }

    public static class Builder {

        private Long licenseId;
        private String licenseCode; ;
        private LocalDate issueDate;
        private LocalDate expiryDate;

        public Builder setLicenseId(Long licenseId) {
            this.licenseId = licenseId;
            return this;
        }
        public Builder setLicenseCode(String licenseCode) {
            this.licenseCode = licenseCode;
            return this;
        }
        public Builder setIssueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }
        public Builder setExpiryDate(LocalDate expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }
        public Builder copy(License license) {
            this.licenseId = license.licenseId;
            this.licenseCode = license.licenseCode;
            this.issueDate = license.issueDate;
            this.expiryDate = license.expiryDate;
            return this;
        }
        public License build() {
            return new License(this);
        }
    }}