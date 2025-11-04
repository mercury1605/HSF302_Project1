# Giai đoạn 1: Build ứng dụng
# Sử dụng image Java 21 (giống như trong pom.xml của bạn) để build
FROM eclipse-temurin:21-jdk-alpine AS build

# Đặt thư mục làm việc bên trong container
WORKDIR /app

# Copy toàn bộ mã nguồn vào
COPY . .

# THÊM DÒNG NÀY: Cấp quyền thực thi cho mvnw
RUN chmod +x ./mvnw

# Build dự án và bỏ qua test để build nhanh hơn
# (Chúng ta dùng mvnw vì nó đi kèm dự án Spring Boot)
RUN ./mvnw clean install -DskipTests

# Giai đoạn 2: Chạy ứng dụng
# Sử dụng image JRE (nhẹ hơn) để chạy
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy file .jar đã được build từ giai đoạn 1
# Tên file jar này lấy từ pom.xml của bạn
COPY --from=build /app/target/HSF302_1-0.0.1-SNAPSHOT.jar app.jar

# Cổng mà Spring Boot (Tomcat) sẽ chạy
# File application.properties của bạn đã có: server.port=${PORT:8080}
# Render sẽ tự động gán biến PORT, nên cấu hình này đã hoàn hảo
EXPOSE 8080

# Lệnh để khởi động ứng dụng
CMD ["java", "-jar", "app.jar"]