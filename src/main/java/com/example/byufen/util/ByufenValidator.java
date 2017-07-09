package com.example.byufen.util;

import java.util.Objects;

/**
 * Created by cejon on 7/7/2017.
 */
public class ByufenValidator {
    private static ByufenValidator ourInstance = new ByufenValidator();

    private ByufenValidator() {
    }

    public static ByufenValidator getInstance() {
        return ourInstance;
    }

    public void validate(String fen) throws ByufenException {
        String[] dataElements = fen.split(" ");
        if (dataElements.length != 6) {
            throw new ByufenException("There are not exactly 6 elements in the fen string");
        }
        //1. Validate piece placement data element
        validatePiecePlacement(dataElements[0]);
        //2. Validate active color element
        if (!dataElements[1].equals("b") && !dataElements[1].equals("w")) {
            throw new ByufenException("The active color element is invalid");
        }
        //3. Validate castling availability element
        validateCastlingAvailability(dataElements[2]);
        //4. Validate en passant target square element
        validateEnPassantTargetSquare(dataElements[3], dataElements[1]);
        //5. Validate halfmove clock element
        if (!dataElements[4].matches("^[0-9]+$")) {
            throw new ByufenException("The halfmove clock element is invalid");
        }
        //6. Validate fullmove number
        if (!dataElements[5].matches("^[0-9]+$")) {
            throw new ByufenException("The fullmove number element is invalid");
        }
    }

    private void validateEnPassantTargetSquare(String targetSquare, String activeColor) throws ByufenException {
        if (Objects.equals(activeColor, "w") && targetSquare.matches("^[a-h][6]$")) {
            return;
        } else if (Objects.equals(activeColor, "b") && targetSquare.matches("^[a-h][3]$")) {
            return;
        } else if (targetSquare.equals("-")) {
            return;
        }
        throw new ByufenException("The en passant target square element is invalid");
    }

    private void validateCastlingAvailability(String dataElement) throws ByufenException {
        if (dataElement.equals("-")) {
            return;
        } else if (dataElement.length() > 0 && dataElement.matches("^K?Q?k?q?$")) {
            return;
        }
        throw new ByufenException("The castling availability element is invalid");
    }

    private void validatePiecePlacement(String dataElement) throws ByufenException {
        if (!dataElement.matches("^[12345678PpNnBbRrQqKk/]+$")) {
            throw new ByufenException("There are invalid characters in the piece placement data element");
        }
        String[] boardRows = dataElement.split("/");
        if (boardRows.length != 8) {
            throw new ByufenException("There are not exactly 8 rows in the piece placement data element");
        }
        for (String boardRow : boardRows) {
            int cellsLeft = 8;
            for (int j = 0; j < boardRow.length(); j++) {
                char c = boardRow.charAt(j);
                if (c >= '1' && c <= '8') {
                    int iters = Character.getNumericValue(c);
                    for (int k = 0; k < iters; k++) {
                        cellsLeft -= 1;
                    }
                } else {
                    cellsLeft -= 1;
                }
            }
            if (cellsLeft != 0) {
                throw new ByufenException("There are not exactly 8 cells in one or more of the piece placement data rows");
            }
        }
    }
}
