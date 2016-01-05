package grafika;

import gra.Line;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Obiekt canvas, na kt�rym jest rysowana zawarto��
 * 
 * @author Sebastian
 *
 */
public class DrawingCanvas extends Canvas
{
	/** Wsp�rz�dne puszczenia przycisku myszy */
	private int oldX, oldY;
	/** Wsp�rz�dne klikni�cia mysz� */
	private int currentX, currentY;
	/** Kolor obiektu */
	private Color color = Color.BLACK;
	/** Obiekt grafiki, kt�ry rysuje obiekty na ekranie */
	private Graphics graphics;
	/** Flaga s�u��ca do sprawdzania, czy poruszamy mysz�(zdarzenie mouseDragged) */
	boolean drag = false;
	/** Flaga s�u��ca do sprawdzenia, czy mysz opu�ci�a canvas */
	private boolean exited = false;
	/** Zbi�r narysowanych odcink�w */
	private List<Drawable> lines = new ArrayList<>();

	/**
	 * Konstruuje pust� przestrze� do rysowania
	 */
	public DrawingCanvas()
	{
		super();
		this.addMouseListener(mouseCallback);
		this.addMouseMotionListener(mouseMotionCallback);
		this.addKeyListener(keyCallback);
	}

	/**
	 * Ustawia kolor rysowanych linii
	 * 
	 * @param index
	 *            indeks koloru @see DrawingCanvas.getColor()
	 */
	public void setColor(int index)
	{
		color = getColor(index);
	}

	/**
	 * Rysuje wszystkie kreski ze zbioru
	 */
	public void paint(Graphics g)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			Line temp = (Line) lines.get(i);
			g.setColor(temp.kolor);
			g.drawLine(temp.poczatek.x, temp.poczatek.y, temp.koniec.x, temp.koniec.y);
		}
	}

	/**
	 * Dodaje kreske do zbioru
	 * 
	 * @param l
	 *            kreska, kt�ra ma zosta� dodana
	 */
	public void draw(Line l)
	{
		lines.add(l);
	}

	/**
	 * Zwraca ilo�� kresek w zbiorze
	 * 
	 * @return
	 */
	public int lineCount()
	{
		return lines.size();
	}

	/**
	 * Usuwa kresk�
	 * 
	 * @param x
	 *            1 je�li pierwsza, 0 jesli wszystkie, -1 je�li ostatnia jest do usuni�cia
	 */
	public void remove(int x)
	{
		if (lines != null && lines.size() != 0)
		{
			if (x == 0)
			{
				lines.clear();
			} else if (x == 1)
			{
				lines.remove(0);
			} else if (x == -1)
			{
				lines.remove(lines.size() - 1);
			}
		}
	}

	/**
	 * Handler event�w myszy
	 */
	private MouseListener mouseCallback = new MouseAdapter()
	{

		public void mousePressed(MouseEvent ev)
		{
			oldX = ev.getX();
			oldY = ev.getY();
			currentX = oldX;
			currentY = oldY;
			drag = true;
			graphics = getGraphics();
			graphics.setColor(color);
			graphics.setXORMode(getBackground());
			graphics.drawLine(oldX, oldY, currentX, currentY);
		}

		public void mouseReleased(MouseEvent e)
		{
			if (drag && !exited)
			{
				graphics.drawLine(oldX, oldY, currentX, currentY); // Erase the previous line.
				int endX = e.getX(); // Where the mouse was released.
				int endY = e.getY();
				graphics.setPaintMode();
				graphics.setColor(color);
				graphics.drawLine(oldX, oldY, endX, endY);
				graphics.dispose();
				drag = false;
				draw(new Line(new Point(oldX, oldY), new Point(endX, endY), color));
			}
		}

		public void mouseEntered(MouseEvent e)
		{
			exited = false;
		}

		public void mouseExited(MouseEvent e)
		{
			if (e.getModifiers() == MouseEvent.BUTTON1_MASK)
			{
				exited = true;
				repaint();
			}
		}
	};

	/**
	 * Handler event�w poruszenia mysz�
	 */
	private MouseMotionListener mouseMotionCallback = new MouseMotionAdapter()
	{
		public void mouseDragged(MouseEvent e)
		{
			if (drag && !exited)
			{
				graphics.drawLine(oldX, oldY, currentX, currentY); // Erase the previous line.
				currentX = e.getX();
				currentY = e.getY();
				graphics.drawLine(oldX, oldY, currentX, currentY); // Draw the new line.
			}
		}
	};

	/**
	 * Handler klawiatury
	 */
	private KeyListener keyCallback = new KeyAdapter()
	{
		public void keyReleased(KeyEvent ev)
		{
			switch (ev.getKeyCode())
			{
				case KeyEvent.VK_F:
					remove(1);
					break;
				case KeyEvent.VK_L:
					remove(-1);
					break;
				case KeyEvent.VK_BACK_SPACE:
					remove(0);
					break;
			};
			repaint();
		}
	};

	/**
	 * Zwraca kolor odpowiadaj�cy indeksowi
	 * 
	 * @param selectedIndex
	 *            indeks
	 * @return kolor
	 */
	private Color getColor(int selectedIndex)
	{
		Color c = null;
		switch (selectedIndex)
		{
			case 0:
				c = Color.BLACK;
				break;
			case 1:
				c = Color.GREEN;
				break;
			case 2:
				c = Color.BLUE;
				break;
			case 3:
				c = Color.RED;
				break;
			case 4:
				c = Color.YELLOW;
				break;
			case 5:
				c = Color.ORANGE;
				break;
			case 6:
				c = Color.GRAY;
				break;
			case 7:
				c = Color.PINK;
				break;
			default:
				c = Color.BLACK;
				break;
		}
		return c;
	}
}
