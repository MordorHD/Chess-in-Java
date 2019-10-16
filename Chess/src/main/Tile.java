package main;

import piece.Piece;

public abstract class Tile {

	public abstract boolean isOccupied();

	public abstract Piece getPiece();

	protected final Board board;

	public Tile(Board board) {
		this.board = board;
	}

	private int[] getPos() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (this == board.tiles[i][j])
					return new int[] { i, j };
		return null;
	}

	public int getX() {
		return getPos()[0];
	}

	public int getY() {
		return getPos()[1];
	}
	public static final class occupiedTile extends Tile {

		public occupiedTile(Board board) {
			super(board);
		}

		@Override
		public boolean isOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			for (Piece piece : board.pieces)
				if (piece.getX() == getX() && piece.getY() == getY())
					return piece;
			return null;
		}
	}
	public static final class emptyTile extends Tile {

		public emptyTile(Board board) {
			super(board);
		}

		@Override
		public boolean isOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}

	}
}
