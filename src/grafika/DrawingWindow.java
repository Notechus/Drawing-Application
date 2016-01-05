package grafika;

import java.awt.*;
import java.awt.event.*;

/**
 * Klasa reprezentuj�ca okno GUI
 * 
 * @author Sebastian
 */
public class DrawingWindow extends Frame
{
	private final int WIDTH = 600;
	private final int HEIGHT = 400;
	/** Panel z kolorami do wyboru */
	private Panel colorPanel;
	/** Obszar rysowania */
	private DrawingCanvas drawCanvas;
	/** Lista wszystkich narysowanych kresek */
	private List drawColors;

	/**
	 * Konstruuje domy�lne okno aplikacji
	 */
	public DrawingWindow()
	{
		super("Drawing Application");
		initGUI();
		initListeners();
		initColors();
		this.setVisible(true);
	}

	/**
	 * Inicjalizuje list� z kolorami
	 */
	private void initColors()
	{
		drawColors.add("Black");
		drawColors.add("Green");
		drawColors.add("Blue");
		drawColors.add("Red");
		drawColors.add("Yellow");
		drawColors.add("Orange");
		drawColors.add("Gray");
		drawColors.add("Pink");
	}

	/**
	 * Inicjalizuje elementy GUI
	 */
	private void initGUI()
	{
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(200, 200);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		drawCanvas = new DrawingCanvas();
		colorPanel = new Panel();
		drawColors = new List();
		colorPanel.add(drawColors);
		this.add(drawColors, BorderLayout.WEST);
		this.add(drawCanvas, BorderLayout.CENTER);
		drawCanvas.setFocusable(true);
		drawCanvas.requestFocus();
	}

	/**
	 * Inicjalizuje handlery event�w okna
	 */
	private void initListeners()
	{
		this.addWindowListener(windowCallback);
		drawColors.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 1)
				{
					drawCanvas.setColor(drawColors.getSelectedIndex());
				}
			}
		});
	}

	/**
	 * Handler zdarze� dotycz�cych bezpo�rednio okna
	 */
	private WindowListener windowCallback = new WindowAdapter()
	{
		public void windowClosing(WindowEvent e)
		{
			DrawingWindow.this.dispose();
			System.exit(0);
		}
	};
}
