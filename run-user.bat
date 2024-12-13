REM Set the classpath, assuming the build was executed with Maven copy-dependencies
SET BASE_CP=jobs4u.app.candidate.console/target/jobs4u.app.candidate.console-0.1.0.jar;jobs4u.app.candidate.console/target/dependency/*

REM Call the Java VM
java -cp %BASE_CP% lapr4.jobs4u.app.candidate.console.CandidateApp
