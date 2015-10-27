package plugin;

import protocol.HttpRequest;
import protocol.HttpResponse;


public interface IPlugin {

	public HttpResponse handleRequest(HttpRequest request, String serverRootDirectory);
	public String getContextRoot();
	public String getConfigPath();
	
}
