FROM gradle:8.12.1-jdk

WORKDIR / app

COPY /app .

RUN gradle installDist

CMD ./build/install/app/bin/app