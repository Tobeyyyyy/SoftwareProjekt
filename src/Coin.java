import javafx.scene.image.Image;

public class Coin extends Entity {
		
	private static Image textur;
    
	public Coin(double x, double y){
		super(x,y,0);
	}

    public static Image getTextur()
    {
        return textur;
    }

    public static void setTextur(Image textur)
    {
        Coin.textur = textur;
    }
}
