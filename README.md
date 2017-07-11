# byufen
Solution to the BYU FEN coding challenge (http://files.lib.byu.edu/webdev-hire/instructions/)

## Instructions
1) ``mvn clean`` 
2) ``mvn install``
3) ``java -jar ./target/byufen-0.0.1-SNAPSHOT.jar``
4) ``mvn test`` for testing

## Endpoints
* ``\ping`` - A sanity endpoint to ensure that the server is up and running
* ``\validate?fen=[FEN]`` - An endpoint to check the validity of the FEN string passed in as a query parameter
* ``\task1?fen=[FEN]`` - Prints out a board based on the provided FEN string. The FEN string must be valid or an error message will display instead
* ``\task2?fen=[FEN]`` - Calls the syzygy.info API and updates the FEN string based on the suggested move. If a ``bestmove`` is specified, it will be used instead
* ``\stretch?fen=[FEN]`` - Prints out a board of an updated FEN, a combination of task 1 and task 2