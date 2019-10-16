package piece;

import java.util.ArrayList;
import java.util.List;

import main.Board;

public final class King extends Piece {
	private final int[][] OFFSET = { { -1, 1 }, { -1, -1 }, { 1, 1 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { 1, 0 },
			{ -1, 0 } };

	public King(Board board, int x, int y, Side side) {
		super(board, x, y, side);
	}

	public King(Board board, Piece other) {
		super(board, other);
	}

	private Move shortCastle() {
		if (moved < 1)
			if (board.pieces[getIndex() - 2].moved < 1)
				if (!board.tiles[getX() + 1][getY()].isOccupied() && !board.tiles[getX() + 2][getY()].isOccupied())
					if (Move.tryMove(board, this, new Move(this, getX() + 2, getY())) != null
							&& Move.tryMove(board, this, new Move(this, getX() + 1, getY())) != null)
						return new Move(this, getX() + 2, getY());

		return null;
	}

	private Move longCastle() {
		if (moved < 1)
			if (board.pieces[getIndex() - 3].moved < 1)
				if (!board.tiles[getX() - 1][getY()].isOccupied() && !board.tiles[getX() - 2][getY()].isOccupied()
						&& !board.tiles[getX() - 3][getY()].isOccupied())
					if (Move.tryMove(board, this, new Move(this, getX() - 2, getY())) != null
							&& Move.tryMove(board, this, new Move(this, getX() - 1, getY())) != null)
						return new Move(this, getX() - 2, getY());

		return null;
	}

	@Override
	public List<Move> rawmoves() {
		List<Move> moves = new ArrayList<Move>();
		for (int[] i : OFFSET) {
			final int x = getX() + i[0];
			final int y = getY() + i[1];

			if (!board.inBounds(x, y))
				continue;

			final Move move = new Move(this, getX() + i[0], getY() + i[1]);

			if (!move.tile.isOccupied() || move.tile.getPiece().getSide() != getSide())
				moves.add(new Move(this, move.x, move.y));

		}
		return moves;
	}

	@Override
	public List<Move> moves() {
		List<Move> moves = new ArrayList<Move>();
		for (Move move : rawmoves())
			if (Move.tryMove(board, this, new Move(this, move.x, move.y)) != null)
				moves.add(new Move(this, move.x, move.y));
		if (!inCheck()) {
			if (shortCastle() != null)
				moves.add(shortCastle());
			if (longCastle() != null)
				moves.add(longCastle());
		}
		return moves;
	}

	public boolean inCheck() {
		for (Piece piece : board.pieces)
			if (piece.getSide() != this.getSide())
				for (Move move : piece.rawmoves())
					if (move.x == getX() && move.y == getY())
						return true;
		return false;

	}

}
