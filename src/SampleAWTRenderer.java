/*
 * @(#)SampleAWTRenderer.java	1.3 01/03/13
 *
 * Copyright (c) 1999-2001 Sun Microsystems, Inc. All Rights Reserved.
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

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;

import javax.media.Buffer;
import javax.media.Control;
import javax.media.Format;
import javax.media.ResourceUnavailableException;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;

/**
 * Renderer for RGB images using AWT Image.
 */
@SuppressWarnings("restriction")
public class SampleAWTRenderer implements javax.media.renderer.VideoRenderer {

	/*************************************************************************
	 * Variables and Constants
	 *************************************************************************/

	// The descriptive name of this renderer
	private static final String name = "Sample AWT Renderer";

	protected RGBFormat inputFormat;
	protected RGBFormat supportedRGB;
	protected Format[] supportedFormats;

	protected MemoryImageSource sourceImage;
	protected Image destImage;
	protected Buffer lastBuffer = null;

	protected int inWidth = 0;
	protected int inHeight = 0;
	protected Component component = null;
	protected Rectangle reqBounds = null;
	protected Rectangle bounds = new Rectangle();
	protected boolean started = false;

	/*************************************************************************
	 * Constructor
	 *************************************************************************/

	public SampleAWTRenderer() {
		// Prepare supported input formats and preferred format
		int rMask = 0x000000FF;
		int gMask = 0x0000FF00;
		int bMask = 0x00FF0000;

		supportedRGB = new RGBFormat(null, // size
				Format.NOT_SPECIFIED, // maxDataLength
				int[].class, // buffer type
				Format.NOT_SPECIFIED, // frame rate
				32, // bitsPerPixel
				rMask, gMask, bMask, // component masks
				1, // pixel stride
				Format.NOT_SPECIFIED, // line stride
				Format.FALSE, // flipped
				Format.NOT_SPECIFIED // endian
		);
		supportedFormats = new VideoFormat[1];
		supportedFormats[0] = supportedRGB;
	}

	/****************************************************************
	 * Controls implementation
	 ****************************************************************/

	/**
	 * Returns an array of supported controls
	 **/
	public Object[] getControls() {
		// No controls
		return (Object[]) new Control[0];
	}

	/**
	 * Return the control based on a control type for the PlugIn.
	 */
	@SuppressWarnings("unchecked")
	public Object getControl(String controlType) {
		try {
			Class cls = Class.forName(controlType);
			Object cs[] = getControls();
			for (int i = 0; i < cs.length; i++) {
				if (cls.isInstance(cs[i]))
					return cs[i];
			}
			return null;
		} catch (Exception e) { // no such controlType or such control
			return null;
		}
	}

	/*************************************************************************
	 * PlugIn implementation
	 *************************************************************************/

	public String getName() {
		return name;
	}

	/**
	 * Opens the plugin
	 */
	public void open() throws ResourceUnavailableException {
		sourceImage = null;
		destImage = null;
		lastBuffer = null;
	}

	/**
	 * Resets the state of the plug-in. Typically at end of media or when media
	 * is repositioned.
	 */
	public void reset() {
		// Nothing to do
	}

	public void close() {
		// Nothing to do
	}

	/*************************************************************************
	 * Renderer implementation
	 *************************************************************************/

	public void start() {
		started = true;
	}

	public void stop() {
		started = false;
	}

	/**
	 * Lists the possible input formats supported by this plug-in.
	 */
	public Format[] getSupportedInputFormats() {
		return supportedFormats;
	}

	/**
	 * Set the data input format.
	 */
	public Format setInputFormat(Format format) {
		if (format != null && format instanceof RGBFormat
				&& format.matches(supportedRGB)) {

			inputFormat = (RGBFormat) format;
			Dimension size = inputFormat.getSize();
			inWidth = size.width;
			inHeight = size.height;
			return format;
		} else
			return null;
	}

	/**
	 * Processes the data and renders it to a component
	 */
	public synchronized int process(Buffer buffer) {
		if (component == null)
			return BUFFER_PROCESSED_FAILED;

		Format inf = buffer.getFormat();
		if (inf == null)
			return BUFFER_PROCESSED_FAILED;

		if (inf != inputFormat || !buffer.getFormat().equals(inputFormat)) {
			if (setInputFormat(inf) != null)
				return BUFFER_PROCESSED_FAILED;
		}

		Object data = buffer.getData();
		if (!(data instanceof int[]))
			return BUFFER_PROCESSED_FAILED;

		if (lastBuffer != buffer) {
			lastBuffer = buffer;
			newImage(buffer);
		}

		sourceImage.newPixels(0, 0, inWidth, inHeight);

		Graphics g = component.getGraphics();
		if (g != null) {
			if (reqBounds == null) {
				bounds = component.getBounds();
				bounds.x = 0;
				bounds.y = 0;
			} else
				bounds = reqBounds;
			g.drawImage(destImage, bounds.x, bounds.y, bounds.width,
					bounds.height, 0, 0, inWidth, inHeight, component);
		}

		return BUFFER_PROCESSED_OK;
	}

	/****************************************************************
	 * VideoRenderer implementation
	 ****************************************************************/

	/**
	 * Returns an AWT component that it will render to. Returns null if it is
	 * not rendering to an AWT component.
	 */
	public java.awt.Component getComponent() {
		if (component == null) {
			component = new Canvas() {
				public Dimension getPreferredSize() {
					return new Dimension(getInWidth(), getInHeight());
				}

				public void update(Graphics g) {
				}

				public void paint(Graphics g) {
					// TODO: Need to repaint image if the movie is in paused
					// state
				}

			};
		}

		return component;
	}

	/**
	 * Requests the renderer to draw into a specified AWT component. Returns
	 * false if the renderer cannot draw into the specified component.
	 */
	public boolean setComponent(java.awt.Component comp) {
		component = comp;
		return true;
	}

	/**
	 * Sets the region in the component where the video is to be rendered to.
	 * Video is to be scaled if necessary. If <code>rect</code> is null, then
	 * the video occupies the entire component.
	 */
	public void setBounds(java.awt.Rectangle rect) {
		reqBounds = rect;
	}

	/**
	 * Returns the region in the component where the video will be rendered to.
	 * Returns null if the entire component is being used.
	 */
	public java.awt.Rectangle getBounds() {
		return reqBounds;
	}

	/*************************************************************************
	 * Local methods
	 *************************************************************************/

	int getInWidth() {
		return inWidth;
	}

	int getInHeight() {
		return inHeight;
	}

	private void newImage(Buffer buffer) {
		Object data = buffer.getData();
		if (!(data instanceof int[]))
			return;
		RGBFormat format = (RGBFormat) buffer.getFormat();

		DirectColorModel dcm = new DirectColorModel(format.getBitsPerPixel(),
				format.getRedMask(), format.getGreenMask(), format
						.getBlueMask());

		sourceImage = new MemoryImageSource(format.getLineStride(), format
				.getSize().height, dcm, (int[]) data, 0, format.getLineStride());
		sourceImage.setAnimated(true);
		sourceImage.setFullBufferUpdates(true);
		if (component != null) {
			destImage = component.createImage(sourceImage);
			component.prepareImage(destImage, component);
		}
	}

}
