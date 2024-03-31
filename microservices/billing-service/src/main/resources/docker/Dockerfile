FROM openjdk:17-alpine

ARG PORT
ARG MODULE_NAME

RUN addgroup -f css && adduser billingservice -g css

WORKDIR /home/billingservice/app
RUN mkdir logs
RUN chown -R billingservice:css .

USER billingservice:css
COPY ./billing-service.jar .
COPY ./entrypoint.sh .

USER root
RUN chmod +x ./entrypoint.sh

USER billingservice:css
EXPOSE $PORT
ENTRYPOINT ["sh", "./entrypoint.sh"]
