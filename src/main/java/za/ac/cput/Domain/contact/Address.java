package za.ac.cput.Domain.contact;
/*Masibuve Sikhulume
221807144
 */
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import za.ac.cput.Domain.User.User;

@Entity
public class Address {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    private String street;
    private String city;
    private String province;
    private String country;
    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private User user;
    public Address() {
    }
    private Address(Builder builder) {
        this.addressId = builder.addressId;
        this.street = builder.street;
        this.city = builder.city;
        this.province = builder.province;
        this.country = builder.country;
        this.user=builder.user;
    }
    public int getAddressId() {
        return addressId;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getProvince() {
        return province;
    }
    public String getCountry() {
        return country;
    }
    public User getUser() {
        return user;
    }
    @Override

    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", user=" + user +
                '}';
    }
    public static class Builder{
        private int addressId;
        private String street;
        private String city;
        private String province;
        private String country;
        private User user;

        public Builder setAddressId(int addressId) {
            this.addressId = addressId;
            return this;
        }
        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }
        public Builder setCity(String city) {
            this.city = city;
            return this;
        }
        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }
        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }
        public Builder setUser(User user) {
            this.user = user;
            return this;
        }
        public Builder copy(Address address) {
            this.addressId = address.addressId;
            this.street = address.street;
            this.city = address.city;
            this.province = address.province;
            this.country = address.country;
            return this;
        }
        public Address build() {
            return new Address(this);
        }
    }
}
