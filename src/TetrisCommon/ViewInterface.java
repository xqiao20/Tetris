package TetrisCommon;

import java.awt.*;

public interface ViewInterface {
    void init();
    void tick();
    void render(Graphics g);
    void goBack();
}
