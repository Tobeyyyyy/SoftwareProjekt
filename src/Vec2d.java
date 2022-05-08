
public class Vec2d {
  
  private double x;
  private double y;
  
  public Vec2d(double x, double y)
  {
	  this.x = x;
      this.y = y;
  }
  
  public double getX() 
  {
      return x;
  }

  public void setX(double x)
  {
      this.x = x;
  }

  public double getY() 
  {
      return y;
  }

  public void setY(double y) 
  {
      this.y = y;
  }
  
  public void print()
  {
      System.out.println(x + " " + y);
  }
} 
