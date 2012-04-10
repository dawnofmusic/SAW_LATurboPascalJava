package de.wsdevel.languages.turbopascal.graph;

/**
 * Created on 10.04.2012 for project: SAW_LATurboPascalJava
 *
 * (c) 2012 Sebastian A. Weiﬂ - All rights reserved.
 *
 * @author <a href="mailto:post@sebastian-weiss.de">Sebastian A. Weiss</a>
 * @version $Author: $ -- $Revision: $ -- $Date: $
 */
public class ViewPortType {

    public int x1 = 0;
    public int y1 = 0;
    public int x2 = 0;
    public int y2 = 0;

    public ViewPortType() {
    }

    public ViewPortType(final int x1Val, final int y1Val, final int x2Val,
	    final int y2Val) {
	this.x1 = x1Val;
	this.y1 = y1Val;
	this.x2 = x2Val;
	this.y2 = y2Val;
    }

    public int height() {
	return this.y2 - this.y1;
    }

    public int width() {
	return this.x2 - this.x1;
    }

}
//
// $Log: $
//
