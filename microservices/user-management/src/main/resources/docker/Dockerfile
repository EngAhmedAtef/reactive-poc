FROM openjdk:17-alpine

ARG PORT

RUN addgroup -s css && adduser usermanagement -g css

WORKDIR /home/usermanagement/app
RUN mkdir logs
RUN chown -R usermanagement:css .

USER usermanagement:css
COPY ./user-management-service.jar .
COPY ./entrypoint.sh .

USER root
RUN chmod +x ./entrypoint.sh

USER usermanagement:css
EXPOSE $PORT
ENTRYPOINT ["sh", "./entrypoint.sh"]
