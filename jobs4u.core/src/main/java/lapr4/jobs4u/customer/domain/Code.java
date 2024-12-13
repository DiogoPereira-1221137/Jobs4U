package lapr4.jobs4u.customer.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.StringMixin;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Code.
 */
@Embeddable
public class Code implements ValueObject, Comparable<Code>, Serializable, StringMixin {
    private static final long serialVersionUID = 1L;
    private static final int MAX_LENGTH = 10;
    private String code;

    /**
     * Instantiates a new Code.
     *
     * @param code the code
     */
    public Code(String code) {
        if (code.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("The Code is longer than 10 Letters");
        }
        if (!Character.isLetter(code.charAt(0))) {
            throw new IllegalArgumentException("The first character is not a letter.");
        }

        this.code = code.toUpperCase();
        this.code = this.code.replaceAll("[^A-Z]", "");
    }

    /**
     * Instantiates a new Code.
     */
    protected Code() {}

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        if (code.length() > MAX_LENGTH) {
            throw new IllegalArgumentException();
        }

        this.code = code.toUpperCase();
        this.code = this.code.replaceAll("[^A-Z]", "");
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public int compareTo(Code o) {
        return this.code.compareTo(o.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code1 = (Code) o;
        return Objects.equals(code, code1.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
