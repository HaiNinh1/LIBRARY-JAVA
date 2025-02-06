CREATE TABLE books (
                       book_code VARCHAR(50) PRIMARY KEY, -- Unique identifier for each book
                       book_name VARCHAR(255) NOT NULL,  -- Name of the book
                       total_quantity INT NOT NULL,      -- Total number of books in the inventory
                       borrowed_quantity INT NOT NULL DEFAULT 0 -- Number of books currently borrowed
);
CREATE TABLE borrowed_books (
                                id SERIAL PRIMARY KEY,            -- Unique identifier for each borrowing record
                                book_code VARCHAR(50) REFERENCES books(book_code) ON DELETE CASCADE, -- Foreign key to books table
                                borrower_name VARCHAR(255) NOT NULL, -- Name of the borrower
                                borrowed_quantity INT NOT NULL,      -- Quantity of the book borrowed
                                borrowed_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Timestamp of borrowing
);

INSERT INTO books (book_code, book_name, total_quantity, borrowed_quantity)
VALUES
    ('B001', 'Lập trình C', 10, 2),
    ('B002', 'Java cơ bản', 15, 5),
    ('B003', 'Python nâng cao', 20, 8);

INSERT INTO borrowed_books (book_code, borrower_name, borrowed_quantity)
VALUES
    ('B001', 'Nguyễn Văn Khoai', 1),
    ('B002', 'Trần Thị Hoa', 2),
    ('B003', 'Lê Minh Tú', 3);

CREATE TABLE account (
                         id SERIAL PRIMARY KEY,          -- Auto-incrementing primary key
                         email VARCHAR(255) UNIQUE,       -- Unique email address
                         password VARCHAR(255) NOT NULL,  -- User password (should be hashed in production)
                         type INTEGER NOT NULL,           -- Type (e.g., 0 = Admin, 1 = Librarian, 2 = Student)
                         lock_status BOOLEAN NOT NULL DEFAULT FALSE -- Account lock status (true = locked, false = active)
);


INSERT INTO account (id, email, password, type, lock_status) VALUES
                                                                 (1, 'user1@example.com', 'hashed_password_1', 1, FALSE),
                                                                 (2, 'user2@example.com', 'hashed_password_2', 2, FALSE),
                                                                 (3, 'admin@example.com', 'hashed_password_3', 0, FALSE),
                                                                 (4, 'haininh@example.com', '123', 0, TRUE),
                                                                 (5, 'haininh1@example.com', '1234', 0, FALSE),
                                                                 (6, 'haininh213', '12', 0, FALSE),
                                                                 (7, 'qwe', '123', 0, FALSE),
                                                                 (8, 'qqq', '123', 2, TRUE),
                                                                 (9, 'lll', '123', 1, FALSE),
                                                                 (10, 'sss', '123', 2, FALSE);
