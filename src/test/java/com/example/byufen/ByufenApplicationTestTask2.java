package com.example.byufen;

import com.example.byufen.util.ByufenURLWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by cejon on 7/10/2017.
 */
@PrepareForTest(ByufenController.class)
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@WebMvcTest
public class ByufenApplicationTestTask2 {

    final String fenServiceBase = "https://syzygy-tables.info/api/v2?fen=";
    @Autowired
    private MockMvc mockMvc;

    private String URLifyString(String fen) {
        return fen.replace(" ", "%20");
    }

    @Test
    public void testValidDoMove1() throws Exception {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"a2a3\"}".getBytes()));
        String updatedFen = "rnbqkbnr/pppppppp/8/8/8/P7/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testValidDoMove2() throws Exception {
        String fen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"a7a5\"}".getBytes()));
        String updatedFen = "rnbqkbnr/1p1ppppp/8/p1p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testValidDoMoveBestMovePreference() throws Exception {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"a2a3\",\"moves\":{\"a2a4\":{}}}".getBytes()));
        String updatedFen = "rnbqkbnr/pppppppp/8/8/8/P7/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testValidDoMoveUseFirstMovesSuggestionIfNoBestMove() throws Exception {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"a2a3\",\"moves\":{\"a2a4\":{},\"b2b3\":{}}}".getBytes()));
        String updatedFen = "rnbqkbnr/pppppppp/8/8/8/P7/1PPPPPPP/RNBQKBNR w KQkq - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testCastlingWhiteKingside() throws Exception {
        String fen = "4k3/8/8/8/8/8/8/4K2R w K - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"e1g1\"}".getBytes()));
        String updatedFen = "4k3/8/8/8/8/8/8/5RK1 w K - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testCastlingWhiteQueenside() throws Exception {
        String fen = "4k3/8/8/8/8/8/8/R3K3 w Q - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"e1c1\"}".getBytes()));
        String updatedFen = "4k3/8/8/8/8/8/8/2KR4 w Q - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testCastlingBlackKingside() throws Exception {
        String fen = "4k2r/8/8/8/8/8/8/4K3 b k - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"e8g8\"}".getBytes()));
        String updatedFen = "5rk1/8/8/8/8/8/8/4K3 b k - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testCastlingBlackQueenside() throws Exception {
        String fen = "r3k3/8/8/8/8/8/8/4K3 b q - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"e8c8\"}".getBytes()));
        String updatedFen = "2kr4/8/8/8/8/8/8/4K3 b q - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testEnPassantWhite() throws Exception {
        String fen = "4k3/8/8/2Pp4/8/8/8/4K3 w - d6 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"c5d6\"}".getBytes()));
        String updatedFen = "4k3/8/3P4/8/8/8/8/4K3 w - d6 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testEnPassantBlack() throws Exception {
        String fen = "4k3/8/8/8/2Pp4/8/8/4K3 b - c3 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"d4c3\"}".getBytes()));
        String updatedFen = "4k3/8/8/8/8/2p5/8/4K3 b - c3 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testPawnPromotionWhite() throws Exception {
        String fen = "4k3/2P5/4K3/8/8/8/8/8 w - - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"c7c8q\"}".getBytes()));
        String updatedFen = "2Q1k3/8/4K3/8/8/8/8/8 w - - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }

    @Test
    public void testPawnPromotionBlack() throws Exception {
        String fen = "8/8/8/8/8/4k3/2p5/4K3 b - - 0 1";
        ByufenURLWrapper mockURL = mock(ByufenURLWrapper.class);
        PowerMockito.whenNew(ByufenURLWrapper.class).withAnyArguments().thenReturn(mockURL);
        when(mockURL.openStream()).thenReturn(new ByteArrayInputStream("{\"bestmove\":\"c2c1q\"}".getBytes()));
        String updatedFen = "8/8/8/8/8/4k3/8/2q1K3 b - - 0 1";
        this.mockMvc.perform(get("/task2?fen=" + fen))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(updatedFen)));
    }


}