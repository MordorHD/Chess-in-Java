package player;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import main.Board;
import main.Constants;
import main.ImageContainer;
import main.Tile;
import piece.King;
import piece.Move;
import piece.Pawn;
import piece.Piece;
import piece.Side;

public class Player {
	public Piece currentPiece;

	public Side side;

	public Board board;

	private Move LastMove;

	public Player(Side side) {
		this.side = side;
		this.board = new Board(this);
	}

	private List<Move> getMoves() {
		if (currentPiece == null)
			return new ArrayList<Move>();
		return currentPiece.moves();
	}

	private int[] getMouse(MouseEvent e) {
		return new int[] { e.getX() / Constants.SQS, e.getY() / Constants.SQS };
	}

	public void mousePressed(MouseEvent e) {
		Tile pressedTile = board.tiles[getMouse(e)[0]][getMouse(e)[1]];

		if (!pressedTile.isOccupied() || pressedTile.getPiece().getSide() != side)
			return;

		currentPiece = pressedTile.getPiece();

		board.setCurrPosition(new int[] { currentPiece.getX(), currentPiece.getY() });
	}

	public void movePiece(MouseEvent e) {
		if (currentPiece == null)
			return;

		for (Move move : getMoves())
			if (getMouse(e)[0] == move.x && getMouse(e)[1] == move.y) {

				setMove(new Move(currentPiece, move.x, move.y, move.tile.getPiece()));

				if (move.tile.isOccupied())
					move.tile.getPiece().voidOut();

				if (currentPiece instanceof King && Math.abs(currentPiece.getX() - move.x) == 2)
					if (move.x == 6)
						board.pieces[currentPiece.getIndex() - 2].movePiece(5, currentPiece.getY());
					else
						board.pieces[currentPiece.getIndex() - 3].movePiece(3, currentPiece.getY());

				board.setLastPosition(new int[] { currentPiece.getX(), currentPiece.getY() });
				board.setLatestPosition(new int[] { move.x, move.y });

				if (board.enPassant != null && currentPiece instanceof Pawn && move.tile == board.enPassant.tile)
					board.enPassantTarget.voidOut();

				board.enPassant = null;
				board.enPassantTarget = null;

				if (currentPiece instanceof Pawn && Math.abs(currentPiece.getY() - move.y) == 2) {
					board.enPassant = new Move(currentPiece, move.x, move.y - currentPiece.getSide().getDirection());
					board.enPassantTarget = currentPiece;
				}

				currentPiece.movePiece(move.x, move.y);
				currentPiece.moved++;

				if (side.isWhite())
					side = Side.BLACK;
				else
					side = Side.WHITE;

				if (board.inCheckMate()) {
					String str = (Side) board.inCheck()[1] == Side.WHITE ? "Black wins!" : "White wins!";
					JOptionPane.showConfirmDialog(null, str, "Checkmate", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE);
					currentPiece = null;
					return;
				}
				if (board.inStaleMate()) {
					JOptionPane.showConfirmDialog(null, "Draw!", "Stalemate", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		currentPiece = null;
		board.setCurrPosition(null);
	}

	public Move getMove() {
		return LastMove;
	}

	public void setMove(Move lastMove) {
		LastMove = lastMove;
	}

	public void render(Graphics2D g) {
		if (currentPiece == null)
			return;
		for (Move move : getMoves()) {
			if (!move.tile.isOccupied())
				GamePanel.drawCenteredCircle(g, move.x * Constants.SQS + Constants.SQS / 2,
						move.y * Constants.SQS + Constants.SQS / 2, Constants.SQS / 4);
			else
				GamePanel.drawCenteredSquare(g, move.x * Constants.SQS + Constants.SQS / 2,
						move.y * Constants.SQS + Constants.SQS / 2, Constants.SQS, 10);
		}

		GamePanel.drawCenteredImage(g, ImageContainer.getTexturebyPiece(currentPiece), GamePanel.mouseX,
				GamePanel.mouseY);

	}

}
