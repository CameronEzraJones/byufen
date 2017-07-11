package com.example.byufen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by cejon on 7/10/2017.
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class ByufenApplicationTestValidityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidFen1() throws Exception {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String okayMessage = "This is a valid fen string";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(okayMessage)));
    }

    @Test
    public void testValidFen2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        String okayMessage = "This is a valid fen string";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(okayMessage)));
    }

    @Test
    public void testValidFen3() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq c6 1 2";
        String okayMessage = "This is a valid fen string";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(okayMessage)));
    }

    @Test
    public void testValidFen4() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq c3 1 2";
        String okayMessage = "This is a valid fen string";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(okayMessage)));
    }

    @Test
    public void testInvalidFenNotSixElements() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R";
        String errorMessage = "There are not exactly 6 elements in the fen string";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidCharactersPiecePlacement() throws Exception {
        String fen = "rnbskbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        String errorMessage = "There are invalid characters in the piece placement data element";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenNotEightRowsOver() throws Exception {
        String fen = "8/rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        String errorMessage = "There are not exactly 8 rows in the piece placement data element";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenNotEightRowsUnder() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        String errorMessage = "There are not exactly 8 rows in the piece placement data element";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenNotEightCellsInRowOver() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/3p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        String errorMessage = "There are not exactly 8 cells in one or more of the piece placement data rows";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenNotEightCellsInRowUnder() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/7/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        String errorMessage = "There are not exactly 8 cells in one or more of the piece placement data rows";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidActiveColor1() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R - KQkq - 1 2";
        String errorMessage = "The active color element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidActiveColor2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R bw KQkq - 1 2";
        String errorMessage = "The active color element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidCastlingAvailability1() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQQ - 1 2";
        String errorMessage = "The castling availability element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidCastlingAvailability2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkqK - 1 2";
        String errorMessage = "The castling availability element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidEnPassantTargetSquare1() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq -- 1 2";
        String errorMessage = "The en passant target square element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidEnPassantTargetSquare2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq c6 1 2";
        String errorMessage = "The en passant target square element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidEnPassantTargetSquare3() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq c3 1 2";
        String errorMessage = "The en passant target square element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidEnPassantTargetSquare4() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq c4 1 2";
        String errorMessage = "The en passant target square element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidHalfmoveClock1() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - - 2";
        String errorMessage = "The halfmove clock element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidHalfmoveClock2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1a 2";
        String errorMessage = "The halfmove clock element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidFullmoveNumber1() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 -";
        String errorMessage = "The fullmove number element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    public void testInvalidFenInvalidFullmoveNumber2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2a";
        String errorMessage = "The fullmove number element is invalid";
        this.mockMvc.perform(get("/validate?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(errorMessage)));
    }
}