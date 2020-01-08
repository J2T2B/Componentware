FROM jboss/wildfly

RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent
ADD ./build/libs/server* /opt/jboss/wildfly/standalone/deployments/