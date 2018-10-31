import java.text.SimpleDateFormat;

public interface Constants {
    public final int FRAME_WIDTH = 285, FRAME_HEIGHT = 190;
    public final String pattern = "yyyy-MM-dd";
    public final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    public final String prefix = "<html><body>", postfix = "</body></html>";
}
