package com.example.byufen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ByufenApplicationTests {

	@Autowired
	private ByufenController byufenController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
		assertThat(byufenController).isNotNull();
	}

	@Test
	public void pingShouldReturnOK() throws Exception {
		this.mockMvc.perform(get("/ping")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("OK!")));
	}

	@Test
	public void testValidBoard() throws Exception {
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
		this.mockMvc.perform(get("/task1?fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"))
				.andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(board)));
	}

	@Test
	public void testInvalidFenElements() throws Exception {
		String error = "There are invalid characters in the piece placement data element";
		this.mockMvc.perform(get("/task1?fen=snbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"))
				.andDo(print()).andExpect(status().isBadRequest()).andExpect(content().string(containsString(error)));
	}
}
