# US G007

## 1. Context

The task at hand is to organize the code from eapli. base project, and modify it to match the needs of our application, 
applying authentication and authorization for all its users and functionalities. 
This is the first time this feature is being implemented, 
so we're starting from scratch to design and develop a seamless process for users registration and authentication.

## 2. Requirements

**US G007** As a Project Manager, I want the system to support and apply authentication and authorization for all its users and functionalities.

**Acceptance Criteria:**

- G007.1. A person can have more than one form of access to the system.

[//]: # (**Customer Specifications and Clarifications:**)

[//]: # ()
[//]: # ()
[//]: # (**Dependencies/References:**)


## 3. Analysis


> **Question20:** Can one person have several roles in the system?
>
> **Answer20:** It will be very difficult to control that a person can't have more than one form of access to the system (for example, a person who is a Customer Manager could also be a candidate for a job offer). With regard to "internal" roles, I would say that we should consider a hierarchy of access. The Admin can do "everything" that the others do. Then the Customer Manager and finally the Operator.


> **Question59:** Do you want all users to access the same application and, depending on their credentials, have access to different functionalities or are they different applications (which access the same database)?
>
> **Answer59:** From the product owner's point of view, it makes sense to have separate applications. In other words, when, for example, a user "runs" the "Candidate App" application, even if they identify themselves as a valid "Customer" user, the application should not accept that login.


[//]: # ()
[//]: # (## 4. Design)

[//]: # ()
[//]: # (*In this section, the team should present the solution design that was adopted to solve the requirement. )

[//]: # (This should include, at least, a diagram of the realization of the functionality &#40;e.g., sequence diagram&#41;, a class diagram &#40;presenting the classes that support the functionality&#41;, the identification and rationale behind the applied design patterns and the specification of the main tests used to validate the functionality.*)

[//]: # ()
[//]: # (### 4.1. Realization)

[//]: # ()
[//]: # (### 4.2. Class Diagram)

[//]: # ()
[//]: # ()
[//]: # (### 4.3. Applied Patterns)

[//]: # ()
[//]: # (### 4.4. Tests)

[//]: # ()
[//]: # (Include here the main tests used to validate the functionality. Focus on how they relate to the acceptance criteria.)

[//]: # ()
[//]: # (**Test 1:** *Verifies that it is not possible to ...*)

[//]: # ()
[//]: # (**Refers to Acceptance Criteria:** G005.1)

[//]: # ()
[//]: # ()
[//]: # (```)

[//]: # (@Test&#40;expected = IllegalArgumentException.class&#41;)

[//]: # (public void ensureXxxxYyyy&#40;&#41; {)

[//]: # (	...)

[//]: # (})

[//]: # (````)
[//]: # ()
[//]: # (## 5. Implementation)

[//]: # ()
[//]: # ([Project commands]&#40;../../readme.md&#41;)

[//]: # ()
[//]: # ([Guide help]&#40;../HelpGuide.md&#41; )

[//]: # ()
[//]: # (## 6. Integration/Demonstration)

[//]: # ()
[//]: # (*In this section the team should describe the efforts realized in order to integrate this functionality with the other parts/components of the system*)

[//]: # ()
[//]: # (*It is also important to explain any scripts or instructions required to execute and demonstrate this functionality*)

[//]: # ()
[//]: # (## 7. Observations)

[//]: # ()
[//]: # (*This section should be used to include any content that does not fit any of the previous sections.*)

[//]: # ()
[//]: # (*The team should present here, for instance, a critical perspective on the developed work including the analysis of alternative solutions or related works*)

[//]: # ()
[//]: # (*The team should include in this section statements/references regarding third party works that were used in the development this work.*)