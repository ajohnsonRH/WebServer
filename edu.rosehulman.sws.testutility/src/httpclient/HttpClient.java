/*
 * HttpClient.java
 * Oct 15, 2012
 *
 * HTTP Test Utility for CSSE 477
 * 
 * Copyright (C) 2012 Chandan Raj Rupakheti
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either 
 * version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/lgpl.html>.
 * 
 */
package httpclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * Utility that tests most common HTTP requests and replies.
 *
 * @author Chandan R. Rupakheti (rupakhet@rose-hulman.edu)
 */
public class HttpClient extends javax.swing.JFrame {
	private static final String SLEEP_TIME_MS = "Sleep Time (ms)";

	private static final String REQUESTS = "Requests";

	private static final String CONNECT = "Connect";

	private static final String DISCONNECT = "Disconnect";

	private static final String PORT_NUMBER = "Port Number";

	private static final String EVEREST_CSSE_ROSE_HULMAN_EDU = "localhost";

	private static final String SERVER_NAME = "Server Name";

	private static final String CONNECTION_SETTINGS = "Connection Settings";

	private static final long serialVersionUID = -659854291303366035L;

	private boolean dosStop = false;
	private Socket socket;

	private JButton butBadRequest;
	private JButton butBadVersionRequest;
	private JButton butCacheRequest;
	private JButton butClear;
	private JButton butConnect;
	private JButton butDisconnet;
	private JButton butGeneratePersistent;
	private JButton butSend;
	private JButton butStartDOS;
	private JButton butStopDOS;
	private JLabel jLabel1;
	private JLabel lblPort;
	private JLabel lblRequests;
	private JLabel lblServer;
	private JScrollPane paneRequest;
	private JScrollPane paneResponse;
	private JPanel panelCommand;
	private JPanel panelConnection;
	private JPanel panelRequest;
	private JPanel panelResponse;
	private JTextField txtPort;
	private JTextArea txtRequest;
	private JTextField txtRequests;
	private JTextArea txtResponse;
	private JTextField txtServer;
	private JTextField txtSleepTime;

	/** Creates new form HttpClient */
	public HttpClient() {
		super("HTTP 1.1 Test Client");
		initComponents();
	}

	private void initComponents() {

		panelConnection = new JPanel();
		lblServer = new JLabel();
		txtServer = new JTextField();
		lblPort = new JLabel();
		txtPort = new JTextField();
		jLabel1 = new JLabel();
		txtSleepTime = new JTextField();
		lblRequests = new JLabel();
		txtRequests = new JTextField();
		butConnect = new JButton();
		butDisconnet = new JButton();
		panelRequest = new JPanel();
		paneRequest = new JScrollPane();
		txtRequest = new JTextArea();
		panelResponse = new JPanel();
		paneResponse = new JScrollPane();
		txtResponse = new JTextArea();
		panelCommand = new JPanel();
		butGeneratePersistent = new JButton();
		butStartDOS = new JButton();
		butStopDOS = new JButton();
		butBadRequest = new JButton();
		butSend = new JButton();
		butClear = new JButton();
		butCacheRequest = new JButton();
		butBadVersionRequest = new JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		panelConnection.setBorder(javax.swing.BorderFactory
				.createTitledBorder(CONNECTION_SETTINGS));

		lblServer.setText(SERVER_NAME);

		txtServer.setText(EVEREST_CSSE_ROSE_HULMAN_EDU);

		lblPort.setText(PORT_NUMBER);

		txtPort.setText("8080");

		jLabel1.setText(SLEEP_TIME_MS);

		txtSleepTime.setText("100");

		lblRequests.setText(REQUESTS);

		txtRequests.setText("10");

		butConnect.setText(CONNECT);
		butConnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butConnectActionPerformed(evt);
			}
		});

		butDisconnet.setText(DISCONNECT);
		butDisconnet.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butDisconnetActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout panelConnectionLayout = new org.jdesktop.layout.GroupLayout(
				panelConnection);
		panelConnection.setLayout(panelConnectionLayout);
		panelConnectionLayout
				.setHorizontalGroup(panelConnectionLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(panelConnectionLayout
								.createSequentialGroup()
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jLabel1).add(lblServer))
								.add(18, 18, 18)
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING,
												false)
										.add(txtServer,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												253,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(txtSleepTime))
								.add(34, 34, 34)
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(lblPort).add(lblRequests))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING,
												false)
										.add(txtRequests)
										.add(txtPort,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												78, Short.MAX_VALUE))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING,
												false)
										.add(butConnect,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(butDisconnet,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addContainerGap(
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		panelConnectionLayout
				.setVerticalGroup(panelConnectionLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(panelConnectionLayout
								.createSequentialGroup()
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(butConnect, 0, 0, Short.MAX_VALUE)
										.add(org.jdesktop.layout.GroupLayout.TRAILING,
												panelConnectionLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.BASELINE)
														.add(lblServer)
														.add(txtServer)
														.add(lblPort)
														.add(txtPort)))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(panelConnectionLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(panelConnectionLayout
												.createParallelGroup(
														org.jdesktop.layout.GroupLayout.BASELINE)
												.add(jLabel1)
												.add(txtSleepTime,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(panelConnectionLayout
												.createParallelGroup(
														org.jdesktop.layout.GroupLayout.BASELINE)
												.add(lblRequests)
												.add(txtRequests,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.add(butDisconnet)))));

		panelRequest.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Connection Request"));

		txtRequest.setColumns(20);
		txtRequest.setRows(5);
		paneRequest.setViewportView(txtRequest);

		org.jdesktop.layout.GroupLayout panelRequestLayout = new org.jdesktop.layout.GroupLayout(
				panelRequest);
		panelRequest.setLayout(panelRequestLayout);
		panelRequestLayout.setHorizontalGroup(panelRequestLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(paneRequest, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						712, Short.MAX_VALUE));
		panelRequestLayout.setVerticalGroup(panelRequestLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(paneRequest, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
						151, Short.MAX_VALUE));

		panelResponse.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Connection Response"));

		txtResponse.setColumns(20);
		txtResponse.setRows(5);
		paneResponse.setViewportView(txtResponse);
		txtResponse.append("HTTP 1.1 Test Client by C.R. Rupakheti\n");
		txtResponse
				.append("--------------------------------------------------------\n");

		org.jdesktop.layout.GroupLayout panelResponseLayout = new org.jdesktop.layout.GroupLayout(
				panelResponse);
		panelResponse.setLayout(panelResponseLayout);
		panelResponseLayout.setHorizontalGroup(panelResponseLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(paneResponse,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 712,
						Short.MAX_VALUE));
		panelResponseLayout.setVerticalGroup(panelResponseLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(paneResponse,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 227,
						Short.MAX_VALUE));

		panelCommand.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Connection Command"));

		butGeneratePersistent.setText("Generate Persistent Request");
		butGeneratePersistent.setActionCommand("Generate Persistent Request");
		butGeneratePersistent
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						butGeneratePersistentActionPerformed(evt);
					}
				});

		butStartDOS.setText("Start DOS Attack");
		butStartDOS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butStartDOSActionPerformed(evt);
			}
		});

		butStopDOS.setText("Stop DOS Attack");
		butStopDOS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butStopDOSActionPerformed(evt);
			}
		});

		butBadRequest.setText("Generate Bad Request");
		butBadRequest.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butBadRequestActionPerformed(evt);
			}
		});

		butSend.setText("Send");
		butSend.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butSendActionPerformed(evt);
			}
		});

		butClear.setText("Clear");
		butClear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butClearActionPerformed(evt);
			}
		});

		butCacheRequest.setText("Generate Cache Request");
		butCacheRequest.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				butCacheRequestActionPerformed(evt);
			}
		});

		butBadVersionRequest.setText("Generate Version Request");
		butBadVersionRequest
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						butBadVersionRequestActionPerformed(evt);
					}
				});

		org.jdesktop.layout.GroupLayout panelCommandLayout = new org.jdesktop.layout.GroupLayout(
				panelCommand);
		panelCommand.setLayout(panelCommandLayout);
		panelCommandLayout
				.setHorizontalGroup(panelCommandLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(panelCommandLayout
								.createSequentialGroup()
								.addContainerGap()
								.add(panelCommandLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.TRAILING,
												false)
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												butBadRequest,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												butGeneratePersistent))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(panelCommandLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING,
												false)
										.add(butCacheRequest,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(butBadVersionRequest))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(panelCommandLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(butStartDOS).add(butStopDOS))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(panelCommandLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(butSend).add(butClear))
								.addContainerGap(29, Short.MAX_VALUE)));
		panelCommandLayout
				.setVerticalGroup(panelCommandLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(panelCommandLayout
								.createSequentialGroup()
								.add(panelCommandLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(butGeneratePersistent)
										.add(butCacheRequest).add(butStartDOS)
										.add(butSend))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.add(panelCommandLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(butBadRequest)
										.add(butBadVersionRequest)
										.add(butStopDOS).add(butClear))
								.addContainerGap()));

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(org.jdesktop.layout.GroupLayout.TRAILING,
						layout.createSequentialGroup()
								.add(layout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.TRAILING)
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												panelRequest,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												panelCommand,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												panelConnection,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(org.jdesktop.layout.GroupLayout.LEADING,
												panelResponse,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.add(panelConnection,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(panelRequest,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(panelCommand,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								93,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(panelResponse,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}

	private void butGeneratePersistentActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_butGeneratePersistentActionPerformed
		StringBuffer buffer = new StringBuffer();
		buffer.append("GET /index.html HTTP/1.1\r\n");

		String host = this.txtServer.getText(); // + ":" +
												// this.txtPort.getText();

		buffer.append("Host: " + host + "\r\n");
		buffer.append("Connection: Keep-Alive\r\n");
		buffer.append("User-Agent: HttpTestClient/1.0\r\n");
		buffer.append("Accept: text/html,text/plain,application/xml,application/json\r\n");
		buffer.append("Accept-Language: en-US,en;q=0.8\r\n");
		buffer.append("\r\n");

		this.txtRequest.setText(buffer.toString());
	}

	private void butBadRequestActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_butBadRequestActionPerformed
		StringBuffer buffer = new StringBuffer();
		buffer.append("GET /index.html HTTP/1.1\r\n");

		String host = this.txtServer.getText(); // + ":" +
												// this.txtPort.getText();

		buffer.append("Tost: " + host + "\r\n");
		buffer.append("Connection: Keep-Alive\r\n");
		buffer.append("User-Agent: HttpTestClient/1.0\r\n");
		buffer.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
		buffer.append("Accept-Language: en-US,en;q=0.8\r\n");
		buffer.append("\r\n");

		this.txtRequest.setText(buffer.toString());
	}

	private void butCacheRequestActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_butCacheRequestActionPerformed
		StringBuffer buffer = new StringBuffer();
		buffer.append("GET /index.html HTTP/1.1\r\n");

		String host = this.txtServer.getText(); // + ":" +
												// this.txtPort.getText();

		buffer.append("Host: " + host + "\r\n");
		buffer.append("Connection: Keep-Alive\r\n");
		buffer.append("User-Agent: HttpTestClient/1.0\r\n");
		buffer.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
		buffer.append("Accept-Language: en-US,en;q=0.8\r\n");
		buffer.append("If-Modified-Since: Sun, 25 Sep 2011 23:30:55 GMT\r\n");
		buffer.append("\r\n");

		this.txtRequest.setText(buffer.toString());
	}

	private void butBadVersionRequestActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_butBadVersionRequestActionPerformed
		StringBuffer buffer = new StringBuffer();
		buffer.append("GET /index.html HTTP/1.0\r\n");

		String host = this.txtServer.getText(); // + ":" +
												// this.txtPort.getText();

		buffer.append("Host: " + host + "\r\n");
		buffer.append("Connection: Close\r\n");
		buffer.append("User-Agent: HttpTestClient/1.0\r\n");
		buffer.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
		buffer.append("Accept-Language: en-US,en;q=0.8\r\n");
		buffer.append("\r\n");

		this.txtRequest.setText(buffer.toString());
	}

	private void butConnectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_butConnectActionPerformed
		try {
			if (this.socket != null)
				this.socket.close();
		} catch (Exception e) {
			this.txtResponse.append(e.toString() + "\n");
		}

		try {
			String host = this.txtServer.getText();
			int port = Integer.parseInt(this.txtPort.getText());
			this.socket = new Socket(host, port);
			this.txtResponse.append("Connection Established!\n");

			// Start receiving response
			new Thread(new Handler()).start();
		} catch (Exception e) {
			this.txtResponse.append(e.toString() + "\n");
			JOptionPane.showMessageDialog(this, e.getMessage()
					+ "Connection Closed by the Server!", "Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void butSendActionPerformed(java.awt.event.ActionEvent evt) {
		new Thread(new Sender()).start();
	}

	private void butDisconnetActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			this.socket.close();
			this.socket = null;
		} catch (Exception e) {
			this.txtResponse.append(e.toString() + "\n");
			JOptionPane.showMessageDialog(this, e.getMessage()
					+ "Connection Closed by the Server!", "Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void butStartDOSActionPerformed(java.awt.event.ActionEvent evt) {
		// JOptionPane.showMessageDialog(this, "You wish! -5 points! :-)",
		// "Feature Disabled", JOptionPane.ERROR_MESSAGE);
		new Thread(new DOSAttack()).start();
	}

	private void butStopDOSActionPerformed(java.awt.event.ActionEvent evt) {
		this.dosStop = true;
	}

	private void butClearActionPerformed(java.awt.event.ActionEvent evt) {
		this.txtRequest.setText("");
		this.txtResponse.setText("");
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new HttpClient().setVisible(true);
			}
		});
	}

	private void invokeLaterNewRunnable(final Exception e) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				txtResponse.append(e.toString() + "\n");
				JOptionPane.showMessageDialog(HttpClient.this, e.getMessage()
						+ " Connetion Closed", "Connection Problem",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private class Sender implements Runnable {
		public void run() {
			try {
				if (!socket.isConnected())
					throw new Exception("Socket is not connected!");

				String request = txtRequest.getText();
				OutputStream out = socket.getOutputStream();
				out.write(request.getBytes());
				out.flush();

				txtResponse.append("Request Sent. Waiting for response ...\n");
			} catch (final Exception e) {
				invokeLaterNewRunnable(e);
			}
		}

	}

	private class Handler implements Runnable {
		public void run() {
			try {
				InputStream in = socket.getInputStream();

				// Read the response
				HttpResponse response = HttpResponse.read(in);
				txtResponse.append(response.toString());
			} catch (final Exception e) {
				invokeLaterNewRunnable(e);
				return;
			}

			txtResponse.append("Socket Disconnected!\n");
			JOptionPane.showMessageDialog(HttpClient.this,
					"Socket Disconnected!", "Connection Problem",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private class DOSAttack implements Runnable {
		public void run() {
			dosStop = false;
			try {
				int requests = Integer.parseInt(txtRequests.getText());
				OutputStream out = socket.getOutputStream();
				for (int i = 0; i < requests; ++i) {
					String request = txtRequest.getText();
					out.write(request.getBytes());
					out.flush();
					txtResponse.append("Request #" + (i + 1)
							+ " Sent. Waiting for response ...\n");

					int sleep = Integer.parseInt(txtSleepTime.getText());
					Thread.sleep(sleep);

					if (dosStop)
						break;
				}
			} catch (Exception e) {
				showMessageDialog(e, "Connection Problem", e.toString() + "\n");
			}
			txtResponse.append("DOS Attack Stopped!\n");
		}

	}

	private void showMessageDialog(Exception e, String message,
			String responseAddition) {
		txtResponse.append(responseAddition);
		JOptionPane.showMessageDialog(HttpClient.this, e.getMessage(), message,
				JOptionPane.ERROR_MESSAGE);
	}
}
