package TetrisController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    private BufferedImage img;
    public static String squarePath = "/Squares.png";
    public static String logoPath = "/Logo.png";
    public static String tetrisPath = "/Tetris.png";
    private static int squareSide = 50;

    public ImageLoader(String path) {
        try {
            img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BufferedImage getImage() {
        return img;
    }
    public BufferedImage getSubImage(int section) {
        return img.getSubimage(section*squareSide, 0, squareSide, squareSide);
    }
}
