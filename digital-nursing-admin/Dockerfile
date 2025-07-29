# 基础镜像
FROM openjdk:11.0-jre-buster
# FROM openjdk:17-jdk-alpine
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#
ENV OSS_BUCKET_NAME $OSS_BUCKET_NAME
ENV OSS_ENDPOINT $OSS_ENDPOINT
ENV OSS_REGION $OSS_REGION
ENV OSS_ACCESS_KEY_ID $OSS_ACCESS_KEY_ID
ENV OSS_ACCESS_KEY_SECRET $OSS_ACCESS_KEY_SECRET
ENV BAIDU_KEY $BAIDU_KEY
ENV GAODE_KEY $GAODE_KEY
ENV BAIDU_QIANFAN_API_KEY $BAIDU_QIANFAN_API_KEY

# 拷贝jar包
COPY digital-nursing-admin.jar /app.jar
# 入口
ENTRYPOINT ["java", "-jar", "/app.jar"]