/*
*    Copyright 2014 Matthew MacGregor
*
*    This file is part of NotAsteroids.
*
*    NotAsteroids is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    Foobar is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with NotAsteroids.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.sudolink.entities;

import com.sudolink.enumeration.Team;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * A GameObject that renders text to the screen.
 * 
 * @author Matthew MacGregor
 */
public class TextDisplay extends GameObject {

    public TextDisplay(Font f, String text) {
        super();
        message = "";
        font = f;
        foreground = Color.WHITE;
        background = Color.BLACK;

        initBuffer();
        setTeam(Team.Neutral);
        setText(text);
        resize();
    }
    
    public TextDisplay( Font f ) {
        this( f, "" );
    }

    @Override
    public void collide(GameObject o) {
        //Does nothing
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (isEnabled) {
            if (shouldRedraw) {
                setBuffer(null);
            }

            super.draw(g2d);
//            g2d.drawImage(backbuffer, 0, 0, null);
        }

    }

    public final void setText(String message) {
        this.message = message;
        shouldRedraw = true;
        resize();
    }

    /**
     * @param foreground the foreground to set
     */
    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    /**
     * @return the isEnabled
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * @param isEnabled the isEnabled to set
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * @param isHorizontallyCentered the isHorizontallyCentered to set
     */
    public void setHorizontallyCentered(boolean isHorizontallyCentered) {
        this.isHorizontallyCentered = isHorizontallyCentered;
    }
    
    public boolean isHorizontallyCentered() {
        return isHorizontallyCentered;
    }

    @Override
    public float getX() {
        if( isHorizontallyCentered ) {
       
            return super.getX() - (getWidth() / 2);
           
        }
        return super.getX();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Private & Protected Methods">
    protected Dimension sizeText(String str) { 
        
        Graphics2D _g2d = getGraphics();
        
        // get metrics from the graphics
        FontMetrics metrics = _g2d.getFontMetrics(font);

        // calculate the size of a box to hold the
        // text with some padding.
        // get the height of a line of text in this
        // font and render context
        int hgt = metrics.getHeight();

        // get the advance of my text in this font
        // and render context (with a little padding).
        int adv = metrics.stringWidth(str);

        //Add a little bit of padding
        hgt += 2;
        adv += 2;
        
        return new Dimension(adv, hgt);
    }
        
//    private void refreshBuffer(int width, int height) {
//        setBuffer(null);
//    }

    private void resize() {
 
        initBuffer();
               
        Dimension d = sizeText(message);
        
        //We only want to refresh the buffered image if it's bigger than 
        //the original (IS THIS CORRECT?) 
        if (d.width > getWidth() || d.height > getHeight()) {
      
            setWidth(d.width);
            setHeight(d.height);
            refreshBuffer();

        }
    }
    
    private void clearBackground() {
        Graphics2D _g2d = getGraphics();
        _g2d.setBackground(background);
        _g2d.clearRect(0, 0, (int) getWidth(), (int) getHeight());
    }

    /**
     * Draws to a buffer only when the
     * @param _g2d text has changed to enhance performance.
     */
    @Override
    protected void drawToBuffer( Graphics2D _g2d ) {

        if (_g2d == null) {
            throw new NullPointerException("Graphics2D object cannot be null!");
        }

        clearBackground();
        _g2d.setFont(font);
        _g2d.setColor(foreground);
        _g2d.drawString(message, 0, getHeight());
        shouldRedraw = false;
    }
    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Private Members">
    private String message;
    private boolean shouldRedraw = true; //First pass should always redraw
    private final Font font;
    private Color foreground;
    private final Color background;
    private boolean isEnabled = true;
    private boolean isHorizontallyCentered = false;
    // </editor-fold>
 

}
