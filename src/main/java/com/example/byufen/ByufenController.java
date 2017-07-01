package com.example.byufen;

import com.example.byufen.util.ByufenException;
import com.example.byufen.util.ByufenPrinter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by cejon on 6/30/2017.
 */
@RestController
public class ByufenController {
    @RequestMapping(method = RequestMethod.GET, value = "/ping")
    public String ping() {
        return "OK!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/task1")
    public ResponseEntity<String> task1(@RequestParam("fen") String fen) {
        try {
            String board = new ByufenPrinter().printBoard(fen);
            return ResponseEntity.status(HttpStatus.OK).body("<pre>" + board + "</pre>");
        } catch (ByufenException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
