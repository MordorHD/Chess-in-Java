package piece;

import java.util.ArrayList;
import java.util.List;

import main.Board;

public final class Pawn extends Piece {

	public Pawn(Board board, int x, int y, Side side) {
		super(board, x, y, side);
	}
	public Pawn(Board board, Piece other) {
		super(board, other);
	}
	@Override
	public List<Move> rawmoves() {
		List<Move> moves = new ArrayList<Move>();

		for (int i = -1; i <= 1; i += 2) {
			final int x = getX() + i;
			final int y = getY() + getSide().getDirection();

			if (!board.inBounds(x, y))
				continue;

			final Move move = new Move(this, x, y);

			moves.add(new Move(this, move.x, move.y));
		}
		return moves;
	}

	@Override
	public List<Move> moves() {
		List<Move> moves = new ArrayList<Move>();
		for (int i = 1; i <= (moved < 1 ? 2 : 1); i++) {
			final int x = getX();
			final int y = getY() + getSide().getDirection() * i;
			
			if(!board.inBounds(x, y))
				continue;

			final Move move = new Move(this, x, y);

			if (!move.tile.isOccupied() && Move.tryMove(board, this,  new Move(this, move.x, move.y)) != null)
				moves.add(new Move(this, move.x, move.y));
			else
				break;
		}
		for (int i = -1; i <= 1; i += 2) {
			final int x = getX() + i;
			final int y = getY() + getSide().getDirection();

			if (!board.inBounds(x, y))
				continue;

			final Move move = new Move(this, x, y);

			if (((move.tile.isOccupied() && move.tile.getPiece().getSide() != getSide())
					|| (board.enPassant != null && move.tile == board.enPassant.tile))
					&& Move.tryMove(board, this,  new Move(this, move.x, move.y)) != null)
				moves.add(new Move(this, move.x, move.y));
		}

		return moves;
	}

}
