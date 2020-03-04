import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class Problem2SutherlandHolmanTest {
    @Test
    public void test1() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("1 11 -8 -3 -9 -13");
        final Problem2SutherlandHolman.Polygon q = createPolygon("5 14 -1 0 4 -14 8 -8 12 4");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test2() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("-8 0 -1 -5 5 1 0 6 -6 5");
        final Problem2SutherlandHolman.Polygon q = createPolygon("-6 4 -3 -3 6 -3 8 6");
        final Problem2SutherlandHolman.Polygon expected = createPolygon("1 5 -6 4 -3 -3 1 -3 5 1");
        assertEquals(expected, Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test3() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 5 0 5 5 0 5");
        final Problem2SutherlandHolman.Polygon q = createPolygon("5 0 10 0 10 5 5 5");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test4() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 5 0 5 5 0 5");
        final Problem2SutherlandHolman.Polygon q = createPolygon("0 0 5 0 5 5 0 5");
        final Problem2SutherlandHolman.Polygon expected = createPolygon("0 5 0 0 5 0 5 5");
        assertEquals(expected, Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test5() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 5 0 5 5 0 5");
        final Problem2SutherlandHolman.Polygon q = createPolygon("0 0 4 0 4 5 0 5");
        final Problem2SutherlandHolman.Polygon expected = createPolygon("0 5 0 0 4 0 4 5");
        assertEquals(expected, Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test6() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 4 0 4 3 0 3");
        final Problem2SutherlandHolman.Polygon q = createPolygon("0 3 4 3 4 5 0 5");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test7() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 4 0 4 3 0 3");
        final Problem2SutherlandHolman.Polygon q = createPolygon("0 3 4 3 4 5 0 5");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test8() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 3 0 3 3 0 3");
        final Problem2SutherlandHolman.Polygon q = createPolygon("3 3 6 3 6 6 3 6");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test9() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("0 0 2 0 0 4");
        final Problem2SutherlandHolman.Polygon q = createPolygon("0 4 2 0 2 4");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test10() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("3 14 -6 6 -12 -4 -7 -13 6 -7");
        final Problem2SutherlandHolman.Polygon q = createPolygon("-1 7 -7 -13 11 -7");
        final Problem2SutherlandHolman.Polygon expected = createPolygon("6 -7 5 0 -1 7 -7 -13");
        assertEquals(expected, Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test11() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("-14 14 -15 -5 3 -13 8 -8");
        final Problem2SutherlandHolman.Polygon q = createPolygon("6 6 -10 -2 -1 -1");
        final Problem2SutherlandHolman.Polygon expected = createPolygon("0 0 -2 2 -10 -2 -1 -1");
        assertEquals(expected, Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test12() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("7 298 -31 296 -60 294 -89 291 -129 286 -134 284 -141 281 -153 273 -174 247 -199 211 -207 197 -224 166 -245 117 -253 96 -272 10 -274 -22 -273 -40 -268 -94 -265 -117 -258 -142 -249 -173 -245 -179 -221 -213 -212 -224 -204 -232 -190 -245 -183 -249 -118 -265 -92 -271 16 -279 43 -275 76 -270 149 -253 163 -249 206 -224 211 -221 219 -211 260 -145 280 -108 287 -93 290 -81 294 -41 296 -11 297 9 286 115 283 135 278 161 271 181 255 215 241 238 223 253 196 270 176 279 162 284 147 288 106 292 78 294 46 296");
        final Problem2SutherlandHolman.Polygon q = createPolygon("48 288 30 287 8 285 -63 275 -106 265 -111 262 -179 220 -218 194 -225 188 -238 176 -272 142 -286 115 -292 98 -296 84 -298 58 -300 -8 -299 -78 -291 -133 -255 -235 -245 -251 -238 -256 -230 -261 -133 -273 -102 -274 -38 -275 38 -269 77 -256 100 -247 126 -233 163 -206 180 -191 206 -160 221 -142 242 -111 253 -84 262 -46 265 -19 270 27 273 68 269 94 262 133 255 167 246 205 229 240 219 254 213 259 189 266 146 274 140 275 108 280");
        final Problem2SutherlandHolman.Polygon expected = createPolygon("48 288 30 287 8 285 -63 275 -106 265 -111 262 -179 220 -203 204 -207 197 -224 166 -245 117 -253 96 -272 10 -274 -22 -273 -40 -268 -94 -265 -117 -258 -142 -249 -173 -245 -179 -221 -213 -212 -224 -204 -232 -190 -245 -183 -249 -118 -265 -92 -271 -38 -275 38 -269 77 -256 100 -247 126 -233 163 -206 180 -191 206 -160 221 -142 242 -111 253 -84 262 -46 265 -19 270 27 273 68 269 94 262 133 255 167 246 205 229 240 219 254 213 259 189 266 146 274 140 275 108 280");
        assertEquals(expected, Problem2SutherlandHolman.solve(p, q));
    }

    @Test
    public void test100() {
        final Problem2SutherlandHolman.Polygon p = createPolygon("-399 -2 -416 -3 -475 -8 -503 -11 -510 -12 -532 -16 -548 -19 -614 -32 -630 -36 -657 -46 -689 -58 -752 -83 -776 -96 -820 -130 -858 -164 -899 -203 -913 -224 -934 -256 -937 -261 -949 -282 -960 -306 -970 -329 -975 -341 -984 -386 -994 -443 -996 -458 -998 -474 -999 -513 -998 -563 -997 -577 -995 -599 -994 -607 -989 -642 -985 -662 -970 -693 -925 -768 -903 -804 -894 -818 -882 -833 -864 -850 -854 -859 -841 -869 -816 -886 -808 -891 -793 -900 -760 -916 -735 -926 -698 -939 -670 -947 -582 -970 -578 -971 -555 -976 -527 -982 -509 -984 -429 -992 -393 -995 -366 -996 -355 -994 -335 -990 -303 -983 -279 -975 -268 -971 -223 -954 -211 -949 -196 -942 -177 -933 -152 -920 -132 -895 -114 -872 -90 -840 -77 -822 -71 -813 -67 -801 -52 -754 -43 -723 -35 -682 -33 -671 -30 -642 -24 -581 -21 -541 -22 -508 -29 -438 -43 -318 -52 -271 -62 -241 -77 -199 -80 -191 -101 -145 -109 -128 -115 -118 -131 -103 -149 -87 -177 -68 -215 -48 -246 -34 -256 -30 -259 -29 -285 -22 -343 -11 -367 -7");
        final Problem2SutherlandHolman.Polygon q = createPolygon("421 982 385 977 328 968 323 967 304 962 297 960 266 950 249 943 221 929 203 918 145 880 129 869 104 851 101 848 91 837 82 825 77 818 60 791 44 760 35 736 24 702 13 652 8 629 6 617 1 580 0 562 4 418 5 397 7 357 8 340 10 325 13 312 24 271 32 242 49 190 54 176 94 103 104 86 128 54 138 41 143 38 165 29 199 22 246 13 267 11 290 9 310 8 412 3 438 2 470 1 495 2 525 4 550 6 593 10 645 15 671 20 680 22 723 34 750 42 763 47 780 56 818 77 823 80 861 109 869 116 872 119 888 136 900 152 916 176 933 207 940 224 945 242 955 285 959 307 960 313 977 445 981 477 988 540 989 553 988 596 986 623 985 632 965 675 963 679 927 747 900 793 884 818 867 844 857 856 843 872 831 883 796 912 766 925 738 937 723 943 697 949 610 967 563 974 551 975 518 977");
        assertNull(Problem2SutherlandHolman.solve(p, q));
    }

    private Problem2SutherlandHolman.Polygon createPolygon(String s) {
        String[] tokens = s.split(" ");
        if (tokens.length % 2 != 0) {
            throw new IllegalArgumentException("Illegal polygon: " + s);
        }
        int n = tokens.length / 2;
        List<Problem2SutherlandHolman.Point> points = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            int x = Integer.parseInt(tokens[2 * i]);
            int y = Integer.parseInt(tokens[2 * i + 1]);
            points.add(new Problem2SutherlandHolman.Point(x, y));
        }
        return new Problem2SutherlandHolman.Polygon(points);
    }
}