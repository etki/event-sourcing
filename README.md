# Event sourcing framework for Java 8+

[![Bintray](https://img.shields.io/bintray/v/etki/maven/event-sourcing.svg?style=flat-square)](https://bintray.com/etki/maven/event-sourcing)
[![CircleCI/master](https://img.shields.io/circleci/project/github/etki/java-event-sourcing/master.svg?style=flat-square)](https://circleci.com/gh/etki/java-event-sourcing/tree/master)
[![Coveralls/master](https://img.shields.io/coveralls/github/etki/java-event-sourcing/master.svg?style=flat-square)](https://coveralls.io/github/etki/java-event-sourcing?branch=master)
[![Scrutinizer/master](https://img.shields.io/scrutinizer/g/etki/java-event-sourcing/master.svg?style=flat-square)](https://scrutinizer-ci.com/g/etki/java-event-sourcing/?branch=master)
[![Code Climate](https://img.shields.io/codeclimate/github/etki/java-event-sourcing.svg?style=flat-square)](https://codeclimate.com/github/etki/java-event-sourcing)

## What does this project do?

This project is aimed to provide simple and easy event sourcing pattern
integration in any project that needs it. Unlike other implementations,
it tries to abstract heavily of specific storage and classes, and you 
should be able to implement your own adapter for your favourite data
store without hassle.

## Installation

### Maven

```xml
<dependencies>
  <dependency>
    <groupId>me.etki</groupId>
    <artifactId>event-sourcing-bundle</artifactId>
    <version>0.1.0</version>  
  </dependency>
</dependencies>
```

### Gradle

```groovy
dependencies {
  compile group: 'me.etki', name: 'event-sourcing-bundle', version: '0.1.0'
}
```

## Motivation

While there is quite a number of other implementations around, they 
often don't account for some challenges involved:

- Most of the implementations just store event class FQCN to identify
it during the restore process. This is as good as hardcoding FQCNs 
anywhere, because it prevents class renaming and migration, forcing you
to keep your errors and typos with you forever. ES framework should 
provide an intermediate mapping layer that would store identifiers
rather than full classes names, resolving them at run time.
- Events tend to evolve over time, with fields being added, removed or 
dropped. Such mutation either results in adding more events or trying 
to handle both older and newer structures deserialization in one 
place - and that often ends bad. Instead, ES framework should provide
structure versioning from the very beginning (it should understand that
`created` v.1 and `created` v.2 are different events), and, if 
possible, perform read repairs to constantly update backing store and 
keep things up-to-date (so older classes could eventually be thrown 
out). It may seem that this contradicts with ES immutability, but in 
fact this does not rewrite history, only keeps data containers in sync.
- Many implementations (and even Martin himself) tie events to 
processing logic. I personally think that it is wrong and events should
only be used to reconstruct history, those who really do processing 
logic are commands, and those who take action depending on entity state
are just some arbitrary processors.
- Most of implementations are tightly tied to some strange 
assumptions - e.g. that entity ID should be UUID or bind themselves to
specific storage like PostgreSQL. ES should not care about storage 
level at all because of it's query simplicity - it should just use some
externally-built interface implementation that would allow to store and
extract serialized events and snapshots. Serializer itself should be
configurable as well, so protobuf lovers would use protobuf, json 
lovers would use json.
- Last but not least, entity should not know anything about events. 
Most implementations i saw are placing handling logic directly inside
entity class, but this goes somewhat crazy as soon as you have tens of 
events for single entity - event processing logic should be placed 
inside events themselves to prevent bloat. This really does contradict
anti-anemic model approach, but it just how things work; if you really
do want to handle things from inside the entity in this framework, you 
can add helper method that would produce mutations objects to store
them later.

So, because of my inner perfectionist and common 'i-do-know-better' 
cognitive bias i thought i could write something like this.

## 300 seconds start

TBD

## Model

TBD

## Components

### event-sourcing-core

The framework itself without any backing implementations. You won't 
need anything else if you're ready to provide it with serializer and
storage implementations.

### event-sourcing-discovery

Set of tools for discovering events included in application. It's 
pretty boring to register them by hand, right?

### event-sourcing-jackson-serializer

Dead simple JSON serializer implementation.

### event-sourcing-bundle

Just a transition dependency that depends on framework itself with most
common backing implementations (jackson serializer).

## FAQ

### Is there anything CQRSish inside?

No, this project is not aimed for direct CQRS support. There are some 
caveats i'm not ready to handle.

## Contributing

Feel free to post issues and/or send pull requests for **dev** branch.

### Dev branch state

[![CircleCI/dev](https://img.shields.io/circleci/project/github/etki/java-event-sourcing/dev.svg?style=flat-square)](https://circleci.com/gh/etki/java-event-sourcing/tree/dev)
[![Coveralls/dev](https://img.shields.io/coveralls/github/etki/java-event-sourcing/dev.svg?style=flat-square)](https://coveralls.io/github/etki/java-event-sourcing?branch=dev)
[![Scrutinizer/dev](https://img.shields.io/scrutinizer/g/etki/java-event-sourcing/dev.svg?style=flat-square)](https://scrutinizer-ci.com/g/etki/java-event-sourcing/?branch=dev)

## Licensing

MIT / Hi, my name is Etki / 2018
