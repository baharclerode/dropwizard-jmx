# Dropwizard JMX Integration [![Build Status](https://jenkins.dragon.zone/buildStatus/icon?job=dragonzone/dropwizard-jmx/master)](https://jenkins.dragon.zone/blue/organizations/jenkins/dragonzone%2Fdropwizard-jmx/activity?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/zone.dragon.dropwizard/dropwizard-jmx/badge.svg)](https://maven-badges.herokuapp.com/maven-central/zone.dragon.dropwizard/dropwizard-jmx/) [![Javadoc](http://javadoc-badge.appspot.com/zone.dragon.dropwizard/dropwizard-jmx.svg)](http://www.javadoc.io/doc/zone.dragon.dropwizard/dropwizard-jmx)

This bundle enables JMX features for a dropwizard application. Currently supported implementations are

* `rmi` - The standard java JMX-RMI agent, used by VisualVM and other tools to connect directly to remote management
* `jminix` - A HTML interface and RESTful API for interacting with the MBeans registered via JMX
* `jolokia` - A JSON REST API for interacting with the MBeans registered via JMX

To use this bundle, add it to your application in the initialize method:

    @Override
    public void initialize(Bootstrap<YourConfig> bootstrap) {
        bootstrap.addBundle(new JmxBundle<>(YourConfig::getJmxConfiguration));
    }

Then, add configuration to tell the bundle which connectors to initialize and how to validate clients connecting to JMX:

    jmx:
        authenticator:
            type: simpleUserPassword
            username: yourUsername
            password: yourPassword
         connectors:
           - type: rmi
             port: 9000
           - type: jolokia
             path: jolokia
           - type: jminix
             path: jminix


## TODO

* Add MX4J support
* Make authenticator control non-RMI connectors