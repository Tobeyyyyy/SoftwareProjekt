import javafx.scene.image.Image;

public class SpeedPowerUp
{
    private Image textur;
    private Vec2d position;
    
    public SpeedPowerUp(double x, double y, String texturURL)
    {
        setTextur(ImageLoader.loadImage(texturURL));
        setPosition(new Vec2d(x,y));
    }

    public Image getTextur()
    {
        return textur;
    }

    public void setTextur(Image textur)
    {
        this.textur = textur;
    }

    public Vec2d getPosition()
    {
        return position;
    }

    public void setPosition(Vec2d position)
    {
        this.position = position;
    }
}
