FROM openjdk:17-alpine

ARG PORT

RUN addgroup -f css && adduser notificationmanagement -g css

WORKDIR /home/notificationmanagement/app
RUN mkdir logs
RUN chown -R notificationmanagement:css .

USER notificationmanagement:css
COPY ./notification-management-service.jar .
COPY ./entrypoint.sh .

USER root
RUN chmod +x ./entrypoint.sh

USER notificationmanagement:css
EXPOSE $PORT
ENTRYPOINT ["sh", "./entrypoint.sh"]
