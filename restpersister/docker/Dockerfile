FROM tomcat:9.0.7-jre9

RUN echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" >  $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<tomcat-users>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<role rolename=\"tomcat\"/>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<role rolename=\"role1\"/>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<user username=\"tomcat\" password=\"tomcat\" roles=\"tomcat\"/>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<user username=\"both\" password=\"both\" roles=\"tomcat,role1\"/>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<user username=\"role1\" password=\"role1\" roles=\"role1\"/>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<user username=\"admin\" password=\"admin\" roles=\"tomcat,manager-gui,manager-script,manager-jmx,manager-status\"/>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "</tomcat-users>" >> $CATALINA_HOME/conf/tomcat-users.xml
RUN echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" >  $CATALINA_HOME/webapps/manager/META-INF/context.xml
RUN echo "<Context antiResourceLocking=\"false\" privileged=\"true\"/>" >>  $CATALINA_HOME/webapps/manager/META-INF/context.xml

RUN sed -i -e 's/connectionTimeout="2000" /connectionTimeout="-1" asyncTimeout="-1"/g' $CATALINA_HOME/conf/server.xml

COPY restpersister.war $CATALINA_HOME/webapps/restpersister.war







