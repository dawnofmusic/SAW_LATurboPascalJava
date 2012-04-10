package de.wsdevel.languages.turbopascal.graph;

import java.awt.Color;

/**
 * Created on 10.04.2012 for project: SAW_LATurboPascalJava
 *
 * (c) 2012 Sebastian A. Weiﬂ - All rights reserved.
 *
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 */
public class EGAColor extends Color {

    /**
     * {@link long} COMMENT.
     */
    private static final long serialVersionUID = 8474364492990621920L;

    public int egaRed;
    public int egaGreen;
    public int egaBlue;

    /**
     * COMMENT.
     * 
     * @param r
     * @param g
     * @param b
     */
    public EGAColor(int r, int g, int b) {
	super(r / 63f, g / 63f, b / 63f, 1.0f);
	// super(1.0f, 1f, 1f, b / 63f);
	this.egaRed = r;
	this.egaGreen = g;
	this.egaBlue = b;
    }

}
//
// $Log: $
//
