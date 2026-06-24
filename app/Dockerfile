FROM gradle:8.12.1-jdk21

WORKDIR /app

COPY . .

RUN gradle installDist --no-daemon

CMD ["./build/install/app/bin/app"]