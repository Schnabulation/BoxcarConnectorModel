import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BoxcarConnectorModel {

	private String message;
	private String sender;
	private String reciever;
	private String sendUrl;
	private String inviteUrl;
	private String broadUrl;
	private HttpURLConnection connection;
	private String body;
	private String apiSecret;

	public BoxcarConnectorModel(String apiKey, String apiSecret) {

		this.sendUrl = "http://boxcar.io/devices/providers/" + apiKey
				+ "/notifications";
		this.inviteUrl = "http://boxcar.io/devices/providers/" + apiKey
				+ "/notifications/subscribe";
		
		this.broadUrl = "http://boxcar.io/devices/providers/" + apiKey
				+ "/notifications/broadcast";
		
		this.apiSecret = apiSecret;
		this.message = "";
		this.sender = "";
		this.reciever = "";
	}

	public void sendInvitation(String reciever) throws Exception {
		this.reciever = reciever;

		this.openConnection("invite");
		this.writeToConnection();
		this.closeConnection();
	}

	public void sendMessage(String reciever, String message, String sender)
			throws Exception {
		this.reciever = reciever;
		this.message = message;
		this.sender = sender;

		this.openConnection("send");
		this.writeToConnection();
		this.closeConnection();
	}
	
	public void sendBroadcast(String message, String sender) throws Exception {
		this.message = message;
		this.sender = sender;
		
		this.openConnection("broad");
		this.writeToConnection();
		this.closeConnection();
	}

	private void openConnection(String type) throws Exception {
		if (type.equals("send")) {
			this.body = "email=" + reciever
					+ "&notification[from_screen_name]=" + sender
					+ "&notification[message]=" + message;
			connection = (HttpURLConnection) this.getSendUrl().openConnection();
		} else if (type.equals("invite")) {
			this.body = "email=" + reciever;
			connection = (HttpURLConnection) this.getInviteUrl()
					.openConnection();
		} else if (type.equals("broad")) {
			this.body = "secret=" + this.apiSecret + "&notification[from_screen_name]=" + sender
					+ "&notification[message]=" + message;
			connection = (HttpURLConnection) this.getBroadUrl().openConnection();
		} else {
			throw new Exception("Please say if you want to invite or to send");
		}
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

	}

	private void writeToConnection() throws Exception {

		OutputStreamWriter stream = new OutputStreamWriter(
				connection.getOutputStream());
		stream.write(this.body);

		stream.flush();
		stream.close();

		/**
		 * Nevertheless the response is not read, this line is needed to get the
		 * notification
		 */

		new BufferedReader(new InputStreamReader(connection.getInputStream()));
	}

	private void closeConnection() {
		connection.disconnect();
	}

	private URL getSendUrl() throws MalformedURLException {
		return new URL(this.sendUrl);
	}

	private URL getInviteUrl() throws MalformedURLException {
		return new URL(this.inviteUrl);
	}
	
	private URL getBroadUrl() throws MalformedURLException {
		return new URL(this.broadUrl);
	}
}
