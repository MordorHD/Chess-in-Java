package piece;


public enum Side {

	WHITE() {

		@Override
		public boolean isWhite() {
			return true;
		}

		@Override
		public int getDirection() {
			return DIRECTION_WHITE;
		}

	},
	BLACK() {

		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public int getDirection() {
			return DIRECTION_BLACK;
		}

	};

	public abstract boolean isWhite();

	public abstract int getDirection();

	private final static int DIRECTION_WHITE = -1;
	private final static int DIRECTION_BLACK = 1;

}
