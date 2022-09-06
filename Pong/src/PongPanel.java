import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PongPanel extends JPanel implements ActionListener, KeyListener
{
	// Constructors below
	private final static Color BACKGROUND_COLOR = Color.BLACK; //constant variable for the background color
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 2;
	private final static int POINTS_TO_WIN = 3;
	private final static int SCORE_TEXT_X = 100;
	private final static int SCORE_TEXT_Y = 100;
	private final static int SCORE_FONT_SIZE = 50;
	private final static String SCORE_FONT = "Comic Sans";
	private final static int WINNER_TEXT_X = 200;
    private final static int WINNER_TEXT_Y = 200;
    private final static int WINNER_FONT_SIZE = 40;
    private final static String WINNER_FONT_FAMILY = "Papyrus";
    private final static String WINNER_TEXT = "WIN!";
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	Ball ball;
	GameState gameState = GameState.Initialising;
	Paddle paddle1;
	Paddle paddle2;

	public void createObjects()
	{
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	private void paintDottedLine(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	
	public PongPanel()
	{
		setBackground(BACKGROUND_COLOR);
		Timer timer = new Timer(TIMER_DELAY, this);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	private void paintScores(Graphics g)
	{
		Font scoreFont = new Font(SCORE_FONT, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
		g.drawString(rightScore, getWidth() - SCORE_TEXT_X, SCORE_TEXT_Y);
	}
	
	private void paintWinner(Graphics g)
	{
		if (gameWinner != null)
		{
			Font winnerFont = new Font(WINNER_FONT_FAMILY, Font.BOLD, WINNER_FONT_SIZE);
			g.setFont(winnerFont);
			int xPosition = getWidth() / 2;
			if (gameWinner == Player.One)
			{
				xPosition -= WINNER_TEXT_X;
			}
			else if (gameWinner == Player.Two)
			{
				xPosition += WINNER_TEXT_X;
			}
			g.drawString(WINNER_TEXT, xPosition, WINNER_TEXT_Y);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintDottedLine(g);
		if (gameState != GameState.Initialising)
		{
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWinner(g);
		}
	}
	
	public void paintSprite(Graphics g , Sprite sprite)
	{
		g.setColor(sprite.getColor());
		g.fillRect(sprite.getxPosition(), sprite.getyPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	@Override
	public void keyTyped(KeyEvent event) 
	{
		//Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent event) 
	{
		if (event.getKeyCode() == KeyEvent.VK_UP)
		{
			paddle2.setyVelocity(-2);
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN)
		{
			paddle2.setyVelocity(2);
		}
		if (event.getKeyCode() == KeyEvent.VK_W)
		{
			paddle1.setyVelocity(-2);
		}
		else if (event.getKeyCode() == KeyEvent.VK_S)
		{
			paddle1.setyVelocity(2);
		}

	}

	@Override
	public void keyReleased(KeyEvent event) 
	{
		if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN)
		{
			paddle2.setyVelocity(0);
		}
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S)
		{
			paddle1.setyVelocity(0);
		}

	}

	private void update()
	{
		switch(gameState)
		{
			case Initialising: 
			{
				createObjects();
				gameState = GameState.Playing;
				ball.setxVelocity(BALL_MOVEMENT_SPEED);
				ball.setyVelocity(BALL_MOVEMENT_SPEED);
				break;
			}
			case Playing:
			{
				moveObject(paddle1);	// moving paddle 1
				moveObject(paddle2);	// moving paddle 2
				moveObject(ball);	//moving ball
				checkWallBounce();	// calling Check wall bounce
				checkPaddleBounce();// calling Check paddle bounce
				checkWin();			// calling CheckWin method to see if the game has been won
				break;
			}
			case Gameover:
			{
				break;
			}
		}
	}
	
	public void moveObject(Sprite obj)
	{
		obj.setxPosition(obj.getxPosition() + obj.getxVelocity(), getWidth());
		obj.setyPosition(obj.getyPosition() + obj.getyVelocity(), getHeight());
	}
	
	public void checkWallBounce()
	{
		if (ball.getxPosition() <= 0)
		{
			//hit left side of screen
			ball.setxVelocity(-ball.getxVelocity());
			addScore(Player.Two);
			resetBall();
		}
		else if (ball.getxPosition() >= getWidth() - ball.getHeight())
		{
			//Hit right side of screen
			ball.setxVelocity(-ball.getxVelocity());
			addScore(Player.One);
			resetBall();
		}
		if (ball.getyPosition() <= 0 || ball.getyPosition() >= getHeight() - ball.getHeight())
		{
			//hit top or bottom of screen
			ball.setyVelocity(-ball.getyVelocity());
		}
	}
	
	public void checkPaddleBounce()
	{
	      if(ball.getxVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) 
	      {
	          ball.setxVelocity(BALL_MOVEMENT_SPEED);
	      }
	      if(ball.getxVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) 
	      {
	          ball.setxVelocity(-BALL_MOVEMENT_SPEED);
	      }
	}
	
	public void resetBall()
	{
		ball.resetToInitialPosition();
	}
	
	public void checkWin()
	{
		if (player1Score >= POINTS_TO_WIN)
		{
			gameWinner = Player.One;
			gameState = GameState.Gameover;
		}
		else if (player2Score >= POINTS_TO_WIN)
		{
			gameWinner = Player.Two;
			gameState = GameState.Gameover;
		}
	}
	
	public void addScore(Player player)
	{
		if (player == Player.One)
		{
			player1Score++;
		}
		else if (player == Player.Two)
		{
			player2Score++;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) 
	{
		update();
		repaint();
	}
	

}
