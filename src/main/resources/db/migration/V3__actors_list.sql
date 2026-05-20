ALTER TABLE movies
    ALTER COLUMN actors TYPE TEXT[]
        USING string_to_array(actors, ', ');