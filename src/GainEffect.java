/*
 * @(#)GainEffect.java	1.4 01/03/13
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


import javax.media.Buffer;
import javax.media.Control;
import javax.media.Effect;
import javax.media.Format;
import javax.media.ResourceUnavailableException;
import javax.media.format.AudioFormat;

@SuppressWarnings("restriction")
public class GainEffect implements Effect {

    /** The effect name **/
    private static String EffectName="GainEffect";

    /** chosen input Format **/
    protected AudioFormat inputFormat;

    /** chosen output Format **/
    protected AudioFormat outputFormat;

    /** supported input Formats **/
    protected Format[] supportedInputFormats=new Format[0];

    /** supported output Formats **/
    protected Format[] supportedOutputFormats=new Format[0];

    /** selected Gain **/
    protected float gain = 1.2F;


    /** 
     * initialize the formats 
     */
    public GainEffect() {
        supportedInputFormats = new Format[] {
	    new AudioFormat(
	        AudioFormat.LINEAR,
                Format.NOT_SPECIFIED,
                16,
                Format.NOT_SPECIFIED,
                AudioFormat.BIG_ENDIAN,
                AudioFormat.SIGNED,
                16,
                Format.NOT_SPECIFIED,
                Format.byteArray
	    )
	};

        supportedOutputFormats = new Format[] {
	    new AudioFormat(
	        AudioFormat.LINEAR,
                Format.NOT_SPECIFIED,
                16,
                Format.NOT_SPECIFIED,
                AudioFormat.BIG_ENDIAN,
                AudioFormat.SIGNED,
                16,
                Format.NOT_SPECIFIED,
                Format.byteArray
	    )
	};

	System.err.println("Set gain by: " + gain);
    }


    /** 
     * get the resources needed by this effect 
     */
    public void open() throws ResourceUnavailableException {
    }


    /** 
     * free the resources allocated by this codec 
     */
    public void close() {
    }


    /** 
     * reset the codec 
     */
    public void reset() {
    }


    /**
     * no controls for this simple effect 
     */
    public Object[] getControls() {
        return (Object[]) new Control[0];
    }


    /**
     * Return the control based on a control type for the effect.
     */
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

    /************** format methods *************/

    /** 
     * set the input format 
     */
    public Format setInputFormat(Format input) {
        // the following code assumes valid Format
        inputFormat = (AudioFormat)input;
        return (Format)inputFormat;
    }


    /** 
     * set the output format 
     */
    public Format setOutputFormat(Format output) {
        // the following code assumes valid Format
        outputFormat = (AudioFormat)output;
        return (Format)outputFormat;
    }


    /** 
     * get the input format 
     */
    protected Format getInputFormat() {
        return inputFormat;
    }


    /** 
     * get the output format 
     */
    protected Format getOutputFormat() {
        return outputFormat;
    }


    /** 
     * supported input formats 
     */
    public Format [] getSupportedInputFormats() {
        return supportedInputFormats;
    }


    /** 
     * output Formats for the selected input format 
     */
    public Format [] getSupportedOutputFormats(Format in) {
        if (! (in instanceof AudioFormat) )
            return new Format[0];

        AudioFormat iaf=(AudioFormat) in;

        if (!iaf.matches(supportedInputFormats[0]))
            return new Format[0];

	AudioFormat oaf= new AudioFormat(
	        AudioFormat.LINEAR,
                iaf.getSampleRate(),
                16,
                iaf.getChannels(),
                AudioFormat.BIG_ENDIAN,
                AudioFormat.SIGNED,
                16,
                Format.NOT_SPECIFIED,
                Format.byteArray
        );

        return new Format[] {oaf};
    }


    /** 
     * gain accessor method 
     */
    public void setGain(float newGain){
        gain=newGain;
    }


    /** 
     * return effect name 
     */
    public String getName() {
        return EffectName;
    }


    /** 
     * do the processing 
     */
    public int process(Buffer inputBuffer, Buffer outputBuffer){

        // == prolog
        byte[] inData = (byte[])inputBuffer.getData();
        int inLength = inputBuffer.getLength();
        int inOffset = inputBuffer.getOffset();

        byte[] outData = validateByteArraySize(outputBuffer, inLength);
        int outOffset = outputBuffer.getOffset();
	int j = outOffset;
        int outLength = inLength;

	int samplesNumber = inLength / 2 ;

        // == main

	int tempH, tempL;
	short sample;

        for (int i=0; i< samplesNumber;i++) {

            tempH = inData[inOffset ++] & 0xff;
            tempL = inData[inOffset ++] & 0xff;

	    sample = (short)((tempH << 8) | tempL);
	    sample = (short)(sample * gain);

            outData[j ++]=(byte)(sample >> 8); 
            outData[j ++]=(byte)(sample & 0xff);
        }

        // == epilog
        updateOutput(outputBuffer,outputFormat, outLength, outOffset);
        return BUFFER_PROCESSED_OK;
    }


    /**
     * Utility: validate that the Buffer object's data size is at least
     * newSize bytes.
     * @return array with sufficient capacity
     **/
    protected byte[] validateByteArraySize(Buffer buffer,int newSize) {
        Object objectArray=buffer.getData();
        byte[] typedArray;
        if (objectArray instanceof byte[]) { // is correct type AND not null
            typedArray=(byte[])objectArray;
            if (typedArray.length >= newSize ) { // is sufficient capacity
                return typedArray;
            }
        }
        typedArray = new byte[newSize];
        buffer.setData(typedArray);
        return typedArray;
    }


    /** 
     * utility: update the output buffer fields 
     */
    protected void updateOutput(Buffer outputBuffer,
                                Format format,int length, int offset) {

        outputBuffer.setFormat(format);
        outputBuffer.setLength(length);
        outputBuffer.setOffset(offset);
    }
}
