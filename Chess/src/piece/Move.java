package piece;

import main.Board;
import main.Tile;

public final class Move {
	public final int x, y;
	public final int px, py;
	public final Tile tile;
	public final Piece piece;
	public final Piece takenpiece;

	private final static int CASTLE_SHORT = 1;
	private final static int CASTLE_LONG = 2;

	private boolean check;

	private boolean checkmate;

	private int castle = 0;

	public Move(Piece piece, int x, int y) {
		this.x = x;
		this.y = y;
		this.px = piece.getX();
		this.py = piece.getY();
		this.piece = piece;
		tile = piece.board.tiles[x][y];
		takenpiece = tile.getPiece();
	}

	// constructor for player class
	public Move(Piece piece, int x, int y, Piece takenpiece) {
		this.x = x;
		this.y = y;

		this.px = piece.getX();
		this.py = piece.getY();

		this.piece = piece;
		if (piece instanceof King && Math.abs(px - x) == 2)
			if (x == 6)
				castle = CASTLE_SHORT;
			else
				castle = CASTLE_LONG;
		tile = piece.board.tiles[x][y];

		this.takenpiece = takenpiece;
	}

	public static Move tryMove(Board board, Piece piece, Move move) {

		Board testboard = new Board(board);

		Tile blockedTile = null;

		for (Tile[] arr : testboard.tiles)
			for (Tile tile : arr)
				if (tile.getX() == move.x && tile.getY() == move.y && tile.isOccupied())
					blockedTile = tile;

		if (piece instanceof Pawn && testboard.enPassant != null && move.x == testboard.enPassant.x
				&& move.y == testboard.enPassant.y) {
			testboard.enPassantTarget.voidOut();
		}

		if (blockedTile != null) {
			blockedTile.getPiece().voidOut();
		}

		testboard.pieces[piece.getIndex()].movePiece(move.x, move.y);

		Piece alliedKing = piece.getSide() == Side.WHITE ? testboard.pieces[15] : testboard.pieces[31];

		return ((King) alliedKing).inCheck() ? null : move;
	}

	public String toString() {
		check = (boolean) piece.board.inCheck()[0];

		checkmate = piece.board.inCheckMate();

		final String check = this.checkmate ? "#" : this.check ? "+" : "";

		if (castle == CASTLE_SHORT)
			return "O-O" + check;
		if (castle == CASTLE_LONG)
			return "O-O-O" + check;

		final char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
		final String PieceLetter = piece instanceof Pawn ? ""
				: piece instanceof Knight ? "N" : Character.toString(piece.getClass().getSimpleName().charAt(0));
		for (Piece piece : this.piece.board.pieces)
			if (piece.getX() != this.x && piece.getY() != this.y)
				if (piece.getClass() == this.piece.getClass())
					;

		String taken = "";
		if (takenpiece != null)
			taken = "x";
		if (piece instanceof Pawn && taken == "x")
			taken = letters[px] + "x";
		return PieceLetter + taken + letters[x] + Math.abs(y - 8) + check;
	}

	public Piece getPiece() {
		return piece;
	}
}
