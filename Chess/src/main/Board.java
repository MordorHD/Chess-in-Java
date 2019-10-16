package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import piece.*;
import player.GamePanel;
import player.Player;

public class Board {

	public Piece[] pieces;

	public Tile[][] tiles;

	private int[] lastPosition;

	private int[] latestPosition;

	private int[] currPosition;

	public Move enPassant;

	public Piece enPassantTarget;

	private Player player;

	public Board(Player player) {
		this();
		this.player = player;
	}

	private Board() {
		tiles = new Tile[8][8];

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				tiles[i][j] = new Tile.emptyTile(this);

		pieces = new Piece[32];

		for (int i = 0; i < 8; i++)
			pieces[i] = (new Pawn(this, i, 6, Side.WHITE)); // WHITE PAWNS 0-7
		pieces[8] = (new Bishop(this, 2, 7, Side.WHITE)); // WHITE BISHOP 8
		pieces[9] = (new Bishop(this, 5, 7, Side.WHITE)); // WHITE BISHOP 9
		pieces[10] = (new Knight(this, 1, 7, Side.WHITE)); // WHITE KNIGHT 10
		pieces[11] = (new Knight(this, 6, 7, Side.WHITE)); // WHITE KNIGHT 11
		pieces[12] = (new Rook(this, 0, 7, Side.WHITE)); // WHITE ROOK 12
		pieces[13] = (new Rook(this, 7, 7, Side.WHITE)); // WHITE ROOK 13
		pieces[14] = (new Queen(this, 3, 7, Side.WHITE)); // WHITE QUEEN 14
		pieces[15] = (new King(this, 4, 7, Side.WHITE)); // WHITE KING 15

		for (int i = 0; i < 8; i++)
			pieces[16 + i] = (new Pawn(this, i, 1, Side.BLACK)); // BLACK PAWNS 16-23
		pieces[24] = (new Bishop(this, 2, 0, Side.BLACK)); // BLACK BISHOP 24
		pieces[25] = (new Bishop(this, 5, 0, Side.BLACK)); // BLACK BISHOP 25
		pieces[26] = (new Knight(this, 1, 0, Side.BLACK)); // BLACK KNIGHT 26
		pieces[27] = (new Knight(this, 6, 0, Side.BLACK)); // BLACK KNIGHT 27
		pieces[28] = (new Rook(this, 0, 0, Side.BLACK)); // BLACK ROOK 28
		pieces[29] = (new Rook(this, 7, 0, Side.BLACK)); // BLACK ROOK 29
		pieces[30] = (new Queen(this, 3, 0, Side.BLACK)); // BLACK QUEEN 30
		pieces[31] = (new King(this, 4, 0, Side.BLACK)); // BLACK KING 31
		
		for(int i = 0; i < 32; i++)
			pieces[i].setIndex(i);
	}

	public Board(Board other) {
		Piece[] pieces = new Piece[32];

		for (int i = 0; i < 32; i++) {
			if (other.pieces[i] instanceof Pawn)
				pieces[i] = new Pawn(this, other.pieces[i]);
			if (other.pieces[i] instanceof Knight)
				pieces[i] = new Knight(this, other.pieces[i]);
			if (other.pieces[i] instanceof Bishop)
				pieces[i] = new Bishop(this, other.pieces[i]);
			if (other.pieces[i] instanceof Rook)
				pieces[i] = new Rook(this, other.pieces[i]);
			if (other.pieces[i] instanceof Queen)
				pieces[i] = new Queen(this, other.pieces[i]);
			if (other.pieces[i] instanceof King)
				pieces[i] = new King(this, other.pieces[i]);
		}

		if (other.lastPosition != null) {
			int[] lastPosition = new int[] { other.lastPosition[0], other.lastPosition[1] };
			this.lastPosition = lastPosition;
		}
		if (other.latestPosition != null) {
			int[] latestPosition = new int[] { other.latestPosition[0], other.latestPosition[1] };
			this.latestPosition = latestPosition;
		}

		if (other.currPosition != null) {
			int[] currPosition = new int[] { other.currPosition[0], other.currPosition[1] };
			this.currPosition = currPosition;
		}
		if (other.enPassant != null) {
			Move enPassant = new Move(other.enPassant.getPiece(), other.enPassant.x, other.enPassant.y);
			this.enPassant = enPassant;
		}
		Piece enPassantTarget = null;

		if (other.enPassantTarget instanceof Pawn)
			enPassantTarget = new Pawn(this, other.enPassantTarget);
		if (other.enPassantTarget instanceof Knight)
			enPassantTarget = new Knight(this, other.enPassantTarget);
		if (other.enPassantTarget instanceof Bishop)
			enPassantTarget = new Bishop(this, other.enPassantTarget);
		if (other.enPassantTarget instanceof Rook)
			enPassantTarget = new Rook(this, other.enPassantTarget);
		if (other.enPassantTarget instanceof Queen)
			enPassantTarget = new Queen(this, other.enPassantTarget);
		if (other.enPassantTarget instanceof King)
			enPassantTarget = new King(this, other.enPassantTarget);

		this.enPassantTarget = enPassantTarget;

		this.pieces = pieces;

		Tile[][] tiles = new Tile[8][8];

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (other.tiles[i][j] instanceof Tile.emptyTile)
					tiles[i][j] = new Tile.emptyTile(this);
				else
					tiles[i][j] = new Tile.occupiedTile(this);

		this.tiles = tiles;
	}

	public boolean inBounds(int x, int y) {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}

	public void renderBoard(Graphics2D g) {
		g.drawImage(ImageContainer.getTexture(ImageContainer.CHESSBOARD), 0, 0, null);

		g.setColor(new Color(0, 125, 0, 100));

		if (lastPosition != null)
			GamePanel.drawFilledCenteredSquare(g, lastPosition[0] * Constants.SQS + Constants.SQS / 2,
					lastPosition[1] * Constants.SQS + Constants.SQS / 2, Constants.SQS);
		if (latestPosition != null)
			GamePanel.drawFilledCenteredSquare(g, latestPosition[0] * Constants.SQS + Constants.SQS / 2,
					latestPosition[1] * Constants.SQS + Constants.SQS / 2, Constants.SQS);
		if (currPosition != null)
			GamePanel.drawFilledCenteredSquare(g, currPosition[0] * Constants.SQS + Constants.SQS / 2,
					currPosition[1] * Constants.SQS + Constants.SQS / 2, Constants.SQS);

		for (Piece piece : pieces)
			if (piece != player.currentPiece)
				piece.render(g);
			else {
				BufferedImage tmpImg = new BufferedImage(ImageContainer.getTexturebyPiece(piece).getWidth(null),
						ImageContainer.getTexturebyPiece(piece).getHeight(null), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = (Graphics2D) tmpImg.getGraphics();
				g2d.setComposite(AlphaComposite.SrcOver.derive(0.3f));
				g2d.drawImage(ImageContainer.getTexturebyPiece(piece), 0, 0, null);
				GamePanel.drawCenteredImage(g, tmpImg, piece.getX() * Constants.SQS + Constants.SQS / 2,
						piece.getY() * Constants.SQS + Constants.SQS / 2);
			}
	}

	public Object[] inCheck() {
		if (((King) pieces[15]).inCheck())
			return new Object[] { true, Side.WHITE };
		if (((King) pieces[31]).inCheck())
			return new Object[] { true, Side.BLACK };
		return new Object[] { false, null };
	}

	public boolean inStaleMate() {
		Side side = player.side;
		List<Move> moves = new ArrayList<Move>();
		for (Piece piece : pieces) {
			if (piece.getSide() == side) {
				moves.addAll(piece.moves());
			}
		}
		return moves.size() < 1;
	}

	public boolean inCheckMate() {
		return (inStaleMate() && (boolean) inCheck()[0]);
	}

	public void setLatestPosition(int[] latestPosition) {
		this.latestPosition = latestPosition;
	}

	public void setLastPosition(int[] lastPosition) {
		this.lastPosition = lastPosition;
	}

	public void setCurrPosition(int[] currPosition) {
		this.currPosition = currPosition;
	}

}
