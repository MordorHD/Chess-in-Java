package main;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import piece.Side;

public enum ImageContainer {
	White_Pawn("images/White_Pawn.png"), Black_Pawn("images/Black_Pawn.png"), White_Bishop("images/White_Bishop.png"),
	Black_Bishop("images/Black_Bishop.png"), White_Knight("images/White_Knight.png"),
	Black_Knight("images/Black_Knight.png"), White_Rook("images/White_Rook.png"), Black_Rook("images/Black_Rook.png"),
	White_Queen("images/White_Queen.png"), Black_Queen("images/Black_Queen.png"), White_King("images/White_King.png"),
	Black_King("images/Black_King.png"), Chessboard("images/Chessboard.PNG");

	Image img;

	private String filepath;

	ImageContainer(String filepath) {
		this.filepath = filepath;
		try {
			img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
		}
	}

	public static void init_resize() {
		for (int i = 0; i < 12; i++)
			values()[i].img = values()[i].img.getScaledInstance(Constants.SQS * 8 / 10, Constants.SQS * 8 / 10,
					Image.SCALE_SMOOTH);
	}

	private String getFilepath() {
		return filepath;
	}

	public Image getTexture() {
		return img;
	}

	public static Image getTexture(int i) {
		return values()[i].getTexture();
	}

	public static Image getTexturebyPiece(piece.Piece piece) {
		String side = piece.getSide() == Side.WHITE ? "White" : "Black";
		for (ImageContainer img : values())
			if (img.getFilepath().split("/")[1].split("_")[0].equals(side))
				if (img.getFilepath().split("_")[1].split(".png")[0].equals(piece.getClass().getSimpleName()))
					return img.getTexture();
		return null;
	}

	public final static int CHESSBOARD = 12;
}
