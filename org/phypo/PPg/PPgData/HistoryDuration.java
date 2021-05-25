package org.phypo.PPg.PPgData;

import java.util.Vector;

public enum HistoryDuration { 
	Min1("1 minute"),
	Min5("5 minute"),
	Min10("10 minute"),
	Min15("15 minute"),
	Min30("30 minute"),
	Hour1("1 hour"),
	Hour2("2 hour"),
	Hour3("3 hour"),
	Hour6("6 hour"),
	Hour12("12 hour"),
	Day1("1 day"),
	Day2("2 day"),
	Day3("3 day"),
	Week1("1 week"),
	Month1("1 month"),
	Month3("3 month"),
	Month6("6 month"),
	Year("1 year"),
	All("All")
	;
	public final String cLabel;
	
	HistoryDuration( String iLabel ){cLabel = iLabel;}
		
	private static Vector<String> sLabels = null;
	
	public String getLabel() { return cLabel; }
	
	public static final Vector<String> GetLabels(){	
		if( sLabels == null ) {
			sLabels = new Vector<>();		
			for( HistoryDuration lVar : HistoryDuration.values() ) {
				sLabels.add(lVar.cLabel);
			}
		}
		return sLabels;
	}
	public static HistoryDuration Find(String iLabel){
		for( HistoryDuration lVar :  HistoryDuration.values()) {
			if( lVar.cLabel.equals(iLabel) ) {
				return lVar;
			}
		}
		return All;			
	}
	static int Find(HistoryDuration iHD){
		int i=0;
		for( HistoryDuration lVar :  HistoryDuration.values()) {
			if( lVar.equals(iHD) ) {
				return i;
			}
			i++;
		}
		return -1;			
	}
};

