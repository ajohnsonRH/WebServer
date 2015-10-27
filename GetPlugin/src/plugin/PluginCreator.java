package plugin;

import plugin.IPlugin;

public class PluginCreator {

	
	public IPlugin createPlugin(){
		IPlugin temp =  new MyPlugin();
		return temp;
	}
}
