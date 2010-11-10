// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Main.java

package br.srv.full.roadsurvey;

import br.srv.full.url.protocol.HVRSURLStreamHandlerFactory;
import java.net.URL;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// Referenced classes of package br.srv.full.roadsurvey:
//            RoadSurveyWindow, RoadSurveyManager

public class Main {

	public Main() {
	}

	public static void main(String args[]) {
		URL.setURLStreamHandlerFactory(HVRSURLStreamHandlerFactory
				.getInstance());
		RoadSurveyWindow rsw = RoadSurveyWindow.getInstance();
		RoadSurveyManager rsm = RoadSurveyManager.getInstance();
		rsm.setView(rsw);
		rsm.start();
	}

	private static final Log slog = LogFactory.getLog(Main.class);

}
