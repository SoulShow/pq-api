FROM java

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN mkdir -p /export/data/docker \
    && mkdir -p /export/data/logs/jedi-api
WORKDIR /export/data/docker
COPY . /export/data/docker
CMD ["java","-jar","-Xms64m","-Xmx128m","pq-api.jar"]