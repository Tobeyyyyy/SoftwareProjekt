import javafx.scene.image.Image;

public class ImageLoader
{
    public static Image loadImage(String url)
    {
        try
        {
            return new Image(url);
        } 
        catch (Exception e)
        {
            System.err.print(url + " konnte nicht geladen werden!");
            System.exit(0);
            return null;
        }
    }
}
