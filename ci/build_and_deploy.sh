#!/bin/bash

if [[ $TRAVIS_BRANCH == 'master' ]]; then
  ./gradlew clean build -x :keanu-python:build -x :docs:runPythonSnippets publishToSonatype -PnexusUser=$NEXUS_USER -PnexusPassword=$NEXUS_PASSWORD -Psigning.keyId=$SIGNING_KEY_ID -Psigning.password=$SIGNING_PASSWORD -Psigning.secretKeyRingFile=../ci/secret-keys-keanu.gpg --info --stacktrace
else
  ./gradlew clean build -x :keanu-python:build -x :docs:runPythonSnippets --info --stacktrace
fi
