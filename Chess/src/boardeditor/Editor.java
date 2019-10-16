package boardeditor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import main.ImageContainer;

public class Editor extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	private final Board board;

	public static int mouseX, mouseY;
	
	public Editor(main.Board board2) {
		this.board = new Board(board2);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(java.awt.Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		board.renderBoard(g2);

		final int size = 50;

		for (int i = 0; i < 12; i++)
			g2.drawImage(ImageContainer.values()[i].getTexture().getScaledInstance(size, size, Image.SCALE_SMOOTH),
					main.Constants.SQS * 8 + size * (i % 2) + size, size * (i / 2), null);

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		board.declareMovingPiece(e);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		board.mouseReleased(e);
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

}
