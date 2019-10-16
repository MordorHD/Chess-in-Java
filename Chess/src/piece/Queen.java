package piece;

import java.util.ArrayList;
import java.util.List;

import main.Board;

public final class Queen extends Piece {
	private final int[][] OFFSET = { { -1, 1 }, { -1, -1 }, { 1, 1 }, { 1, -1 }, { 0, 1 }, { 0, -1 }, { 1, 0 },
			{ -1, 0 } };

	public Queen(Board board, int x, int y, Side side) {
		super(board, x, y, side);
	}
	public Queen(Board board, Piece other) {
		super(board, other);
	}
	@Override
	public List<Move> rawmoves() {
		List<Move> moves = new ArrayList<Move>();
		for (int[] i : OFFSET)
			for (int mult = 1; mult < 8; mult++) {
				final int x = getX() + i[0] * mult;
				final int y = getY() + i[1] * mult;

				if (!board.inBounds(x, y))
					break;

				final Move move = new Move(this, x, y);

				if (!move.tile.isOccupied())
					moves.add(new Move(this, move.x, move.y));
				else if (move.tile.getPiece().getSide() == getSide())
					break;
				else {
					moves.add(new Move(this, move.x, move.y));
					break;
				}

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
