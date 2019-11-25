# Private S3 Repo Gradle Plugin

[![Latest release](https://badgen.net/github/tag/thubalek/private-s3-repo-gradle-plugin?label=version)](https://github.com/thubalek/private-s3-repo-gradle-plugin/releases)
[![Known Vulnerabilities](https://snyk.io/test/github/thubalek/private-s3-repo-gradle-plugin/badge.svg)](https://snyk.io/test/github/thubalek/private-s3-repo-gradle-plugin)
[![CircleCI](https://badgen.net/circleci/github/thubalek/private-s3-repo-gradle-plugin)](https://circleci.com/gh/thubalek/private-s3-repo-gradle-plugin/tree/master)

This plugin configures Amazon AWS S3 private repo for your projects. Simply either put configuration properties
to `gradle.properties` file or set environment variables (useful for CI servers).

Supported properties (replace with your own credentials):

In `~/.gradle/gradle.properties`
```
s3Repo=s3://my-private-maven-repo/repo
s3AccessKey=HKHDHDKKH59AHAHS
s3SecretKey=THIoNTUtEserecteRYSIDwaRbiLIumPeRIdEr
```

or environment variables:
```
export S3_REPO="s3://my-private-maven-repo/repo"
export S3_ACCESS_KEY="HKHDHDKKH59AHAHS"
export S3_SECRET_KEY="THIoNTUtEserecteRYSIDwaRbiLIumPeRIdEr"
```

## Installation
To use this plugin you have to do following:

1\. Add plugin to your top level `gradle.build` file in following way:

```
plugins {
    id 'net.hubalek.gradle.s3privaterepo' version '0.0.X' // replace 0.0.X with latest version of the plugin
}
```

Plugin will automagically add your S3 repository to all your build scripts and to all your submodules.
