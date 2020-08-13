package br.com.uoutec.community.ediacaran.system.pub;

import java.io.File;

public class WebPluginUtil {

	public static String getPublicPath(File pluginPath) {
		File supplierPath = pluginPath.getParentFile();
		File path         = supplierPath.getParentFile();
		return "/plugins/" + path.getName() + "/" + supplierPath.getName() + "/" + pluginPath.getName();
	}
	
}
