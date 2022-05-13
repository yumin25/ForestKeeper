# openjdk:11-jre-slim 이미지를 기본으로 사용
FROM openjdk:11-jre-slim

# jar 파일이 복사되고, docker image 를 구성하면서 명령어를 실행하며 지지고 볶을 디렉토리 설정
ENV APP_HOME=/usr/app/

ENV GOOGLE_APPLICATION_CREDENTIALS=/usr/app/google_key.json

# 해당 디렉토리에서 작업을 시작하겠다는 구문
WORKDIR $APP_HOME

# jar 파일을 복사
COPY build/libs/forestkeeper-0.0.1-SNAPSHOT.jar application.jar

COPY build/resources/main/google_key.json google_key.json

# 어플리케이션 포트
EXPOSE 8443

CMD ["java", "-jar", "application.jar"]
