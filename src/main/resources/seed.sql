-- Run this after first startup to create an admin user
-- Password below is BCrypt hash of: Admin@123

INSERT INTO users (email, password, role)
VALUES ('admin@placemate.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN')
ON DUPLICATE KEY UPDATE email = email;
