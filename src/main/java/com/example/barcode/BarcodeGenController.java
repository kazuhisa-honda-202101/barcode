package com.example.barcode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarcodeGenController {

    @GetMapping(path = "_barcode/nw-7/svg", produces = "text/html")
    public String genBarcodeAsSvg(@RequestParam String value) {
        return html(BarcodeNW7Generator.generateSvg((value)));
    }

    @GetMapping(path = "_barcode/nw-7/div", produces = "text/html")
    public String genBarcodeAsDiv(@RequestParam String value) {
        return html(BarcodeNW7Generator.generateDiv((value)));
    }

    public String html(String body) {
        return String.join("\n"
            , "<html>"
            , "<head>"
            , "<style>"
            , "html, body{height: 100%;}"
            , "body {display: flex; justify-content: center; align-items: center;}"
            , "</style>"
            , "</head>"
            , "<body>"
            , body
            , "</body>"
            , "</html>");
    }
}