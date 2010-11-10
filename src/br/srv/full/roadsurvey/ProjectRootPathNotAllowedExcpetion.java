// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProjectRootPathNotAllowedExcpetion.java

package br.srv.full.roadsurvey;

import java.net.URI;

public class ProjectRootPathNotAllowedExcpetion extends Exception
{

    public ProjectRootPathNotAllowedExcpetion(URI p_uri)
    {
        super(p_uri.toString());
        uri = p_uri;
    }

    private URI uri;
}
