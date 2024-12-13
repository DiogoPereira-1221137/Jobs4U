# US 2001b

## 1. Context


The task at hand is to list all my applications and their state (including the number of applicants), as a Candidate.
This is the first time this feature is being implemented,
so we're starting from scratch to design and develop a seamless process for setting up the process to list all my applications and their state (including the number of applicants).

## 2. Requirements

**US 2001b**  As Product Owner, I want the system to, continuously, process the files produced by the Applications Email Bot, so that they can be imported into the system by initiative of the Operator

**Acceptance Criteria:**

- 2001b.1. Utilize **processes, shared memory and semaphore primitives**

**Customer Specifications and Clarifications:**

> **Question:** What information should appear under the candidate's name (full name, first and last name, etc.)?
>
> **Answer:** I would say the name as received in the application you made (page 6, "name of the candidate").

**Dependencies/References:**

* There is a dependency to "US 2001:  As Product Owner, I want the system to, continuously, process the files produced by the Applications Email Bot, so that they can be imported into the system by initiative of the Operator."

NFR13: 
The “Applications File Bot” must be developed in C and utilize **processes, shared
memory and semaphore primitives**. It's crucial to eliminate busy waiting wherever
possible.

A child process should be created to periodically monitor an input directory for new
files related to the 'Application' phase of the recruitment process. If new files are
detected, a notification (using a semaphore) should be sent to the parent process.
Please refer to Section 2.2.3 of the “System Specification” document for a
description of the input directory, input files, output directory, and their expected
subdirectories.

Upon receiving the notification, the parent process should distribute the new files
among a fixed number of worker child processes. Each child process will be
responsible for copying all files related to a specific candidate to its designated
subdirectory in the output directory.
Once a child has finished copying all files for a candidate, it should inform its parent
that it is ready to perform additional work. Child workers do not terminate.
Once all files for all candidates have been copied, the parent process should
generate a report file in the output directory. This report should list, for each
candidate, the name of the output subdirectory and the names of all files that were
copied.

The names of the input and output directories, the number of worker children, the
time interval for periodic checking of new files, etc., should be configurable. This
configuration can be achieved either through input parameters provided when
running the application or by reading from a configuration file.

Unit and integration tests are highly valued


**Input and Output Data**

**Input Data:**

* Typed data:
    * Input folder
    * Output folder
    

* Selected data:
    * None


**Output Data:**
* Sucess message 



## 3. Analysis

* 

[//]: # (### 3.1. Domain Model)

[//]: # ()
[//]: # (![sub domain model]&#40;us2001b_sub_domain_model.svg&#41;)

[//]: # (## 4. Design)

[//]: # ()
[//]: # (**Domain Class/es:** )

[//]: # ()
[//]: # (**Controller:** )

[//]: # ()
[//]: # (**UI:** )

[//]: # ()
[//]: # (**Repository:**	)

[//]: # ()
[//]: # (**Service:** )

[//]: # ()
[//]: # (### 4.1. Sequence Diagram)

[//]: # ()
[//]: # (![sequence diagram]&#40;us2001b_sequence_diagram.svg&#41;)

[//]: # ()
[//]: # (### 4.2. Class Diagram)

[//]: # ()
[//]: # (![a class diagram]&#40;us2001b_class_diagram.svg &#41;)

[//]: # (### 4.3. Applied Patterns)

[//]: # ()
[//]: # (### 4.4. Tests)

[//]: # ()
[//]: # (Include here the main tests used to validate the functionality. Focus on how they relate to the acceptance criteria.)

[//]: # ()
[//]: # (**Test 1:** *Verifies that it is not possible to ...*)

[//]: # ()
[//]: # (**Refers to Acceptance Criteria:** G002.1)

[//]: # ()
[//]: # ()
[//]: # (```)

[//]: # (@Test&#40;expected = IllegalArgumentException.class&#41;)

[//]: # (public void ensureXxxxYyyy&#40;&#41; {)

[//]: # (	...)

[//]: # (})

[//]: # (````)

## 5. Implementation

### Methods in the Controller

* 
## 6. Integration/Demonstration

[//]: # (## 7. Observations)

[//]: # ()
[//]: # (*This section should be used to include any content that does not fit any of the previous sections.*)

[//]: # ()
[//]: # (*The team should present here, for instance, a critical perspective on the developed work including the analysis of alternative solutions or related works*)

[//]: # ()
[//]: # (*The team should include in this section statements/references regarding third party works that were used in the development this work.*)