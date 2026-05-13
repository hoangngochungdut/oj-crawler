# oj-crawler

# Setup Project

## 1. Cài đặt phần mềm cần thiết
- Java JDK 17+
- Maven
- MySQL Server
- MySQL Workbench

---

## 2. Tạo database
- Mở MySQL Workbench
- Chọn:
    File -> Open SQL Script
- Mở file `schema.sql`
- Nhấn Execute để tạo database

---

## 3. Clone project
git clone https://github.com/hoangngochungdut/oj-crawler

---

## 4. Tải Edge Driver
Tải Edge Driver phù hợp với phiên bản Microsoft Edge:

https://developer.microsoft.com/vi-vn/microsoft-edge/tools/webdriver/

---

## 5. Cài đặt Edge Driver
- Giải nén file .zip
- Copy `msedgedriver.exe`
- Dán vào thư mục project

Ví dụ:
oj-crawler/msedgedriver.exe

---

## 6. Cấu hình database
Mở file DbConnection.java và chỉnh:

- database url
- username
- password

---

## 7. Chạy project
mvn clean package

hoặc chạy trực tiếp bằng IDE.