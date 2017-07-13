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
public class ByufenApplicationTask1Tests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testValidStartingBoard() throws Exception {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        String board =
                "  ---------------------------------\n" +
                        "8 | r | n | b | q | k | b | n | r |\n" +
                        "  |-------------------------------|\n" +
                        "7 | p | p | p | p | p | p | p | p |\n" +
                        "  |-------------------------------|\n" +
                        "6 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "5 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "4 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "3 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "2 | P | P | P | P | P | P | P | P |\n" +
                        "  |-------------------------------|\n" +
                        "1 | R | N | B | Q | K | B | N | R |\n" +
                        "  ---------------------------------\n" +
                        "    a   b   c   d   e   f   g   h";
        this.mockMvc.perform(get("/task1?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(board)));
    }

    @Test
    public void testValidEmptyBoard() throws Exception {
        String fen = "8/8/8/8/8/8/8/8 w - - 0 1";
        String board =
                "  ---------------------------------\n" +
                        "8 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "7 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "6 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "5 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "4 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "3 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "2 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "1 |   |   |   |   |   |   |   |   |\n" +
                        "  ---------------------------------\n" +
                        "    a   b   c   d   e   f   g   h";
        this.mockMvc.perform(get("/task1?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(board)));
    }

    @Test
    public void testValidEarlygameBoard() throws Exception {
        String fen = "r2q1rk1/pp2ppbp/2p2np1/6B1/3PP1b1/Q1P2N2/P4PPP/3RKB1R b K - 0 13";
        String board =
                "  ---------------------------------\n" +
                        "8 | r |   |   | q |   | r | k |   |\n" +
                        "  |-------------------------------|\n" +
                        "7 | p | p |   |   | p | p | b | p |\n" +
                        "  |-------------------------------|\n" +
                        "6 |   |   | p |   |   | n | p |   |\n" +
                        "  |-------------------------------|\n" +
                        "5 |   |   |   |   |   |   | B |   |\n" +
                        "  |-------------------------------|\n" +
                        "4 |   |   |   | P | P |   | b |   |\n" +
                        "  |-------------------------------|\n" +
                        "3 | Q |   | P |   |   | N |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "2 | P |   |   |   |   | P | P | P |\n" +
                        "  |-------------------------------|\n" +
                        "1 |   |   |   | R | K | B |   | R |\n" +
                        "  ---------------------------------\n" +
                        "    a   b   c   d   e   f   g   h";
        this.mockMvc.perform(get("/task1?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(board)));
    }

    @Test
    public void testValidEndgameBoard() throws Exception {
        String fen = "8/8/2P5/4B3/1Q6/4K3/6P1/3k4 w - - 5 67";
        String board =
                "  ---------------------------------\n" +
                        "8 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "7 |   |   |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "6 |   |   | P |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "5 |   |   |   |   | B |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "4 |   | Q |   |   |   |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "3 |   |   |   |   | K |   |   |   |\n" +
                        "  |-------------------------------|\n" +
                        "2 |   |   |   |   |   |   | P |   |\n" +
                        "  |-------------------------------|\n" +
                        "1 |   |   |   | k |   |   |   |   |\n" +
                        "  ---------------------------------\n" +
                        "    a   b   c   d   e   f   g   h";
        this.mockMvc.perform(get("/task1?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(board)));
    }

    @Test
    public void testInvalidString() throws Exception {
        String fen = "Blah";
        String error = "There are not exactly 6 elements in the fen string";
        this.mockMvc.perform(get("/task1?fen=" + fen))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(error)));
    }
}