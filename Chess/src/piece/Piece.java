package piece;

import java.awt.Graphics2D;
import java.util.List;

import main.Board;
import main.Constants;
import main.ImageContainer;
import main.Tile;
import player.GamePanel;

public abstract class Piece {
	private int x, y;
	private final Side side;
	private int index;
	public int moved;
	Board board;

	Piece(Board board, int x, int y, Side side) {
		this.x = x;
		this.y = y;
		this.side = side;
		this.board = board;
		board.tiles[x][y] = new Tile.occupiedTile(board);
	}

	public Piece(Board board, Piece other) {
		x = other.x;
		y = other.y;
		index = other.index;
		side = other.side;
		moved = other.moved;
		this.board = board;
	}

	// rawmoves are all the squares that are attacked by a piece
	public abstract List<Move> rawmoves();

	// just regular moves
	public abstract List<Move> moves();

	public void render(Graphics2D g) {
		GamePanel.drawCenteredImage(g, ImageContainer.getTexturebyPiece(this), x * Constants.SQS + Constants.SQS / 2,
				y * Constants.SQS + Constants.SQS / 2);
	}

	public void movePiece(int x, int y) {

		try {
			board.tiles[this.x][this.y] = new Tile.emptyTile(board);
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		try {
			board.tiles[x][y] = new Tile.occupiedTile(board);
		} catch (ArrayIndexOutOfBoundsException e) {

		}

		this.x = x;
		this.y = y;
	}

	public void voidOut() {
		try {
			board.tiles[x][y] = new Tile.emptyTile(board);
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		x = -3;
		y = -3;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Side getSide() {
		return side;
	}

	public boolean isWhite() {
		return side.isWhite();
	}

	public boolean isOppositeSide(Piece other) {
		return other.side != side;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
