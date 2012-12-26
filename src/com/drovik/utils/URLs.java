package com.drovik.utils;

import java.io.File;

public class URLs {
	public final static String HOST = "www.drovik.com";//192.168.1.213
	private final static String HTTP = "http://";
	private final static String HTTPS = "https://";
	
	private final static String URL_SPLITTER = File.separator;
	
	public final static String getUpdateVersion(boolean security, String subFold) {
		String URL_API_HOST = security?HTTPS:HTTP + HOST + URL_SPLITTER;
		return URL_API_HOST+"apk_down_load" + URL_SPLITTER + subFold + URL_SPLITTER + "AppVersion.xml";
	}
	
	public final static String makeUrl(boolean security, String subFold, String des) {
		String URL_API_HOST = security?HTTPS:HTTP + HOST + URL_SPLITTER;
		return URL_API_HOST+"apk_down_load" + URL_SPLITTER + subFold + URL_SPLITTER + des;
	}
}
