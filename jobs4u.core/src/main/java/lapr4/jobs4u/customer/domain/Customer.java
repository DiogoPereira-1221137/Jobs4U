package lapr4.jobs4u.customer.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.general.domain.model.EmailAddress;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jakarta.persistence.*;
import lapr4.jobs4u.candidatemanagement.domain.Address;
import org.springframework.data.annotation.CreatedBy;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Customer.
 */
@Entity
public class Customer implements AggregateRoot<Code>, Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private Long version;

	@OneToOne
	private SystemUser systemUser;

	@EmbeddedId
	private Code code;

	@Embedded
	private Address address;

	@ManyToOne
	private SystemUser customerManager;

	/**
	 * Instantiates a new Customer.
	 *
	 * @param systemUser      the system user
	 * @param code            the code
	 * @param address         the address
	 * @param customerManager the customer manager
	 */
	public Customer(final SystemUser systemUser, final Code code, final Address address, final SystemUser customerManager) {
		if (systemUser == null || code == null || address == null || customerManager == null) {
			throw new IllegalArgumentException("SystemUser, Code, Address, CustomerManager, and EmailAddress cannot be null");
		}
		this.systemUser = systemUser;
		this.code = code;
		this.address = address;
		this.customerManager = customerManager;
	}

	/**
	 * Instantiates a new Customer.
	 */
	protected Customer() {	}

	/**
	 * User system user.
	 *
	 * @return the system user
	 */
	public SystemUser user() {
		return systemUser;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public Code getId() {
		return code;
	}

	/**
	 * Address address.
	 *
	 * @return the address
	 */
	public Address address() {
		return address;
	}

	/**
	 * Customer manager associated system user.
	 *
	 * @return the system user
	 */
	public SystemUser customerManagerAssociated() {return customerManager;	}

	public boolean sameAs(final Object other) {
		return DomainEntities.areEqual(this.user(), other);
	}

	@Override
	public int compareTo(Code other) {
		return AggregateRoot.super.compareTo(other);
	}

	/**
	 * Sets id.
	 *
	 * @param code the code
	 */
	public void setId(Code code) {
		this.code = code;
	}

	/**
	 * Change address.
	 *
	 * @param address the address
	 */
	public void changeAddress(Address address) {
		this.address = address;
	}

	/**
	 * Change user.
	 *
	 * @param systemUser the system user
	 */
	public void changeUser(SystemUser systemUser) {
		this.systemUser = systemUser;
	}

	/**
	 * Change customer manager.
	 *
	 * @param customerManager the customer manager
	 */
	public void changeCustomerManager(SystemUser customerManager) {this.customerManager = customerManager;}

	@Override
	public Code identity() {return code;}

	@Override
	public boolean hasIdentity(Code id) {
		return AggregateRoot.super.hasIdentity(id);
	}


	@Override
	public String toString() {
		return "Customer{" +
				"version=" + version +
				", systemUser=" + systemUser +
				", code=" + code +
				", address=" + address +
				", customerManager=" + customerManager +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(version, customer.version) && Objects.equals(systemUser, customer.systemUser) && Objects.equals(code, customer.code) && Objects.equals(address, customer.address) && Objects.equals(customerManager, customer.customerManager);
	}

	@Override
	public int hashCode() {
		return Objects.hash(version, systemUser, code, address, customerManager);
	}
}
