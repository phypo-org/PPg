package org.phypo.PPgGame.PPgGame;

import java.awt.Color;



//*************************************************
public enum EnumFaction{
		Neutral( "Neutral", Color.lightGray , (byte)    0 ),
				Blue(    "Blue",    Color.blue,   (byte)    1 ),
				Red(     "Red",     Color.red,    (byte)    2 ),
				Green(   "Green",   Color.green,  (byte)    4 ),
				Yellow(  "Yellow",  Color.yellow, (byte)    8 ),
				Black(   "Black",   Color.black,  (byte)   16 ),
				White(   "White",   Color.white,  (byte)   32 ),
				Orange(  "Orange",  Color.orange, (byte)   64 ),
				Pink(    "Pink",    Color.pink,   (byte)  128 );


				private final String cMyName;
				private final Color  cMyColor;
				private final byte   cMyCode;

				EnumFaction( String pName, Color pColor, byte pCode) {
						cMyName  = pName;
						cMyColor = pColor;
						cMyCode  = pCode;
				}

				public String getName()   { return cMyName;  }
				public Color  getColor()  { return cMyColor; }
				public byte   getCode()   { return cMyCode;  }
};
//*************************************************
