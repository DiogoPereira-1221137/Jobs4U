package lapr4.jobs4u.jobopening.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.infrastructure.authz.domain.model.Role;
import jakarta.persistence.Embeddable;

/**
 * The type Contract type.
 */
@Embeddable
public class ContractType  implements ValueObject, Comparable<ContractType> {

    private String contractType;

    /**
     * Instantiates a new Contract type.
     *
     * @param contractType the contract type
     */
    public ContractType(String contractType) {
        this.contractType=contractType;

    }

    /**
     * Instantiates a new Contract type.
     */
    protected ContractType() {

    }

    public String toString() {
        return this.contractType;
    }

    /**
     * Value of contract type.
     *
     * @param contractType the contract type
     * @return the contract type
     */
    public static ContractType valueOf(final String contractType) {
        return new ContractType(contractType);
    }

    @Override
    public int compareTo(ContractType o) {
        return 0;
    }

    /**
     * Contract type string.
     *
     * @return the string
     */
    public String contractType() {
        return contractType;
    }
}
