package org.phypo.SqlTools;

import java.awt.BorderLayout;
import java.awt.Color;
	// SQL
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//import com.mysql.fabric.xmlrpc.base.Array;
import org.phypo.PPg.PPgWin.PPgTable;


//***************************************
public class GrafViewer2D extends  GrafViewer{

	final XYSeriesCollection cDataset;
	JFreeChart cActivityChart;

	ArrayList<XYSeries> cTabSeries = new ArrayList<>();


	ChartPanel          cChartPanel;

	ArrayList<String>      cStringSeries;


	
	XYLineAndShapeRenderer         cRenderer = new XYLineAndShapeRenderer( );
	
	//-----------------------	
	public  GrafViewer2D( String pTitle, PPgTable pTable, ArrayList<Integer> pCourbes, ArrayList<Integer> pValues, int pAbsciss ){
		super( pTitle, pTable, pCourbes, pValues, pAbsciss );
		
	//	System.out.println( "");
		//===================
		String lStrAbsciss = "Count";
		if( cAbsciss > -1 )
			lStrAbsciss =	cTable.getColumnName( cAbsciss );
	
		String lStrOrdonate="";
		
		for( Integer lValueIndex : cValues ) {  //lValueIndex 
			
			System.out.println( "\t\tlValueIndex " + lValueIndex );
			
			lStrOrdonate = lStrOrdonate + " " + cTable.getColumnName( lValueIndex );		 	
		}
		//===================
		
		cDataset = new XYSeriesCollection( );          

		cActivityChart = ChartFactory.createXYLineChart(	cTitle,																													
															lStrAbsciss ,
															lStrOrdonate,
															cDataset,
															PlotOrientation.VERTICAL ,
															true , true , false);
		cChartPanel = new ChartPanel(cActivityChart); 

		cChartPanel.setPreferredSize( new java.awt.Dimension( 800 , 600) );

		final XYPlot plot = cActivityChart.getXYPlot( );

		plot.setRenderer( cRenderer ); 
		add( cChartPanel,  BorderLayout.CENTER  );
		
		System.out.println( "before fillDataset");

		fillDataset();
		
	}
	//-------------------------------
	void fillDataset() {
		
		String lStrAbsciss = "Count";
		if( cAbsciss > -1 )
			lStrAbsciss =	cTable.getColumnName( cAbsciss );
		
		System.out.println( "lStrAbsciss" + lStrAbsciss);
	
		Collection< ArrayList<Integer> > lCourbesIndexs = cSortCourbesLines.values();
		
		Set<String> lKeySet = cSortCourbesLines.keySet();
		
		Object[] lTitlesCourbes = lKeySet.toArray();
		
		int lNbCourbe =0;
		for( ArrayList<Integer> lCourbeIndex : lCourbesIndexs ) {
	
			String lCourbeTitle = (String)lTitlesCourbes[lNbCourbe];
			
			System.out.println( "\tlCourbeTitle " + lNbCourbe + " " + lCourbeTitle );
			
			for( Integer lValueIndex : cValues ) {  //lValueIndex 
				
			    //	System.out.println( "\t\tlValueIndex " + lValueIndex );
				
				String lStrOrdonate = cTable.getColumnName( lValueIndex );		 	
				//	System.out.println( "\t\tlStrOrdonate " + lStrOrdonate);
								
				XYSeries lXYSerie    = new XYSeries( lCourbeTitle + " " + lStrOrdonate );
				cTabSeries.add( lXYSerie );
				cDataset.addSeries( lXYSerie );
				cRenderer.setSeriesPaint( lNbCourbe , GetColor(lNbCourbe) );	

				//	System.out.println( "\t\tlCourbeIndex.size() " + 	lCourbeIndex.size() );
				
				int lNumberLine = 0;
				//===========================
				for( Integer lIndexRow : lCourbeIndex ) {
					
			//		System.out.println("lNumberLine " + lNumberLine+ " lIndexRow : " + lIndexRow);
					
					Object lObjValue = cTable.getValueAt( lIndexRow, lValueIndex);
					float  lValueConverFloat =0;
					String lValueStrConvert = String.valueOf( lObjValue );
					
				//	System.out.println("\t\t\tlValueStrConvert " + lValueStrConvert);	
					
					try {
						lValueConverFloat = Float.valueOf(lValueStrConvert);						
						
					} catch( Exception e ) {
						lValueConverFloat = 0;
					}
				
					float  lValueAbsciss = lNumberLine++;
					
					if( cAbsciss != -1 ) {
						Object lObjAbsciss = cTable.getValueAt( lIndexRow, cAbsciss );						
						String lAbscissStrConvert = String.valueOf( lObjAbsciss);
						//		System.out.println( "\t\t\tlAbscissStrConvert "+lAbscissStrConvert);			
						try {
							lValueAbsciss = Float.valueOf(lAbscissStrConvert);						
						
						} catch( Exception e ) {
							lValueAbsciss = 0;
						}
					}
					//	System.out.println( "\t\t\t\tlXYSerie.add " + lValueAbsciss + " : " + lValueConverFloat );
					lXYSerie.add( lValueAbsciss, lValueConverFloat);
				}
				//===========================				
			} //for( Integer lValueIndex : cValues )
			lNbCourbe++;
		} //for( ArrayList<Integer> lCourbeIndex : lCourbesIndexs ) {	
	} // fillDataset
	
}

/*
SELECT  timePeriod, SUBSTRING_INDEX( SUBSTRING_INDEX( processuid,'_',-2), '_', 1),
SUM( CPU_10 )/10   CPU_10,
SUM( MEM_10 )/10   MEM_10		
FROM ( SELECT processid,SUBSTRING_INDEX( SUBSTRING_INDEX( processuid,'_',-2), '_', 1) as processuid,
	from_unixtime((unix_timestamp(updatedate) div 300)* 300) as timePeriod,
	AVG( CPU_percent_10 )   CPU_10,
	AVG( MEM_percent_10)    MEM_10
	FROM processhistory
 	WHERE
        updatedate >= "2016/11/08 00:00:00"
	AND updatedate <"2016/11/08 12:00:00"
-- 	AND  SUBSTRING_INDEX( SUBSTRING_INDEX( processuid,'_',-2), '_', 1)='PREX1'
	GROUP BY 1, 2, 3
	)  AS processhistory
GROUP BY 1,2
ORDER BY 1,2

*/
