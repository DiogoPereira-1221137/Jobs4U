/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package lapr4.jobs4u.candidatemanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import jakarta.persistence.*;
import eapli.framework.general.domain.model.EmailAddress;

import java.io.Serializable;

/**
 * A Client User. This class represents "candidates" (client users). It follows a
 * DDD approach where candidate is the root entity of the candidate Aggregate and all
 * of its properties are instances of value objects. A candidate references a
 * System User This approach may seem a little more complex than just having
 * String or native type attributes but provides for real semantic of the domain
 * and follows the Single Responsibility Pattern
 *
 * @author Jorge Santos ajs@isep.ipp.pt
 */
@Entity
public class Candidate implements AggregateRoot<EmailAddress>, Serializable {

	private static final long serialVersionUID = 1L;

	@Version
	private Long version;

	/**
	 * cascade = CascadeType.NONE as the systemUser is part of another aggregate
	 */
	@OneToOne
	private SystemUser systemUser;
	@Column(nullable = false)
	@Embedded
	private PhoneNumber phoneNumber;
	@EmbeddedId
	private EmailAddress id;


    /**
     * Instantiates a new Candidate.
     *
     * @param user        the user
     * @param phoneNumber the phone number
     */
    public Candidate(final SystemUser user, final PhoneNumber phoneNumber) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		this.systemUser = user;
		this.phoneNumber = phoneNumber;

	}

//	public Candidate build() {
//		if (this.systemUser == null || this.emailAddress == null) {
//			throw new IllegalStateException("SystemUser and EmailAddress must be set to build a Candidate");
//		}
//		Candidate candidate = new Candidate(this.systemUser, this.phoneNumber, this.emailAddress);
//		candidate.setId(this.emailAddress); // Set the EmailAddress as the identifier
//		return candidate;
//	}

    /**
     * Instantiates a new Candidate.
     */
    protected Candidate() {

	}

    /**
     * User system user.
     *
     * @return the system user
     */
    public SystemUser user() {
		return this.systemUser;
	}

	@Override
	public boolean equals(final Object o) {
		return DomainEntities.areEqual(this.user(), o);
	}

	@Override
	public int hashCode() {
		return DomainEntities.hashCode(this.user());
	}


	public boolean sameAs(final Object other) {
		return DomainEntities.areEqual(this.user(), other);
	}

//	@Override
//	public int compareTo(EmailAddress other) {
//		return DomainEntity.super.compareTo(other);
//	}

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(EmailAddress id) {
		this.id = id;
	}

    /**
     * Phone number phone number.
     *
     * @return the phone number
     */
    public PhoneNumber phoneNumber() {
		return phoneNumber;
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public EmailAddress getId() {
		return id;
	}

	@Override
	public EmailAddress identity() {
		return id;
	}

//	@Override
//	public boolean hasIdentity(EmailAddress id) {
//		return DomainEntity.super.hasIdentity(id);
//	}


//	public MecanographicNumber mecanographicNumber() {
//		return identity();
//	}
//

}
