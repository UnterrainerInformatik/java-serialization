![GitHub forks](https://img.shields.io/github/forks/UnterrainerInformatik/java-serialization?style=social) ![GitHub stars](https://img.shields.io/github/stars/UnterrainerInformatik/java-serialization?style=social) ![GitHub repo size](https://img.shields.io/github/repo-size/UnterrainerInformatik/java-serialization) [![GitHub issues](https://img.shields.io/github/issues/UnterrainerInformatik/java-serialization)](https://github.com/UnterrainerInformatik/java-serialization/issues)

[![license](https://img.shields.io/github/license/unterrainerinformatik/FiniteStateMachine.svg?maxAge=2592000)](http://unlicense.org) [![Travis-build](https://travis-ci.org/UnterrainerInformatik/java-serialization.svg?branch=master)](https://travis-ci.org/github/UnterrainerInformatik/java-serialization) [![Maven Central](https://img.shields.io/maven-central/v/info.unterrainer.commons/serialization)](https://search.maven.org/artifact/org.webjars.npm/serialization) [![Twitter Follow](https://img.shields.io/twitter/follow/throbax.svg?style=social&label=Follow&maxAge=2592000)](https://twitter.com/throbax)




# serialization

A library to help with serialization and deserialization using [com.fasterxml.jackson](https://github.com/FasterXML/jackson).

This is a single point of configuration for our projects.

Contains a pre-configured JSON-mapper and a builder for it.



## Properties

Allows DateTime-type serialization and deserialization using ISO8601.

Ignores unknown properties when deserializing.

Ignores null-values when serializing.

Contains fix for [Project Lombok](https://projectlombok.org)s fluent- and chained accessors. 



## Usage

```java
JsonMapper mapper = JsonMapper.create();

// JSON to string.

ChildJson j = ChildJson.builder()
    .id(2L)
    .createdOn(localDateTime)
    .editedOn(localDateTime)
    .string("test")
    .build();
String result = mapper.toStringFrom(j);

// String to JSON.

String input = "{\"string\":\"gluppy\",\"blah\":\"platy\"}";
SimpleJson json = mapper.fromStringTo(SimpleJson.class, input);
```

