package player;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane parent;
	private JPanel contentPanel;

	private AbstractAction deleteTabAction = new AbstractAction("X") {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (HeaderPanel.this.parent != null) {
				for (int i = 0; i < HeaderPanel.this.parent.getTabCount(); i++) {
					if (HeaderPanel.this.parent.getComponentAt(i) == contentPanel) {
						HeaderPanel.this.parent.removeTabAt(i);
					}
				}
			}
		}
	};

	public HeaderPanel(JTabbedPane parent, JPanel contentPanel, String title) {
		this.parent = parent;
		this.contentPanel = contentPanel;

		this.setLayout(new FlowLayout());
		this.add(new JLabel(title));
		this.add(new JButton(deleteTabAction));
		this.setOpaque(false);
	}
}
