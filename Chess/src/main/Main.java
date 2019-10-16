package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import player.GamePanel;
import player.HeaderPanel;
import player.Player;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	public static JTabbedPane jtp = new JTabbedPane();

	Main() {
		super("Chess in Java");
		getContentPane().add(jtp);

		addTab(new GamePanel(new Player(piece.Side.WHITE)), "Game");

		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Window");
		JMenuItem newgame = new JMenuItem("New game");
		newgame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addTab(new GamePanel(new Player(piece.Side.WHITE)), "Game");
			}

		});
		JMenuItem editor = new JMenuItem("Editor");
		editor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addTab(new boardeditor.Editor(new main.Board(new Player(null))), "Editor");
			}

		});
		menu.add(newgame);
		menu.add(editor);
		menubar.add(menu);
		setJMenuBar(menubar);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		
	}

	public static void addTab(JPanel game, String name) {
		game.setOpaque(false);
		jtp.add(game);
		jtp.setTabComponentAt(jtp.indexOfComponent(game), new HeaderPanel(jtp, game, name));
	}

	public static void main(String[] args) {
		ImageContainer.init_resize();
		new Main();
	}

}
