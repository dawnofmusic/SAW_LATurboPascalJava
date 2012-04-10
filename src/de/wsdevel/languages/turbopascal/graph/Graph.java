package de.wsdevel.languages.turbopascal.graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.wsdevel.tools.awt.GUIToolkit;
import de.wsdevel.tools.awt.SwingThreadAdapter;

/**
 * Created on 10.04.2012 for project: SAW_LATurboPascalJava
 *
 * (c) 2012 Sebastian A. Weiﬂ - All rights reserved.
 *
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 */
public class Graph extends JPanel {

    private static final Font DEFAULT_FONT = new Font(Font.MONOSPACED,
	    Font.BOLD, 14);

    /**
     * {@link PaletteType} COMMENT.
     */
    private static PaletteType allPalette;

    /**
     * {@link boolean} COMMENT.
     */
    public static final boolean ClipOff = false;

    /**
     * {@link boolean} COMMENT.
     */
    public static final boolean ClipOn = true;

    /**
     * {@link int} COMMENT.
     */
    private static int color;

    /**
     * {@link FillStyle} COMMENT.
     */
    private static FillStyle fillStyle;

    /**
     * {@link int} COMMENT.
     */
    public static final int GetMaxX = 800;

    /**
     * {@link int} COMMENT.
     */
    public static final int GetMaxY = 600;

    /**
     * {@link Graph} COMMENT.
     */
    private static Graph instance;

    /**
     * {@link EGAColor[]} COMMENT.
     */
    private static EGAColor[] rgbPalette;

    /**
     * {@link long} COMMENT.
     */
    private static final long serialVersionUID = -7649002361074556452L;

    /**
     * {@link WriteMode} COMMENT.
     */
    private static WriteMode writeMode = WriteMode.NormalPut;

    static {
	Graph.rgbPalette = new EGAColor[64];
	for (int i = 0; i < 64; i++) {
	    Graph.rgbPalette[i] = new EGAColor(i, i, i);
	}
    }

    /**
     * {@link Log} COMMENT.
     */
    private static final Log LOG = LogFactory.getLog(Graph.class);

    /**
     * {@link char} COMMENT.
     */
    private static char lastKey;

    /**
     * {@link int} COMMENT.
     */
    private static int backgroundColor;

    /**
     * COMMENT.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public static void Bar(final int x1, final int y1, final int x2,
	    final int y2) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		takeCareOfXorMode(graphics);
		graphics.setPaint(getColor(Graph.color));
		switch (Graph.fillStyle) {
		case SolidFill:
		    graphics.fillRect(x1, y1, x2 - x1, y2 - y1);
		}
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param xcor
     * @param ycor
     * @param radius
     */
    public static void Circle(final int xcor, final int ycor, final int radius) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		takeCareOfXorMode(graphics);
		graphics.setPaint(getColor(Graph.color));
		graphics.drawOval(xcor - radius, ycor - radius, radius * 2,
			radius * 2);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     */
    public static void ClearViewPort() {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		graphics.setPaint(Graph.getColor(Graph.backgroundColor));
		graphics.fillRect(0, 0, Graph.getInstance().viewport.width,
			Graph.getInstance().viewport.height);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param width
     * @param height
     * @param color
     */
    public static void FloodFill(final int width, final int height,
	    final int color) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		SetColor(color);
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		takeCareOfXorMode(graphics);
		graphics.setPaint(getColor(Graph.color));
		graphics.fillRect(0, 0, width, height);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param pos
     *            <code>int</code>
     * @return {@link Color}
     */
    private static Color getColor(int pos) {
	if (pos > (Graph.allPalette.Size - 1)) {
	    throw new IllegalArgumentException("pos [" + pos
		    + "] exceeded all palette size [" + Graph.allPalette.Size
		    + "]!");
	}
	return Graph.rgbPalette[Graph.allPalette.Colors[pos]];
    }

    /**
     * @return {@link Graph} the instance of this singleton.
     */
    public static Graph getInstance() {
	if (Graph.instance == null) {
	    Graph.instance = new Graph();
	}
	return Graph.instance;
    }

    /**
     * COMMENT.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public static void Line(final int x1, final int y1, final int x2,
	    final int y2) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		takeCareOfXorMode(graphics);
		graphics.setPaint(getColor(Graph.color));
		graphics.drawLine(x1, y1, x2, y2);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param x
     * @param y
     * @param string
     */
    public static void OutTextXY(final int x, final int y, final String string) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		takeCareOfXorMode(graphics);
		graphics.setPaint(getColor(Graph.color));
		graphics.setFont(Graph.DEFAULT_FONT);
		graphics.drawString(string, x, y);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * @return <code>char</code> the last key read.
     */
    public static char ReadKey() {
	Graph.lastKey = 0;
	while (Graph.lastKey == 0) {
	    try {
		Thread.sleep(200);
	    } catch (final InterruptedException e1) {
		Graph.LOG.error(e1.getLocalizedMessage(),
			Graph.LOG.isDebugEnabled() ? e1 : null);
	    }
	}
	if (Graph.LOG.isInfoEnabled()) {
	    Graph.LOG.info("read key: " + Graph.lastKey);
	}
	return Graph.lastKey;
    }

    /**
     * COMMENT.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public static void Rectangle(final int x1, final int y1, final int x2,
	    final int y2) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		takeCareOfXorMode(graphics);
		graphics.setPaint(getColor(Graph.color));
		graphics.drawRect(x1, y1, x2 - x1, y2 - y1);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param allPalletteRef
     */
    public static void SetAllPalette(final PaletteType allPalletteRef) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.allPalette = allPalletteRef;
	    }
	});
    }

    /**
     * @param color
     *            <code>int</code>
     */
    public static void SetBkColor(final int color) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.backgroundColor = color;
		final Graphics2D graphics = Graph.getInstance()
			.getBufferGraphics();
		graphics.setPaint(getColor(Graph.backgroundColor));
		graphics.fillRect(0, 0, Graph.GetMaxX, Graph.GetMaxY);
		Graph.getInstance().repaint();
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param colorRef
     */
    public static void SetColor(final int colorRef) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.color = colorRef;
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param solidfill
     * @param ausgfColor
     */
    public static void SetFillStyle(final FillStyle solidfill,
	    final int ausgfColor) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.fillStyle = solidfill;
		Graph.color = ausgfColor;
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param pos
     * @param red
     * @param green
     * @param blue
     */
    public static void SetRGBPalette(final int pos, final int red,
	    final int green, final int blue) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.rgbPalette[pos] = new EGAColor(red, green, blue);
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param Clip
     */
    public static void SetViewPort(final int x1, final int y1, final int x2,
	    final int y2, final boolean Clip) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.getInstance().viewport = new Rectangle(x1, y1, x2 - x1,
			y2 - y1);
		Graph.getInstance().clip = Clip;
	    }
	});
    }

    /**
     * COMMENT.
     * 
     * @param writeModeRef
     *            WriteMode;
     */
    public static void SetWriteMode(final WriteMode writeModeRef) {
	SwingThreadAdapter.runInSwingThread(new Runnable() {
	    @Override
	    public void run() {
		Graph.writeMode = writeModeRef;
		takeCareOfXorMode(Graph.getInstance().getBufferGraphics());
	    }

	});
    }

    /**
     * COMMENT.
     */
    private static void takeCareOfXorMode(Graphics2D g2d) {
	switch (writeMode) {
	case XorPut:
	    // g2d.setXORMode(Color.white);
	    break;
	case NormalPut:
	    // g2d.setXORMode(Color.black);
	default:
	    break;
	}
    }

    /**
     * COMMENT.
     * 
     * @param string
     * @return
     */
    public static int TextHeight(String string) {
	return Graph.getInstance().getGraphics().getFontMetrics().getHeight();
    }

    /**
     * COMMENT.
     * 
     * @param string
     * @return
     */
    public static double TextWidth(String string) {
	final Graphics2D graphics = Graph.getInstance().getBufferGraphics();
	graphics.setFont(Graph.DEFAULT_FONT);
	return graphics.getFontMetrics().getStringBounds(string, graphics)
		.getWidth();
    }

    /**
     * {@link boolean} COMMENT.
     */
    @SuppressWarnings("unused")
    private boolean clip;

    /**
     * {@link Image} COMMENT.
     */
    private final Image internalImage;

    /**
     * {@link Rectangle} COMMENT.
     */
    Rectangle viewport;

    /**
     * {@link KeyListener} COMMENT.
     */
    private static KeyListener lastKeyKeyListener = new KeyListener() {

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	    Graph.lastKey = e.getKeyChar();
	    if (Graph.LOG.isDebugEnabled()) {
		Graph.LOG.debug("read key: " + Graph.lastKey);
	    }
	}
    };

    /**
     * COMMENT.
     */
    public Graph() {
	final Dimension d = new Dimension(Graph.GetMaxX, Graph.GetMaxY);
	setSize(d);
	setMinimumSize(d);
	setMaximumSize(d);
	setPreferredSize(d);

	final GraphicsEnvironment ge = GraphicsEnvironment
		.getLocalGraphicsEnvironment();
	final GraphicsDevice gs = ge.getDefaultScreenDevice();
	final GraphicsConfiguration gc = gs.getDefaultConfiguration();
	// Create an image that supports arbitrary levels of transparency
	this.internalImage = gc.createCompatibleImage(Graph.GetMaxX,
		Graph.GetMaxY, Transparency.OPAQUE);
    }

    /**
     * @return {@link Graphics2D}
     */
    public Graphics2D getBufferGraphics() {
	if (this.viewport != null) {
	    return (Graphics2D) ((BufferedImage) this.internalImage)
		    .createGraphics().create(this.viewport.x, this.viewport.y,
			    this.viewport.x + this.viewport.width,
			    this.viewport.y + this.viewport.height);
	} else {
	    return ((BufferedImage) this.internalImage).createGraphics();
	}
    }

    /**
     * @param g
     *            {@link Graphics}
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
	g.drawImage(this.internalImage, 0, 0, this);
    }

    /**
     * COMMENT.
     * 
     * @param titel
     *            {@link String}
     */
    public void showUI(final String titel) {
	final JFrame jFrame = new JFrame(titel);
	jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	jFrame.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		System.exit(0);
	    }
	});
	jFrame.getContentPane().add(Graph.getInstance());
	jFrame.setResizable(false);
	jFrame.addKeyListener(Graph.lastKeyKeyListener);
	jFrame.pack();
	GUIToolkit.center(jFrame);
	jFrame.setVisible(true);
    }

}
//
// $Log: $
//
