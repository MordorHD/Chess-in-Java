package boardeditor;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import main.Constants;
import main.ImageContainer;
import main.Tile;
import piece.*;
import player.GamePanel;

public class Board extends main.Board {

	piece.Piece movingPiece;

	public Board(main.Board board) {
		super(board);
	}

	@Override
	public void renderBoard(Graphics2D g) {
		g.drawImage(ImageContainer.getTexture(ImageContainer.CHESSBOARD), 0, 0, null);

		for (Piece piece : pieces)
			if (piece != movingPiece)
				piece.render(g);
			else {
				GamePanel.drawCenteredImage(g, ImageContainer.getTexturebyPiece(movingPiece), Editor.mouseX,
						Editor.mouseY);
			}
	}

	private int[] getMouse(MouseEvent e) {
		return new int[] { e.getX() / Constants.SQS, e.getY() / Constants.SQS };
	}

	public void declareMovingPiece(MouseEvent e) {
		Piece[][] pieces = { {new Pawn(this, 0, 0, Side.WHITE)} };
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 6; j++)
				if (e.getX() > main.Constants.SQS * 8 + 50 * i + 50 && e.getY() > 50 * j
						&& e.getX() < main.Constants.SQS * 8 + 50 * i + 100 && e.getY() < 50 * j + 50)
					movingPiece = pieces[i][j];

		Tile pressedTile = tiles[getMouse(e)[0]][getMouse(e)[1]];

		if (!pressedTile.isOccupied())
			return;

		movingPiece = pressedTile.getPiece();
	}

	public void mouseReleased(MouseEvent e) {
		if (movingPiece == null)
			return;
		Tile pressedTile = tiles[getMouse(e)[0]][getMouse(e)[1]];
		if (pressedTile.isOccupied())
			pressedTile.getPiece().voidOut();

		movingPiece.movePiece(getMouse(e)[0], getMouse(e)[1]);

		movingPiece = null;
	}
}
