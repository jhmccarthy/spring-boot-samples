FROM openjdk:13
MAINTAINER Jim McCarthy

# Label Schema - http://label-schema.org/rc1/ 
ARG BUILD_DATE=unspecified
ARG BUILD_VERSION=unspecified
ARG GIT_COMMIT_ID=unspecified

LABEL org.label-schema.schema-version = "1.0" \
	  org.label-schema.name="springBoot" \
	  org.label-schema.build-date=$BUILD_DATE \
	  org.label-schema.vcs-ref=$GIT_COMMIT_ID \
	  org.label-schema.version=$BUILD_VERSION
# End Label Schema

# Copy the build artifact into the Docker image
COPY ./target/springBoot.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
