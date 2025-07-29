# 基础镜像
FROM openjdk:11.0-jre-buster
# FROM openjdk:17-jdk-alpine
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 拷贝jar包
COPY digital-nursing-admin.jar /app.jar
# 入口
# ENTRYPOINT ["java", "-jar", "/app.jar"]

# 创建启动脚本
RUN echo '#!/bin/bash\n\
if [ -f /etc/profile.d/env.sh ]; then\n\
  source /etc/profile.d/env.sh\n\
fi\n\
exec java -jar /app.jar\n' > /entrypoint.sh && chmod +x /entrypoint.sh

# 入口点
ENTRYPOINT ["/entrypoint.sh"]