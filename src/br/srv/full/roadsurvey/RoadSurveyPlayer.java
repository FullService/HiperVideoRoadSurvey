package br.srv.full.roadsurvey;

/*
 * @(#)MediaPlayerSample.java	1.1 00/05/23
 *
 * Copyright (c) 1999 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

import java.applet.Applet;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.media.bean.playerbean.MediaPlayer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RoadSurveyPlayer extends Applet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6504117893853441834L;

	Log log = LogFactory.getLog(getClass());
	{
		log.info("Criado");
	}
	public void init() {

		// {{INIT_CONTROLS
		setLayout(null);
		setSize(1050, 700);
		mediaPlayer1.setMediaLocation("blueline:E:\\strata5\\060BGO0152-1SD11.arv");
		add(mediaPlayer1);
		mediaPlayer1.setBounds(36, 24, 1000, 600);
		// }}

		// {{REGISTER_LISTENERS
		SymComponent aSymComponent = new SymComponent();
		this.addComponentListener(aSymComponent);
		SymContainer aSymContainer = new SymContainer();
		this.addContainerListener(aSymContainer);
		// }}
	}

	public void stop() {
		if (mediaPlayer1 != null)
			mediaPlayer1.stop();

	}

	public void destroy() {
		if (mediaPlayer1 != null)
			mediaPlayer1.deallocate();

	}

	// {{DECLARE_CONTROLS
	MediaPlayer mediaPlayer1 = new MediaPlayer();

	// }}

	class SymComponent extends ComponentAdapter {
		public void componentShown(ComponentEvent event) {
			Object object = event.getSource();
			if (object == RoadSurveyPlayer.this)
				MediaPlayerSample_ComponentShown(event);
		}
	}

	void MediaPlayerSample_ComponentShown(ComponentEvent event) {
		// to do: code goes here.

		MediaPlayerSample_ComponentShown_Interaction1(event);
	}

	class SymContainer extends ContainerAdapter {
		public void componentRemoved(ContainerEvent event) {
			Object object = event.getSource();
			if (object == RoadSurveyPlayer.this)
				MediaPlayerSample_ComponentRemoved(event);
		}
	}

	void MediaPlayerSample_ComponentRemoved(ContainerEvent event) {
		// to do: code goes here.

		MediaPlayerSample_ComponentRemoved_Interaction1(event);
	}

	void MediaPlayerSample_ComponentShown_Interaction1(ComponentEvent event) {
		try {
			mediaPlayer1.start();
		} catch (Exception e) {
		}
	}

	void MediaPlayerSample_ComponentRemoved_Interaction1(ContainerEvent event) {
		try {
			mediaPlayer1.stop();
			mediaPlayer1.deallocate();
		} catch (Exception e) {
		}
	}
}
