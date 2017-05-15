package cn.ljj.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	protected static final int LOG_LEVEL_VERBOSE = 0;
	protected static final int LOG_LEVEL_DEBUG = 1;
	protected static final int LOG_LEVEL_INFO = 2;
	protected static final int LOG_LEVEL_WARNING = 3;
	protected static final int LOG_LEVEL_ERROR = 4;
	
	private static int currentLogLevel = LOG_LEVEL_VERBOSE;
	private static SimpleDateFormat dateFormater;
	
	static {
		dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS ");
	}

    public static int v(String tag, String msg) {
        return log(LOG_LEVEL_VERBOSE, tag, msg, null);
    }

    public static int v(String tag, String msg, Throwable tr) {
        return log(LOG_LEVEL_VERBOSE, tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        return log(LOG_LEVEL_DEBUG, tag, msg, null);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return log(LOG_LEVEL_DEBUG, tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        return log(LOG_LEVEL_INFO, tag, msg, null);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return log(LOG_LEVEL_INFO, tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        return log(LOG_LEVEL_WARNING, tag, msg, null);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return log(LOG_LEVEL_WARNING, tag, msg, tr);
    }

    public static int e(String tag, String msg) {
        return log(LOG_LEVEL_ERROR, tag, msg, null);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return log(LOG_LEVEL_ERROR, tag, msg, tr);
    }
    
    private static int log(int logLevel, String tag, String msg, Throwable tr){
    	if(logLevel < currentLogLevel){
    		return 0;
    	}
    	StringBuilder sb = new StringBuilder(dateFormater.format(new Date()));
    	switch(logLevel){
    	case LOG_LEVEL_VERBOSE:
        	sb.append("[V] ");
    		break;
    	case LOG_LEVEL_DEBUG:
        	sb.append("[D] ");
    		break;
    	case LOG_LEVEL_INFO:
        	sb.append("[I] ");
    		break;
    	case LOG_LEVEL_WARNING:
        	sb.append("[W] ");
    		break;
    	case LOG_LEVEL_ERROR:
        	sb.append("[E] ");
    		break;
    	}
    	sb.append(tag).append(": ");
    	if(msg != null){
    		sb.append(msg);
    	}
    	System.out.println(sb.toString());
    	if(tr != null){
    		tr.printStackTrace();
    	}
    	return 1;
    }
}
