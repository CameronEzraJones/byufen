package com.example.byufen.util;

/**
 * Created by cejon on 6/30/2017.
 */
public class ByufenPrinter {
    public String printBoard(String fen) throws ByufenException {
        ByufenValidator.getInstance().validate(fen);
        String[] fenDataElements = fen.split(" ");
        String piecePlacement = fenDataElements[0];
        String[] boardRows = piecePlacement.split("/");
        StringBuilder sb = new StringBuilder("  ---------------------------------\n");
        for(int i = 8; i >= 1; i--) {
            String boardRow = boardRows[8 - i];
            sb.append(i).append(" |");
            int cellsLeft = 8;
            for(int j = 0; j < boardRow.length(); j++) {
                char c = boardRow.charAt(j);
                if(c >= '1' && c <= '8') {
                    int iters = Character.getNumericValue(c);
                    for(int k = 0; k < iters; k++) {
                        sb.append("   |");
                        cellsLeft -= 1;
                    }
                } else {
                    sb.append(" ").append(c).append(" |");
                    cellsLeft -= 1;
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
