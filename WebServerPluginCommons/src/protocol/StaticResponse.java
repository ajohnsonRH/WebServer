package protocol;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

public class StaticResponse extends HttpResponse {

	public StaticResponse(String version, int status, String phrase,
			Map<String, String> header, File file) {
		super(version, status, phrase, header, file);
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

		// We are reading a file
		if (this.getStatus() == Protocol.OK_CODE && getFile() != null) {
			// Process text documents
			FileInputStream fileInStream = new FileInputStream(getFile());
			BufferedInputStream inStream = new BufferedInputStream(
					fileInStream, Protocol.CHUNK_LENGTH);

			byte[] buffer = new byte[Protocol.CHUNK_LENGTH];
			int bytesRead = 0;
			// While there is some bytes to read from file, read each chunk and
			// send to the socket out stream
			while ((bytesRead = inStream.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
			// Close the file input stream, we are done reading
			inStream.close();
		}

		// Flush the data so that outStream sends everything through the socket
		out.flush();
	}

}
