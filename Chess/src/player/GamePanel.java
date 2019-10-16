package player;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import piece.Side;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;

	public static int mouseX, mouseY;

	public final Player player;

	JTable moves;

	public GamePanel(Player player) {
		setLayout(null);
		this.player = player;
		addMouseListener(this);
		addMouseMotionListener(this);

		moves = new JTable(new DefaultTableModel(new Object[] { "White", "Black" }, 0)) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		JScrollPane spTable = new JScrollPane(moves);
		spTable.setBounds(800, 0, 210, 800);
		add(spTable);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(1000, 786);
	}

	public static void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		g.fillOval(x - r / 2, y - r / 2, r, r);
	}

	public static void drawFilledCenteredSquare(Graphics2D g, int x, int y, int r) {
		g.fillRect(x - r / 2, y - r / 2, r, r);
	}

	public static void drawCenteredImage(Graphics2D g, Image img, int x, int y) {
		g.drawImage(img, x - img.getWidth(null) / 2, y - img.getHeight(null) / 2, null);
	}

	public static void drawCenteredSquare(Graphics2D g, int x, int y, int r, int sr) {
		g.setStroke(new BasicStroke(sr));
		g.drawRect(x - r / 2 + sr / 2, y - r / 2 + sr / 2, r - sr, r - sr);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		player.board.renderBoard(g2);
		player.render(g2);
		if (player.getMove() != null) {

			DefaultTableModel model = (DefaultTableModel) moves.getModel();

			if (player.getMove().piece.getSide() == Side.WHITE)
				model.addRow(new Object[] { player.getMove().toString(), "" });
			else {
				String save = (String) model.getValueAt( moves.getRowCount() - 1, 0);
				model.removeRow(moves.getRowCount() - 1);

				System.out.println(save);

				model.addRow(new Object[] { save, player.getMove().toString() });
			}

			player.setMove(null);
		}
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
		player.mousePressed(e);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		player.movePiece(e);
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
