# Log4j Migration Plan: serialization

**Layer**: 1 — update after `parent-pom`.

## Before Starting

Prompt the user for the following version numbers before making any changes:

| Variable | Question |
|----------|----------|
| `NEW_PARENT_POM_VERSION` | What is the new `parent-pom` version? |
| `OWN_NEW_VERSION` | What version should `serialization` be bumped to? (currently `1.0.1`) |

## Context

Part of a migration from Log4j 1.x to Log4j 2.25.3 across all libraries. The logging backend change happens in `parent-pom`; this project needs the parent version bump and log4j config conversion. No `@Slf4j` usage in source — config files only.

> **IMPORTANT for execution**: This plan should be executed by actually making the file changes described below — create the new `log4j2.xml` / `log4j2-test.xml` files with the content provided, and delete the old `log4j.properties` files. Do not leave the config migration as a manual step.

## Current State

- **Artifact**: `info.unterrainer.commons:serialization`
- **Parent**: `parent-pom:1.0.1`
- **Own dependencies**: jackson, jmapper (no in-house deps)
- **log4j.properties**: YES (main + test, identical content)
- **@Slf4j usage**: none

### Current `log4j.properties` content (both main and test):
```properties
log4j.rootLogger=DEBUG, A1
log4j.appender.A1=org.apache.log4j.ConsoleAppender
log4j.appender.A1.layout=org.apache.log4j.PatternLayout
log4j.appender.A1.layout.charset=UTF-8
log4j.appender.A1.layout.ConversionPattern=%-4r [%t] %-5p %c %x - %m%n
log4j.logger.io.netty=WARN
log4j.logger.org.eclipse.milo=WARN
log4j.logger.com.googlecode.jmapper.JMapper=FATAL
log4j.logger.org.reflections.Reflections=WARN
```

## Steps

### 1. Update parent version in `pom.xml`

Change the parent version (line 7) to the new parent-pom version:

```xml
<parent>
    <groupId>info.unterrainer.commons</groupId>
    <artifactId>parent-pom</artifactId>
    <version>NEW_PARENT_POM_VERSION</version>
</parent>
```

### 2. Bump own version

Increment `<version>` (line 11, currently `1.0.1`).

### 3. Create `src/main/resources/log4j2.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout charset="UTF-8"
                           pattern="%-4r [%t] %-5p %c %x - %m%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="io.netty" level="WARN"/>
        <Logger name="org.eclipse.milo" level="WARN"/>
        <Logger name="com.googlecode.jmapper.JMapper" level="FATAL"/>
        <Logger name="org.reflections.Reflections" level="WARN"/>
        <Root level="DEBUG">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

### 4. Create `src/test/resources/log4j2-test.xml`

Same content as above. Log4j 2 auto-detects `log4j2-test.xml` on the test classpath (takes precedence over `log4j2.xml` from main):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout charset="UTF-8"
                           pattern="%-4r [%t] %-5p %c %x - %m%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="io.netty" level="WARN"/>
        <Logger name="org.eclipse.milo" level="WARN"/>
        <Logger name="com.googlecode.jmapper.JMapper" level="FATAL"/>
        <Logger name="org.reflections.Reflections" level="WARN"/>
        <Root level="DEBUG">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

### 5. Delete old config files

- Delete `src/main/resources/log4j.properties`
- Delete `src/test/resources/log4j.properties`

### 6. Build, test, install

```bash
mvn clean install
```

### 7. Verify

```bash
mvn dependency:tree -Dincludes="*log4j*,*slf4j*"
```

Must show Log4j 2 artifacts, not `slf4j-log4j12` / `slf4j-simple`.

## Files Changed

| File | Action |
|------|--------|
| `pom.xml` | Update parent version, bump own version |
| `src/main/resources/log4j2.xml` | Create |
| `src/test/resources/log4j2-test.xml` | Create |
| `src/main/resources/log4j.properties` | Delete |
| `src/test/resources/log4j.properties` | Delete |
