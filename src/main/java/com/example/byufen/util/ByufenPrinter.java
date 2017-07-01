package com.example.byufen.util;

import java.util.regex.Pattern;

/**
 * Created by cejon on 6/30/2017.
 */
public class ByufenPrinter {
    public String printBoard(String fen) throws ByufenException {
        String[] fenDataElements = fen.split(" ");
        if(fenDataElements.length != 6) {
            throw new ByufenException("This is an invalid FEN string");
        }
        String piecePlacement = fenDataElements[0];
        if(piecePlacement.matches("[^12345678PpNnBbRrQqKk/]")) {
            throw new ByufenException("There are invalid characters in the piece placement data element");
        }
        String[] boardRows = piecePlacement.split("/");
        if(boardRows.length != 8) {
            throw new ByufenException("The board is formatted incorrectly");
        }
        StringBuilder sb = new StringBuilder("  ---------------------------------\n");
        for(int i = 8; i >= 1; i--) {
            String boardRow = boardRows[8 - i];
            sb.append(i).append(" |");
            for(int j = 0; j < boardRow.length(); j++) {
                char c = boardRow.charAt(j);
                if(c >= '1' && c <= '8') {
                    int iters = Character.getNumericValue(c);
                    for(int k = 0; k < iters; k++) {
                        sb.append("   |");
                    }
                } else {
                    sb.append(" ").append(c).append(" |");
                }
            }
            if(i != 1) {
                sb.append("\n  |-------------------------------|\n");
            } else {
                sb.append("\n  ---------------------------------\n");
            }
        }
        sb.append("    a   b   c   d   e   f   g   h");
        return sb.toString();
    }
}
