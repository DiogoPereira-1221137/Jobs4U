package lapr4.jobs4u.candidatemanagement.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Address.
 */
@Embeddable
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String street;
    private final String city;
    private final String state;
    private final String country;
    private final String zipCode;

    /**
     * Instantiates a new Address.
     *
     * @param street  the street
     * @param city    the city
     * @param state   the state
     * @param country the country
     * @param zipCode the zip code
     */
    public Address(String street, String city, String state, String country, String zipCode) {
        if (street.isEmpty() || city.isEmpty() || state.isEmpty() || country.isEmpty() || !validateZipCode(zipCode)){
            throw new IllegalArgumentException();
        }
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    /**
     * Instantiates a new Address.
     */
    public Address() {
        // Initialize all fields to default values
        this.street = "";
        this.city = "";
        this.state = "";
        this.country = "";
        this.zipCode = "";
    }

    private boolean validateZipCode(String zipCode){
        return zipCode.matches("\\d{4}-\\d{3}");
    }




    @Override
    public String toString() {
        return String.format("\033[1mAddress:\033[0m %s, %s, %s, %s, %s", street, city, state, country, zipCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(country, address.country) && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, city, state, country, zipCode);
    }
}
