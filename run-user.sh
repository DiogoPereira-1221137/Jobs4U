#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=exemplo.app.user.console/target/exemplo.app.user.console-0.1.0.jar:exemplo.app.user.console/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP lapr4.jobs4u.app.user.console.ExemploUserApp
