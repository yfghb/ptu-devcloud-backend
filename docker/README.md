## docker命令



```shell
# 打包镜像 -f:指定Dockerfile文件路径 --no-cache:不使用缓存
docker build -f Dockerfile --build-arg JAVA_OPTS="-XX:+UseG1GC" -t "标签名" . --no-cache

# 推送镜像
docker push 远程仓库

# 拉取镜像
docker pull 远程仓库

# 运行
docker run -d -p 8080:8080 --name appname

# 删除旧容器
docker ps -a | grep appname | grep dev | awk `{print $1}` | xargs -I docker stop {} | xargs -I docker rm {}

# 删除旧镜像
docker images | grep -E appname | grep dev | awk `{print $3}` | uniq | xargs -I {} docker rmi --force {}

```



