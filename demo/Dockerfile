FROM tomcat:8.0
ENV myKey
ENV mySecret

#ADD ./webapp/target/*.war /usr/local/tomcat/webapps/
ADD demo/target/*.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]