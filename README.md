# HSF302_1 - Quản lý Sinh viên và Phòng ban 🚀

Đây là một dự án Spring Boot đơn giản để quản lý sinh viên và các phòng ban.

## 🌐 Live Demo

Dự án đã được triển khai trên Render và có thể truy cập trực tiếp tại địa chỉ:
**[https://hsf302-project1.onrender.com/](https://hsf302-project1.onrender.com/)**

## 🛠️ Công nghệ sử dụng

*   **Java** ☕
*   **Spring Boot** 🌱 - Framework chính để xây dựng ứng dụng.
*   **Spring Data JPA** 🗃️ - Để tương tác với cơ sở dữ liệu.
*   **Thymeleaf** 🍃 - Template engine để tạo các trang HTML.
*   **Maven** 📦 - Công cụ quản lý dependency và build dự án.

## ⚙️ Cách chạy dự án

1.  **Clone repository:**
    ```bash
    git clone https://github.com/mercury1605/HSF302_Project1
    cd HSF302_Project1
    ```

2.  **Build và chạy ứng dụng bằng Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```
    Hoặc chạy từ IDE của bạn bằng cách mở file `Hsf3021Application.java` và chạy nó.

3.  **Truy cập ứng dụng (Local):**
    Mở trình duyệt và truy cập `http://localhost:8080`.

## 📁 Cấu trúc dự án

*   `src/main/java`: Chứa mã nguồn Java của ứng dụng.
    *   `config`: Cấu hình ứng dụng (Web, Security,...).
    *   `controller`: Xử lý các request HTTP.
    *   `entity`: Các đối tượng Java tương ứng với các bảng trong cơ sở dữ liệu.
    *   `repository`: Giao diện để tương tác với cơ sở dữ liệu.
    *   `service`: Chứa business logic.
*   `src/main/resources`: Chứa các file tài nguyên.
    *   `static`: Chứa các file tĩnh (CSS, JavaScript, hình ảnh).
    *   `templates`: Chứa các file template (HTML).
    *   `application.properties`: File cấu hình chính của Spring Boot.
*   `pom.xml`: File cấu hình của Maven, định nghĩa các dependency và cách build dự án.


## 🧾 Tài khoản test
| Role | username     | Password                       |
| :-------- | :------- | :-------------------------------- |
| `Manager`      |manager1 |manager1 |
| `Manager`      | manager2 |manager2 |
| `Staff`      | staff1 |staff2 |
| `Guest`      | guest |guest |

