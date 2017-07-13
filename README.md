# byufen
Solution to the BYU FEN coding challenge (http://files.lib.byu.edu/webdev-hire/instructions/)

## Instructions
1) ``mvn clean`` 
2) ``mvn install``
3) ``mvn test`` for testing
4) ``mvn spring-boot:run``

## Endpoints
Running ``mvn spring-boot:run`` will spin up the application on ``localhost:8081``. All endpoints are accessed with the HTTP ``GET`` request.
* ``\ping`` - A sanity endpoint to ensure that the server is up and running
* ``\validate?fen=[FEN]`` - An endpoint to check the validity of the FEN string passed in as a query parameter
* ``\task1?fen=[FEN]`` - Prints out a board based on the provided FEN string. The FEN string must be valid or an error message will display instead
* ``\task2?fen=[FEN]`` - Calls the syzygy.info API and updates the FEN string based on the suggested move. If a ``bestmove`` is specified, it will be used instead. This endpoint handles castling, en passant, and pawn promotion.
* ``\stretch?fen=[FEN]`` - Prints out a board of an updated FEN, a combination of task 1 and task 2

## Why Spring Boot?
Most Java-based services use Java EE or some sort of framework. I chose Spring because it was the Java framework I was most familiar with. This also allows me to showcase my skills with developing a RESTful service.

## How can I improve this?
* These are the steps to move a piece and update the FEN representation
    * Convert current FEN piece data representation to a 64 character string, replacing numbers with that number of dashes:
        * e.g. ``rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR`` becomes ``rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR``
    * Determine the "from" square and the "to" square.
        * e.g. ``a2a3``. ``a2`` is the from square. ``a3`` is the to square.
    * Calculate the character positions of the given squares
        * character position = 8 * (8 - row number) + column number, where a = 0 -> h = 7
        * ``a2`` = 48, ``a3`` = 40
    * Set the to square = character in from square
    * Set the from square character to '-'
    * Convert the 64 character string representation back to FEN notation
I would like to be able to work out a solution that didn't affect the entire board; just the rows that are affected by the move
* Only the piece placement data is updated. Improvements could include changing the active color each move, remove castling ability flags if the king or rooks move without castling, detect if a pawn moves in such a way that it can be captured by en passant, etc...
* As part of the FEN validation process, I could check to see if there are too many pieces of one type, such as checking to ensure that each side has only one king.
* Ensure moves are valid before completing the movement action. This would include
    * Check to see if the "from" square is within reach of the piece in the "to" square.
    * Check to make sure that a move doesn't put the active player's king in check