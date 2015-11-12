package protocol;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.Map;

public class DynamicResponse extends HttpResponse {
	private String body;
	private String tempo;
	public DynamicResponse(String version, int status, String phrase,
			Map<String, String> header, File file) {
		super(version, status, phrase, header, file);
		
		this.body = "";
	}
	public void setBody(String bodyText){
		this.body = bodyText;
	}
	@Override
	public void write(OutputStream outStream) throws Exception {
		BufferedOutputStream out = new BufferedOutputStream(outStream,
				Protocol.CHUNK_LENGTH);

		// First status line
		String line = this.getVersion() + Protocol.SPACE + this.getStatus()
				+ Protocol.SPACE + this.getPhrase() + Protocol.CRLF;
		out.write(line.getBytes());

		// Write header fields if there is something to write in header field
		if (getHeader() != null && !getHeader().isEmpty()) {
			for (Map.Entry<String, String> entry : getHeader().entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();

				// Write each header field line
				line = key + Protocol.SEPERATOR + Protocol.SPACE + value
						+ Protocol.CRLF;
				out.write(line.getBytes());
			}
		}

		// Write a blank line
		out.write(Protocol.CRLF.getBytes());
		out.write(this.body.getBytes());
		out.flush();
	}

}
