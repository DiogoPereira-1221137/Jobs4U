# US G004

## 1. Context

For this user story we had to research tools to setup a continuous integration server that helps developers to accomplish work in a consistent and productive manner. In this way we had to analyze the documentation of these tools, as well as research how to implement them in our project

## 2. Requirements

**US G004** As Project Manager, I want the team to set up a continuous integration server

**Acceptance Criteria:**

- G004.1. GitHub Actions/Workflows should be used

- G004.2. Whenever each contributor to the project pushes to the main branch, by accessing GitHub UI is able to see all the commits and compilation errors of the other contributors and itself.

- G004.3. When publishing results, the process must not fail due to compilation errors.

**Customer Specifications and Clarifications:**

> **Question:** With regard to the continuous integration server, will the workflow have to be executed with each push or once a day, in the evening?
>
> **Answer:** Quick answer: every time there is a push to the "main". The process run by the CI at each push must not exceed 2 minutes. The process must be able to "compile" the system, run tests and publish results without "errors", i.e. it must not fail due to "compilation" errors. In the event of failures, the "person responsible" for the failure (author of the commit/push) must justify the failure (for example, by justifying it in their area of the repository documentation).


## 3. Analysis

We had to analyze the documentation of these tools, as well as research how to implement them in our project.
To implement this user story we had to add a YAML file in the repository.

**About GitHub Actions/WorkflowWorkflow:**

Workflows are defined by a YAML file checked in to your repository and will run when triggered by an event in your repository, or they can be triggered manually, or at a defined schedule.

Workflows are defined in the _.github/workflows_ directory in a repository, and a repository can have multiple workflows, each of which can perform a different set of tasks. For example, you can have one workflow to build and test pull requests, another workflow to deploy your application every time a release is created, and still another workflow that adds a label every time someone opens a new issue.

Reference: https://docs.github.com/en/actions/using-workflows/about-workflows

[//]: # (## 4. Design)

[//]: # ()
[//]: # (*In this section, the team should present the solution design that was adopted to solve the requirement. This should include, at least, a diagram of the realization of the functionality &#40;e.g., sequence diagram&#41;, a class diagram &#40;presenting the classes that support the functionality&#41;, the identification and rationale behind the applied design patterns and the specification of the main tests used to validate the functionality.*)

[//]: # ()
[//]: # (### 4.1. Realization)

[//]: # ()
[//]: # (### 4.2. Class Diagram)

[//]: # ()
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
[//]: # (**Refers to Acceptance Criteria:** G004.1)

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