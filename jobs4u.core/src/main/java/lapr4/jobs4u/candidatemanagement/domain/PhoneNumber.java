package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Converter;
import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * The type Phone number.
 */
@Embeddable
public class PhoneNumber implements ValueObject, Comparable<PhoneNumber>{
    private static final long serialVersionUID = 1L;
    private String number;

    /**
     * Instantiates a new Phone number.
     *
     * @param number the number
     */
    public PhoneNumber(String number) {
        this.number = number;
    }

    /**
     * Instantiates a new Phone number.
     */
    protected PhoneNumber() {

    }

    /**
     * Value of phone number.
     *
     * @param dbData the db data
     * @return the phone number
     */
    public static PhoneNumber valueOf(String dbData) {
        Preconditions.nonEmpty(dbData, "phone number should neither be null nor empty");
        Preconditions.ensure(isValid(dbData), "Invalid phone number");
        return new PhoneNumber(dbData);
    }

    /**
     * Is valid boolean.
     *
     * @param dbData the db data
     * @return the boolean
     */
// Method to validate if the provided phone number is in valid Portuguese format
    public static boolean isValid(String dbData) {
        return dbData.matches("(9\\d{8})|(2\\d{8})|(3\\d{8})|(4\\d{8})|(5\\d{8})|(2\\d{9})|(3\\d{9})|(4\\d{9})|(5\\d{9})");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    // toString method to represent the object as a string
    @Override
    public String toString() {
        return  number;
    }

    @Override
    public int compareTo(PhoneNumber o) {
        return 0;
    }
//
//    @Override
//    public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
//        return phoneNumber.toString();
//    }
//
//    @Override
//    public PhoneNumber convertToEntityAttribute(String dbData) {
//        return PhoneNumber.valueOf(dbData);
//    }
}
