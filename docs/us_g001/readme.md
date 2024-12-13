# US G001

[//]: # (*This is an example template*)

## 1. Context

[//]: # (*Explain the context for this task. It is the first time the task is assigned to be developed or this tasks was incomplete in a previous sprint and is to be completed in this sprint? Are we fixing some bug?*)

This is a project-long task and serves as a guide for the team, defining the practices, constraints and concerns to be followed
over the course of the project.

## 2. Requirements

[//]: # (*In this section you should present the functionality that is being developed, how do you understand it, as well as possible correlations to other requirements &#40;i.e., dependencies&#41;. You should also add acceptance criteria.*)


**US G001** As Project Manager, I want the team to follow the technical constraints and concerns of the project



[//]: # (**Acceptance Criteria:**)

[//]: # ()
[//]: # (- G002.1. The system should...Blá Blá Blá ...)

[//]: # ()
[//]: # (- G002.2. Blá Blá Blá ...)

Some constraints and concerns that should be taken into account when designing and implementing the solution.

- **NFR01 - Programming language:**
The solution should be implemented using Java as the main language. Other languages can be used in accordance with more specific requirements.


- **NFR02 - Technical Documentation:**
Project documentation should be always available
on the project repository ("docs" folder, Markdown format) and, when applicable, in
accordance to the UML notation. The development process of every US (e.g.: analysis,
design, testing, etc.) must be reported (as part of the documentation).


- **NFR03 - Test-driven development:**
The team should develop a relevant set of automated tests for every US / Class / Method. The team should aim to adopt a test-driven
development approach.


- **NFR04 - Source Control:**
The source code of the solution as well as all the documentation and related artifacts should be versioned in a GitHub repository to be provided
to the students. Only the main (master/main) branch will be used (e.g., as a source for
releases)


- **NFR05 - Continuous Integration:**
The GitHub repository will provide night builds with
publishing of results and metrics.


- **NFR06 - Deployment and Scripts:**
The repository should include the necessary scripts
to build and deploy the solution in a variety of systems (at least Linux and Windows). It
should also include a readme.md file in the root folder explaining how to build, deploy
and execute the solution.


- **NFR07 - Database:**
By configuration, the system must support that data persistence
is done either "in memory" or in a relational database (RDB). Although in-memory database solutions can be used during development and testing, the solution must include a final deployment where a persistent relational database is used. The system
should have the ability to initialize some default data.


- **NFR08 - Authentication and Authorization:** 
The system must support and apply authentication and authorization for all its users and functionalities.


- **NFR09(LPROG) - Requirement Specifications and Interview Models:** 
The support for this functionality must follow specific technical requirements, specified in LPROG.
The ANTLR tool should be used (https://www.antlr.org/).


- **NFR10(RCOMP):** 
Functionalities related to the Candidate and Customer Apps and to the Follow Up Server part of the system have very specific technical requirements. It
must follow a client-server architecture, where a client application is used to access a server. 
Communications between these two components must follow specific protocol described in a document from RCOMP ("Application Protocol"). 
Also, the client applications can not access the relational database, they can only access the server application.


- **NFR11(RCOMP):** 
The solution should be deployed using several network nodes. It is
expected that, at least, the relational database server and the Follow Up Server be deployed in nodes different from localhost, preferably in the cloud. The e-mail notification
tasks must be executed in background by the Follow Up Server.


- **NFR12(SCOMP):** 
The base solution for the upload of files must be implemented following specific technical requirements such as the use of the C programming language with
processes, signals and pipes. Specific requirements will be provided in SCOMP.


- **NFR13(SCOMP):** 
An alternative solution for the upload of files must be implemented
following specific technical requirements such as the use of the C programming language with shared memory and semaphores. Specific requirements will be provided in
SCOMP.


- **NFR14(SCOMP):** 
The process to count words of very large files should follow specific
technical requirements such as implementing parallelism and concurrency using Java
and threads. Specific requirements will be provided in SCOMP.


- **NFR15(LAPR4):**
This project has some specific requirements regarding communication
and presentation of the project and its results. This is a concern of the project, and it's
related to the presentations for the sprint reviews in the context of the LAPR4 TP classes
(i.e., skills module). LAPR4 will provide further specification for this requirement.


**Dependencies/References:**

[//]: # (*Regarding this requirement we understand that it relates to...*)

These constraints and concerns are described in Section 3.2 of the *Integrative Project of the 4th Semester System Specification* document.


[//]: # (## 3. Analysis)

[//]: # ()
[//]: # (*In this section, the team should report the study/analysis/comparison that was done in order to take the best design decisions for the requirement. This section should also include supporting diagrams/artifacts &#40;such as domain model; use case diagrams, etc.&#41;,*)

[//]: # ()
[//]: # (## 4. Design)

[//]: # ()
[//]: # (*In this section, the team should present the solution design that was adopted to solve the requirement. This should include, at least, a diagram of the realization of the functionality &#40;e.g., sequence diagram&#41;, a class diagram &#40;presenting the classes that support the functionality&#41;, the identification and rational behind the applied design patterns and the specification of the main tests used to validate the functionality.*)

[//]: # ()
[//]: # (### 4.1. Realization)

[//]: # ()
[//]: # (### 4.2. Class Diagram)

[//]: # ()
[//]: # (![a class diagram]&#40;class-diagram-01.svg "A Class Diagram"&#41;)

[//]: # ()
[//]: # (### 4.3. Applied Patterns)

[//]: # ()
[//]: # (### 4.4. Tests)

[//]: # ()
[//]: # (Include here the main tests used to validate the functionality. Focus on how they relate to the acceptance criteria.)

[//]: # ()
[//]: # (**Test 1:** *Verifies that it is not possible to ...*)

[//]: # ()
[//]: # (**Refers to Acceptance Criteria:** G001.1)

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
[//]: # (*In this section the team should present, if necessary, some evidences that the implementation is according to the design. It should also describe and explain other important artifacts necessary to fully understand the implementation like, for instance, configuration files.*)

[//]: # ()
[//]: # (*It is also a best practice to include a listing &#40;with a brief summary&#41; of the major commits regarding this requirement.*)

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