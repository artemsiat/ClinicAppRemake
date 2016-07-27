package programm;

import javafx.scene.paint.Color;

/**
 * Created by Artem Siatchinov on 7/24/2016.
 */
public class FrameColor {

    private static Color COLOR_SUCESS = Color.GREEN;
    private static Color COLOR_ERROR = Color.BLUE;

    public static Color getColorSucess() {
        return COLOR_SUCESS;
    }

    public static Color getColorError() {
        return COLOR_ERROR;
    }
}
