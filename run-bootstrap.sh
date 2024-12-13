#!/usr/bin/env bash

#REM set the class path,
#REM assumes the build was executed with maven copy-dependencies
export BASE_CP=jobs4u.bootstrappers\target\jobs4u.bootstrappers-0.1.0.jar:jobs4u.app.bootstrap/target/dependency/*;

#REM call the java VM, e.g,
java -cp $BASE_CP lapr4.jobs4u.app.bootstrap.ExemploBootstrap
