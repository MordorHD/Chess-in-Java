package piece;

import java.util.ArrayList;
import java.util.List;

import main.Board;

public final class Knight extends Piece {

	private final int[][] OFFSET = { { -2, 1 }, { -2, -1 }, { 2, 1 }, { 2, -1 }, { -1, -2 }, { -1, 2 }, { 1, -2 },
			{ 1, 2 } };

	public Knight(Board board, int x, int y, Side side) {
		super(board, x, y, side);
	}
	public Knight(Board board, Piece other) {
		super(board, other);
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
			if (Move.tryMove(board, this,  new Move(this, move.x, move.y)) != null)
				moves.add(new Move(this, move.x, move.y));
		return moves;
	}

}
