# US G005

## 1. Context

For this task is necessary to add scripts to build/executions/deployments the project and apps. 

## 2. Requirements

**US G005** As Project Manager, I want the team to add to the project the necessary scripts,
so that build/executions/deployments/... can be executed effortlessly    

**Acceptance Criteria:**

- G005.1. Include scripts for all the major tasks and execution of applications

**Customer Specifications and Clarifications:**

> **Question:** The email bot is referred to as being "out of scope". Is this about the system or the business model?
>
> **Answer:** From the point of view of the process of receiving applications, it is important to have an idea of how applications are received and how they are processed.
> That said, the automatic process described as "email bot" is outside the scope of the solution to be developed, as illustrated in figure 4.1.


> **Question:** Regarding G005, are the scripts that are referred to only for building and testing?
>
> **Answer:** I would say that at this stage (sprint A) the scripts are possibly only for building the applications, running tests and running the applications. However, the idea is that they can maintain a set of scripts that allow them to perform the most common operations at any given time in a simplified way and outside the IDE. Later on, this becomes more important when you have to prepare more complex deployments, for example.

**Dependencies/References:**

Regarding this requirement we understand that it relates to Applications Email Bot (out of scope), Applications File Bot, [Project Jobs4U](../../readme.md).

## 3. Analysis

To make these scrips we had to search for maven commands, .sh and .bat files and review pom features.
Besides that, we search and solve some errors that came up and register in [Project commands](../../readme.md)
and [Guide help](../HelpGuide.md) documentation.


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

## 5. Implementation

[Project commands](../../readme.md)

[Guide help](../HelpGuide.md) 

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