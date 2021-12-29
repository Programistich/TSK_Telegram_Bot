CREATE TABLE IF NOT EXISTS chats
(
    chat_id VARCHAR(512) PRIMARY KEY,
    chat_current_car INT,
    chat_cars TEXT,
    chat_message_id INT
);

CREATE TABLE IF NOT EXISTS cars(
    car_id  SERIAL PRIMARY KEY,
    car_link VARCHAR(200),
    car_name VARCHAR(200),
    car_status VARCHAR(200),
    car_price INT,
    car_image TEXT,
    car_model VARCHAR(200),
    car_year INT,
    car_mileage INT,
    car_drive_unit VARCHAR(100),
    car_suspension VARCHAR(200),
    car_range VARCHAR(200),
    car_city VARCHAR(200)
);