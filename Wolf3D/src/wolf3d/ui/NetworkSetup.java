/**
 *
 */
package wolf3d.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Simon Brannigan
 *
 */
public class NetworkSetup extends JFrame {
	private String ip;
	private int port;

	public static void main(String[] args){
		NetworkSetup ns = new NetworkSetup();
	}

	public NetworkSetup(){
		/*Basic frame setup*/
		JFrame frame = new JFrame();
		frame.setTitle("Network setup");
		frame.setSize(300, 500);
		frame.setResizable(false);
        JPanel panel = new JPanel();

        /*Instruction labels*/
        JLabel instruct = new JLabel("If single player, hit execute.");
        instruct.setVisible(true);
        JLabel labelIP = new JLabel("Enter the IP address!");
        labelIP.setVisible(true);
        JLabel labelPort = new JLabel("Enter the port number");
        labelPort.setVisible(true);

        /*Input areas*/
        final JTextField ipInput = new JTextField();
        ipInput.setText("localhost");
        final JTextField portInput = new JTextField();
        portInput.setText("0");
        ipInput.setPreferredSize(new Dimension(120, 20));
        portInput.setPreferredSize(new Dimension(120, 20));

        /*Button for finish*/
        JButton execute = new JButton("Execute");
        execute.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
        		NetworkSetup.this.ip=ipInput.getText();
        		try {
					NetworkSetup.this.port=Integer.parseInt(portInput.getText());
				} catch (NumberFormatException e1) {
					System.out.println("Enter a valid number in the port");
				}
            }
        });

        /*Add the items to the panel*/
        panel.add(execute);
        panel.add(labelIP);
        panel.add(ipInput);
        panel.add(labelPort);
        panel.add(portInput);
        panel.add(execute);

        frame.add(panel);
        frame.setVisible(true);
	}
}
