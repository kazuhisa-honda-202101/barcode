package com.example.barcode;

import java.util.ArrayList;
import java.util.List;

/**
 * NW-7形式のバーコードをSVGで作成
 * 参考：http://yylab.ce.cst.nihon-u.ac.jp/~hiroshi/JavaScript/barcode.html
 */
public class BarcodeNW7Generator {

    private final int gap = 2;
    private final int shortBar = 2;
    private final int longBar = 6;
    private final int vMargin = 10;
    private final int barHeight = 100;

    private static final String[] ccode = new String[20];
    private static final String[] bcode = new String[20];

    {
        ccode[0] = "0";
        bcode[0] = "0000011";
        ccode[1] = "1";
        bcode[1] = "0000110";
        ccode[2] = "2";
        bcode[2] = "0001001";
        ccode[3] = "3";
        bcode[3] = "1100000";
        ccode[4] = "4";
        bcode[4] = "0010010";
        ccode[5] = "5";
        bcode[5] = "1000010";
        ccode[6] = "6";
        bcode[6] = "0100001";
        ccode[7] = "7";
        bcode[7] = "0100100";
        ccode[8] = "8";
        bcode[8] = "0110000";
        ccode[9] = "9";
        bcode[9] = "1001000";
        ccode[10] = "-";
        bcode[10] = "0001100";
        ccode[11] = "$";
        bcode[11] = "0011000";
        ccode[12] = ":";
        bcode[12] = "1000101";
        ccode[13] = "/";
        bcode[13] = "1010001";
        ccode[14] = ".";
        bcode[14] = "1010100";
        ccode[15] = "+";
        bcode[15] = "0010101";
        ccode[16] = "A";
        bcode[16] = "0011010";
        ccode[17] = "B";
        bcode[17] = "0101001";
        ccode[18] = "C";
        bcode[18] = "0001011";
        ccode[19] = "D";
        bcode[19] = "0001110";
    }

    private final int startCode = 16;
    private final int stopCode = 17;
    private int startX;
    private final List<String> results = new ArrayList<>();

    private BarcodeNW7Generator() {
    }

    public static String generateSvg(String value) {
        return new BarcodeNW7Generator().genSvg(value);
    }

    public static String generateDiv(String value) {
        return new BarcodeNW7Generator().genDiv(value);
    }

    private String genSvg(String value) {
        drawSvg(bcode[startCode]);
        for (int i = 0; i < value.length(); i++) {
            for (int j = 0; j < 16; j++) { // スタート，ストップコード以外で検索
                if (value.substring(i, i + 1).startsWith(ccode[j])) {
                    drawSvg(bcode[j]);
                    break;
                }
            }
        }
        drawSvg(bcode[stopCode]);
        return String.join("\n"
            , String.format("<svg width='%d' height='%d'>", startX, barHeight)
            , String.join("\n", results)
            , "</svg>"
        );
    }

    void drawSvg(String bstr) {
        var h = vMargin + barHeight;
        startX += gap;  // キャラクタ間ギャップ
        for (int i = 0; i < bstr.length(); i++) {
            int width;
            if (bstr.substring(i, i + 1).startsWith("0")) {
                width = shortBar;
            } else {
                width = longBar;
            }
            if ((i % 2) == 0) {
                results.add(String.format(
                    "<rect x='%d' y='%d' width='%d' height='%d' fill='black' stroke='none'/>",
                    startX, vMargin, width, h
                ));
            }
            startX += width;
        }
    }


    private String genDiv(String value) {
        drawDiv(bcode[startCode]);
        for (int i = 0; i < value.length(); i++) {
            for (int j = 0; j < 16; j++) { // スタート，ストップコード以外で検索
                if (value.substring(i, i + 1).startsWith(ccode[j])) {
                    drawDiv(bcode[j]);
                    break;
                }
            }
        }
        drawDiv(bcode[stopCode]);
        return String.join("\n"
            , String.format("<div style='position: relative; width: %d; height: %d'>", startX, barHeight)
            , String.join("\n", results)
            , "</div>"
        );
    }

    void drawDiv(String bstr) {
        var h = vMargin + barHeight;
        startX += gap;  // キャラクタ間ギャップ
        for (int i = 0; i < bstr.length(); i++) {
            int width;
            if (bstr.substring(i, i + 1).startsWith("0")) {
                width = shortBar;
            } else {
                width = longBar;
            }
            if ((i % 2) == 0) {
                results.add(String.format(
                    "<div style='background-color:black; position: absolute; top: 0; left: %dpx; width: %dpx; height: %dpx'></div>"
                    , startX, width, h
                    //"<rect x='%d' y='%d' width='%d' height='%d' fill='black' stroke='none'/>",
                    //, startX, vMargin, width, h
                ));
            }
            startX += width;
        }
    }

}
