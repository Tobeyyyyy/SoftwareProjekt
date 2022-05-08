import java.util.ArrayList;

import javafx.scene.image.Image;

public class Player extends Entity
{
	private ArrayList<Image> texturen;
	private int momentaneTextur;

	private int timer;

	public Player(double x, double y, int scale, String... texturenURLs)
	{
		super(x, y, 1);
		texturen = new ArrayList<Image>();
		timer = 0;

		for (int i = 0; i < texturenURLs.length; i++)
		{
			this.texturen.add(ImageLoader.loadImage(texturenURLs[i]));
		}
		momentaneTextur = 0;
	}

	public void tick(PacmanController controllerRef)
	{
		if (timer <= 30)
		{
			timer++;
		} else
		{
			if (momentaneTextur == 1)
			{
				momentaneTextur = 0;
			} else if (momentaneTextur == 0)
			{
				momentaneTextur = 1;
			}
			timer = 0;
		}

		if (position.getX() % 30 == 0 && position.getY() % 30 == 0)
		{
			if (controllerRef.getRequestedDirection() != -1)
			{
				switch (controllerRef.getRequestedDirection())
				{
					case 0:
						if (controllerRef.getLevel().istFeldFrei(position.getX(), position.getY() - 30.0))
						{
							richtung = 0;
							controllerRef.setRequestedDirection(-1);
						}
						break;
					case 1:
						if (controllerRef.getLevel().istFeldFrei(position.getX() + 30.0, position.getY()))
						{
							richtung = 1;
							controllerRef.setRequestedDirection(-1);
						}
						break;
					case 2:
						if (controllerRef.getLevel().istFeldFrei(position.getX(), position.getY() + 30.0))
						{
							richtung = 2;
							controllerRef.setRequestedDirection(-1);
						}
						break;
					case 3:
						if (controllerRef.getLevel().istFeldFrei(position.getX() - 30.0, position.getY()))
						{
							richtung = 3;
							controllerRef.setRequestedDirection(-1);
						}
						break;
				}
			} else
			{
				if (richtung == 0)
				{
					if (!controllerRef.getLevel().istFeldFrei(position.getX(), position.getY() - 30.0))
					{
						richtung = -1;
					}

				} else if (richtung == 1)
				{
					if (!controllerRef.getLevel().istFeldFrei(position.getX() + 30.0, position.getY()))
					{
						richtung = -1;
					}
				} else if (richtung == 2)
				{
					if (!controllerRef.getLevel().istFeldFrei(position.getX(), position.getY() + 30.0))
					{
						richtung = -1;
					}
				} else if (richtung == 3)
				{
					if (!controllerRef.getLevel().istFeldFrei(position.getX() - 30.0, position.getY()))
					{
						richtung = -1;
					}
				}
			}
			if (richtung == 0)
			{
				if (!controllerRef.getLevel().istFeldFrei(position.getX(), position.getY() - 30.0))
				{
					richtung = -1;
				}

			} else if (richtung == 1)
			{
				if (!controllerRef.getLevel().istFeldFrei(position.getX() + 30.0, position.getY()))
				{
					richtung = -1;
				}
			} else if (richtung == 2)
			{
				if (!controllerRef.getLevel().istFeldFrei(position.getX(), position.getY() + 30.0))
				{
					richtung = -1;
				}
			} else if (richtung == 3)
			{
				if (!controllerRef.getLevel().istFeldFrei(position.getX() - 30.0, position.getY()))
				{
					richtung = -1;
				}
			}
			
		} else
		{
			if (richtung == 0 && controllerRef.getRequestedDirection() == 2)
			{
				richtung = 2;
				controllerRef.setRequestedDirection(-1);

			} else if (richtung == 1 && controllerRef.getRequestedDirection() == 3)
			{
				richtung = 3;
				controllerRef.setRequestedDirection(-1);

			} else if (richtung == 2 && controllerRef.getRequestedDirection() == 0)
			{
				richtung = 0;
				controllerRef.setRequestedDirection(-1);

			} else if (richtung == 3 && controllerRef.getRequestedDirection() == 1)
			{
				richtung = 1;
				controllerRef.setRequestedDirection(-1);
			}
		}

		switch (richtung)
		{
			case 0:
				position.setY(position.getY() - 1.0);
				break;
			case 1:
				position.setX(position.getX() + 1.0);
				break;
			case 2:
				position.setY(position.getY() + 1.0);
				break;
			case 3:
				position.setX(position.getX() - 1.0);
				break;
		}
	}

	public Image getMomentaneTextur()
	{
		return texturen.get(momentaneTextur);
	}
}
