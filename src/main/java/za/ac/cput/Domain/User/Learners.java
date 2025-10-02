package za.ac.cput.Domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Learners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learnersId;
    private String learnersCode;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    public Learners() {
    }
    public Learners(Builder builder) {
        this.learnersId = builder.learnersId;
        this.learnersCode = builder.learnersCode;
        this.issueDate = builder.issueDate;
        this.expiryDate = builder.expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public String getLearnersCode() {
        return learnersCode;
    }

    public Long getLearnersId() {
        return learnersId;
    }

    @Override
    public String toString() {
        return "Learners{" +
                "expiryDate='" + expiryDate + '\'' +
                ", learnersId='" + learnersId + '\'' +
                ", learnersCode='" + learnersCode + '\'' +
                ", issueDate='" + issueDate + '\'' +
                '}';
    }
    public static class Builder{
        private Long learnersId;
        private String learnersCode;
        private LocalDate issueDate;
        private LocalDate expiryDate;

        public Builder setLearnersId(Long learnersId) {
            this.learnersId = learnersId;
            return this;
        }

        public Builder setLearnersCode(String learnersCode) {
            this.learnersCode = learnersCode;
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
        public Learners build(){
            return new Learners(this);
        }
    }
}
