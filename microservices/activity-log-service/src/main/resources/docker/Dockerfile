FROM openjdk:17-alpine

ARG PORT
ARG MODULE_NAME

RUN adduser -S activitylogservice -G css

WORKDIR /home/activitylogservice/app
RUN mkdir logs
RUN chown -R activitylogservice:css .

USER activitylogservice:css
COPY ./activity-log-service.jar .
COPY ./entrypoint.sh .

USER root
RUN chmod +x ./entrypoint.sh

USER activitylogservice:css
EXPOSE $PORT
ENTRYPOINT ["sh", "./entrypoint.sh"]
