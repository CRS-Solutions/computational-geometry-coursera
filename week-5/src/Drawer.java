public interface Drawer {
    Drawer reset();
    Drawer rect(int x1, int y1, int x2, int y2, int color);
    Drawer line(int x1, int y1, int x2, int y2, int color);
    Drawer point(int x, int y, int color);
}
