
public class Entity {

	protected Vec2d position;
	protected int richtung;

    public Entity(double x, double y, int richtung) 
	{
		position = new Vec2d(x, y);
		this.richtung = richtung;
	}

	public Vec2d getPosition()
	{
		return position;
	}

	public void setPosition(Vec2d position)
	{
		this.position = position;
	}

    public int getRichtung()
    {
        return richtung;
    }

    public void setRichtung(int richtung)
    {
        this.richtung = richtung;
    }
}
