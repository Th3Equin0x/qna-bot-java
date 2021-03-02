FROM openjdk:11

#copies the contents of the first argument and adds them to the container's filesystem at the second argument
COPY . /usr/src/myapp
#Sets the working directory of the application to the argument
WORKDIR /usr/src/myapp

ARG qnabot_token
ENV qnabot_token=$qnabot_token

#Creates a local build and runs as a program.
RUN ./gradlew build
CMD ["./gradlew", "run"]