
CREATE TABLE SudokuBoardDB (
                               id SERIAL PRIMARY KEY,
                               name TEXT NOT NULL UNIQUE
);

CREATE TABLE SudokuFieldDB (
                               id SERIAL PRIMARY KEY,
                               value INTEGER NOT NULL CHECK (value >= 0 AND value <= 9),
                               index INTEGER NOT NULL CHECK (index >= 0 AND index < 81),
                               editable BOOLEAN NOT NULL,
                               board_id INTEGER NOT NULL,
                               FOREIGN KEY (board_id) REFERENCES SudokuBoardDB(id) ON DELETE CASCADE,
                               UNIQUE (index, board_id)
);
