package org.phypo.PPg.PPgWin;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;



//*************************************************
public class FontComboBox extends JComboBox<Font> {

		final Font[] cFonts;


	//------------------------------------------------
		public FontComboBox( final Component... components) {
				
				cFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
				
				
				Arrays.sort(cFonts, new Comparator<Font>() {
								@Override
										public int compare(Font f1, Font f2) {
										return f1.getName().compareTo(f2.getName());
								}
						});
				
				for (Font font : cFonts) {
						if (font.canDisplayUpTo(font.getName()) == -1) {
								addItem(font);
						}
				}
				
				addItemListener(new ItemListener() {
								@Override
										public void itemStateChanged(ItemEvent pEv ) {
										final Font lFont = (Font) pEv.getItem();

										/*										System.out.println( "SetFont name:" + lFont.getName() 
																				+ " | face:" + lFont.getFontName() 
																				+ " | familly:" + lFont.getFamily());
										*/
										for (Component comp : components) {
												setFontPreserveSize(comp, lFont);
										}
								}
						});
				
				setRenderer(new FontCellRenderer());
		}
		//------------------------------------------------
		public void select( Font pFont ){
				
				int i = 0;
				for( Font lFont : cFonts) {
						if ( lFont.canDisplayUpTo( lFont.getName() ) == -1) {
								//								System.out.println( i+ " "+ lFont.getName() + " <> " + pFont.getName() );

								if( lFont == pFont || lFont.getName().compareTo(pFont.getName()) == 0) {
										//										System.out.println( "FOUND" );
										setSelectedIndex( i );
										break;
								}
								i++;
						}
				}				
		}
	//------------------------------------------------
	private static class FontCellRenderer 
			implements ListCellRenderer<Font> {
		
		protected DefaultListCellRenderer renderer = 
				new DefaultListCellRenderer();
		
		public Component getListCellRendererComponent(
				JList<? extends Font> list, Font font, int index, 
				boolean isSelected, boolean cellHasFocus) {
			
			final Component result = renderer.getListCellRendererComponent(
					list, font.getName(), index, isSelected, cellHasFocus);
			
			setFontPreserveSize(result, font);
			return result;
		}
	}

	//------------------------------------------------

	private static void setFontPreserveSize(final Component comp, Font font) {
		final float size = comp.getFont().getSize();
		comp.setFont(font.deriveFont(size));
	}
}
//*************************************************
