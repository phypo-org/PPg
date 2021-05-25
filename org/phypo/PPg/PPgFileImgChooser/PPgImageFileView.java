/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 
package org.phypo.PPg.PPgFileImgChooser;

import java.io.File;
import javax.swing.*;
import java.beans.*;
import java.awt.*;
import javax.swing.filechooser.*;

//*************************************************
/* ImageFileView.java is used by FileChooserDemo2.java. */

public class PPgImageFileView extends FileView {

		static int cMaxWidth  = 50;
		static int cMaxHeight = 50;
		
		int cLastWidth =0;
		int cLastHeight=0;
		//------------------------------------------------
		boolean isGraphic( File f ){

				String extension = PPgImageUtils.getExtension(f);

				if (extension != null) {
            if (extension.equals( PPgImageUtils.jpeg) ||
                extension.equals( PPgImageUtils.jpg ) ||
								extension.equals( PPgImageUtils.gif)  ||
								extension.equals( PPgImageUtils.bmp)  ||
								extension.equals( PPgImageUtils.tiff) ||
								extension.equals( PPgImageUtils.tif)  || 																								
								extension.equals( PPgImageUtils.png)) {

								return true;
						}
				}

				return false;								
		}
		//------------------------------------------------

    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }
		//------------------------------------------------

    public String getDescription(File f) {

        if( isGraphic(f) ) {
						return new String( ""+cLastWidth +'x'+cLastHeight);
				}
				
        return null; //let the L&F FileView figure this out
    }
		//------------------------------------------------

    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }

		//------------------------------------------------

    public String getTypeDescription(File f) {
        String extension = PPgImageUtils.getExtension(f);
        String type = null;

        if (extension != null) {
            if (extension.equals( PPgImageUtils.jpeg) ||
                extension.equals(PPgImageUtils.jpg)) {
                type = "JPEG Image";
            } else if (extension.equals(PPgImageUtils.gif)){
                type = "GIF Image";
            } else if (extension.equals(PPgImageUtils.tiff) ||
                       extension.equals(PPgImageUtils.tif)) {
                type = "TIFF Image";
            } else if (extension.equals(PPgImageUtils.png)){
                type = "PNG Image";
            }
        }
        return type;

    }
		//------------------------------------------------

    public Icon getIcon(File f) {

        ImageIcon icon = null;

        if( isGraphic( f) ) {
						ImageIcon tmpIcon = new ImageIcon(f.getPath());
						if(  tmpIcon != null ) {
								cLastWidth  = tmpIcon.getIconWidth();
								cLastHeight = tmpIcon.getIconHeight();
								
								
								if(  cLastWidth > cMaxWidth  || cLastHeight > cMaxHeight ) {
										if( tmpIcon.getIconWidth() >  tmpIcon.getIconHeight() )
												icon = new ImageIcon( tmpIcon.getImage().getScaledInstance( cMaxWidth, -1,
																																										Image.SCALE_FAST));
										else
												icon = new ImageIcon( tmpIcon.getImage().getScaledInstance( -1, cMaxHeight,
																																										Image.SCALE_FAST));														
								}
								else
										icon = tmpIcon;
						}
				}
				return icon;
		}
}

//*************************************************
