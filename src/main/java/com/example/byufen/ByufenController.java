package com.example.byufen;

import com.example.byufen.util.ByufenException;
import com.example.byufen.util.ByufenPrinter;
import com.example.byufen.util.ByufenURLWrapper;
import com.example.byufen.util.ByufenValidator;
import com.google.common.base.CharMatcher;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by cejon on 6/30/2017.
 */
@RestController
public class ByufenController {
    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public String ping() {
        return "OK!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public ResponseEntity<String> validate(@RequestParam("fen") String fen) {
        try {
            ByufenValidator.getInstance().validate(fen);
            return ResponseEntity.status(HttpStatus.OK).body("This is a valid fen string");
        } catch (ByufenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/task1")
    public ResponseEntity<String> task1(@RequestParam("fen") String fen) {
        try {
            String board = new ByufenPrinter().printBoard(fen);
            return ResponseEntity.status(HttpStatus.OK).body(board);
        } catch (ByufenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/task2")
    public ResponseEntity<String> task2(@RequestParam("fen") String fen) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(getSuggestedMove(fen));
        } catch (IOException | ByufenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/stretch")
    public ResponseEntity<String> stretchGoal(@RequestParam("fen") String fen) {
        try {
            String updatedFen = getSuggestedMove(fen);
            String board = new ByufenPrinter().printBoard(updatedFen);
            return ResponseEntity.status(HttpStatus.OK).body("" + "Updated FEN: " + updatedFen + "\n\n" + board);
        } catch (IOException | ByufenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    private String getSuggestedMove(String fen) throws IOException, ByufenException {
        ByufenValidator.getInstance().validate(fen);
        ByufenURLWrapper fenService = establishURLConnection(fen);
        String data = readDataFromURLConnection(fenService);
        JsonObject object = new JsonParser().parse(data).getAsJsonObject();
        String suggestedMove = determineSuggestedMove(object);
        if (!suggestedMove.matches("^[a-h][1-8][a-h][1-8][nbrq]?")) {
            throw new ByufenException("The suggested move was formatted incorrectly");
        }
        return movePiece(fen, suggestedMove);
    }

    private String movePiece(String fen, String suggestedMove) {
        String[] dataElements = fen.split(" ");
        String piecePlacementData = dataElements[0];
        char[] expandedPPD = generateExpandedPiecePlacementData(piecePlacementData).toCharArray();
        int fromSquare = determineSquare(suggestedMove.substring(0, 2));
        int toSquare = determineSquare(suggestedMove.substring(2, 4));
        char pieceAtFromSquare = expandedPPD[fromSquare];
        char pieceAtToSquare = expandedPPD[toSquare];
        expandedPPD[toSquare] = expandedPPD[fromSquare];
        expandedPPD[fromSquare] = '-';
        if (isCastlingMove(fromSquare, toSquare, pieceAtFromSquare)) {
            handleCastle(fromSquare, toSquare, expandedPPD);
        }
        if (!dataElements[3].equals("-") && determineSquare(dataElements[3]) == toSquare /*En Passant*/) {
            handleEnPassant(fromSquare, toSquare, expandedPPD);
        }
        if (suggestedMove.length() == 5 /*Pawn promotion*/) {
            expandedPPD[toSquare] = dataElements[1].equals("w") ? Character.toUpperCase(suggestedMove.charAt(4))
                    : suggestedMove.charAt(4);
        }
        dataElements[0] = generateFenPiecePlacementData(String.valueOf(expandedPPD));
        return String.join(" ", dataElements);
    }

    private void handleEnPassant(int fromSquare, int toSquare, char[] expandedPPD) {
        if (toSquare >= 40 && toSquare <= 47) {
            expandedPPD[toSquare - 8] = '-';
        } else if (toSquare >= 16 && toSquare <= 23) {
            expandedPPD[toSquare + 8] = '-';
        }
    }

    private void handleCastle(int fromSquare, int toSquare, char[] expandedPPD) {
        // e1 - g1, white kingside castling
        if (toSquare == 62) {
            expandedPPD[63] = '-';
            expandedPPD[61] = 'R';
        }
        // e1 - c1, white queenside castling
        else if (toSquare == 58) {
            expandedPPD[56] = '-';
            expandedPPD[59] = 'R';
        }
        // e8 - g8, black kingside castling
        else if (toSquare == 6) {
            expandedPPD[7] = '-';
            expandedPPD[5] = 'r';
        }
        // e8 - c8, black queenside castling
        else if (toSquare == 2) {
            expandedPPD[0] = '-';
            expandedPPD[3] = 'r';
        }
    }

    private boolean isCastlingMove(int fromSquare, int toSquare, char pieceAtFromSquare) {
        if (pieceAtFromSquare == 'K' && fromSquare == 60 /*e1*/) {
            return (toSquare == 62 || toSquare == 58);
        } else if (pieceAtFromSquare == 'k' && fromSquare == 4 /*e8*/) {
            return (toSquare == 6 || toSquare == 2);
        }
        return false;
    }

    private String generateFenPiecePlacementData(String expandedPPD) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 8; i++) {
            String row = expandedPPD.substring(8 * i, 8 * i + 8);
            for (int j = 8; j >= 1; j--) {
                row = row.replace(String.valueOf(new char[j]).replace('\0', '-'), Integer.toString(j));
            }
            sb.append(row);
            if (i != 7) {
                sb.append("/");
            }
        }
        return sb.toString();
    }

    private String generateExpandedPiecePlacementData(String piecePlacementData) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < piecePlacementData.length(); i++) {
            char c = piecePlacementData.charAt(i);
            if (c >= '1' && c <= '8') {
                int numEmptySpaces = Character.getNumericValue(c);
                sb.append(String.valueOf(new char[numEmptySpaces]).replace('\0', '-'));
            } else if ("PpNnBbRrQqKk".contains(Character.toString(c))) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private int determineSquare(String squareCoords) {
        return (8 - (squareCoords.charAt(1) - '0')) * 8 + (squareCoords.charAt(0) - 'a');
    }

    private String determineSuggestedMove(JsonObject object) throws ByufenException {
        if (object.has("moves") && object.getAsJsonObject("moves") != null) {
            Set<Map.Entry<String, JsonElement>> entries = object.getAsJsonObject("moves").entrySet();
            Iterator<Map.Entry<String, JsonElement>> iter = entries.iterator();
            if (iter.hasNext()) {
                return iter.next().getKey();
            }
            throw new ByufenException("The moves property didn't return a valid move");
        } else if (object.has("bestmove") && !object.get("bestmove").isJsonNull()) {
            return object.getAsJsonPrimitive("bestmove").getAsString();
        } else {
            throw new ByufenException("The response didn't contain any suggested moves");
        }
    }

    private String readDataFromURLConnection(ByufenURLWrapper fenService) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(fenService.openStream()));
        String inputLine;
        StringBuilder data = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            data.append(inputLine);
        }
        return data.toString();
    }

    private ByufenURLWrapper establishURLConnection(String fen) throws IOException {
        String url = "https://syzygy-tables.info/api/v2?fen=" + CharMatcher.is(' ').replaceFrom(fen, "%20");
        return new ByufenURLWrapper(url);
    }
}
