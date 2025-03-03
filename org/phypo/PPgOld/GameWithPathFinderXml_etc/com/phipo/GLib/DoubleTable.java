package com.phipo.GLib;


//*************************************************
public class DoubleTable{

	final	public DoubleTable( int pSize) {
				double [] cTab = new double[pSize];		
				for( int i=0;i<pSize;i++)
						cTab[i] = 0;
		}

		final	public double get(int pPos) { return cTab[pPos];}
		final public void   set(int pPos, double pVal) { cTab[pPos] = pPos;
};
