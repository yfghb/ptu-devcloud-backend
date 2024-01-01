# jre
FROM openjdk:8-jre-alpine

# author
MAINTAINER yangfan

# 接受传参
ENV JAVA_OPIS ""

# 添加jar包到容器中(注意！请先执行shell拷贝jar包到docker目录下)
ADD system-boot-1.0-SNAPSHOT.jar /home/app.jar

EXPOSE 9090

CMD ["sh", "-c", "echo \"****** 运行命令: java -jar ${JAVA_OPIS} /home/app.jar\" & java -jar ${JAVA_OPIS} /home/app.jar"]