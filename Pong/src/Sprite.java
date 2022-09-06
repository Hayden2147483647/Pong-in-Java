import java.awt.Color;
import java.awt.Rectangle;

public class Sprite 
{

	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private int initialXPosition, initialYPosition;
	private Color color;

	public int getxPosition()
	{
		return xPosition;
	}

	public void setxPosition(int xPosition)
	{
		this.xPosition = xPosition;
	}

	public int getyPosition()
	{
		return yPosition;
	}

	public void setyPosition(int yPosition)
	{
		this.yPosition = yPosition;
	}
	
	public void setxPosition(int newX, int panelWidth) 
	{
	     xPosition = newX;
	     if (xPosition < 0)
	     {
	    	 xPosition = 0;
	     }
	     else if (xPosition + width > panelWidth)
	     {
	    	 xPosition = panelWidth - width;
	     }
	 }
	 public void setyPosition(int newY, int panelHeight) 
	 {
	     yPosition = newY;
	     if (yPosition < 0)
	     {	
	    	 yPosition = 0;
	     }
	     else if (yPosition + height > panelHeight)
	     {
	    	 yPosition = panelHeight - height;
	     }
	 }
	 
	public void setInitialPosition(int initialX, int initialY)
	{
		this.initialXPosition = initialX;
		this.initialYPosition = initialY;
	}
	
	public void resetToInitialPosition()
	{
		setxPosition(initialXPosition);
		setyPosition(initialYPosition);
	}

	public int getxVelocity()
	{
		return xVelocity;
	}

	public void setxVelocity(int xVelocity)
	{
		this.xVelocity = xVelocity;
	}

	public int getyVelocity() 
	{
		return yVelocity;
	}

	public void setyVelocity(int yVelocity)
	{
		this.yVelocity = yVelocity;
	}

	public int getWidth() 
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getHeight() 
	{
		return height;
	}
	public void setHeight(int height) 
	{
		this.height = height;
	}

	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle(getxPosition(), getyPosition(), getWidth(), getHeight());
	}
}
