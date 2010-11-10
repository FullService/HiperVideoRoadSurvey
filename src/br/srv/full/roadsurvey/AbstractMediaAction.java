// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractMediaAction.java

package br.srv.full.roadsurvey;

import javax.swing.AbstractAction;
import javax.swing.Action;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractMediaAction extends AbstractAction
    implements Action
{

    public AbstractMediaAction()
    {
    }

    private static final long serialVersionUID = 0x4011f3b1499a00f4L;
    protected final Log log = LogFactory.getLog(getClass());
}
