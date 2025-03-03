package org.phypo.SqlTools;


	
import java.awt.BorderLayout;
import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.phypo.PPg.PPgWin.PPgTable;


//***************************************
public class GrafViewer extends  JPanel{

		
    //		DefaultCategoryDataset cDataset;
			
    JLabel     cLabelTitle;
    String     cTitle;
    PPgTable    cTable;
    ArrayList<Integer> cCourbes;
    ArrayList<Integer> cValues;
			
    HashMap<String, ArrayList<Integer> > cSortCourbesLines = null;
			
    int cAbsciss; 
			
    public String getTitle() { return cTitle; }

    //-----------------------

    public GrafViewer(  String pTitle, PPgTable pTable, ArrayList<Integer> pCourbes, ArrayList<Integer> pValues, int pAbsciss ){
				
	cTitle   = pTitle;
	cTable   = pTable;
	cCourbes = pCourbes;
	cValues  = pValues;
	cAbsciss = pAbsciss;
				
	setLayout( new BorderLayout() );

	cLabelTitle = new JLabel( cTitle );				
	add( cLabelTitle,  BorderLayout.NORTH  );
				
				
	filterCourbes();									
    }
    //----------------------------------------
    // Si pCourbes est renseigner, il faut trier les lignes entres les differentes courbes
    // On  va le faire avec une Hasmap avec comme clefs concatenations des valeurs des champs des courbes 
    // (A l'utilisateur de ne pas mettre n'importe quoi !)
			
    void filterCourbes(){
	//			if( cCourbes == null || cCourbes.size() == 0 ) //A FAIRE PLUS TARD
	//return ;
	
	int lNbVal = cTable.getRowCount();                // le nombre de row 
	System.out.println( "filterCourbes "+ lNbVal);
			
	cSortCourbesLines = new HashMap<>();
				
				
	for( int lLine = 0; lLine < lNbVal; lLine++){   // On va cycler sur toutes les lignes
	    StringBuilder  lCompositeKey=null;					
				
	    //	System.out.println( "\tfilterCourbes lLine" +lLine);
			
	    if( cCourbes == null || cCourbes.size() == 0 )
		{
		    // A OPTIMISER TRES LARGEMENT !!! 
		    if( lCompositeKey == null ) lCompositeKey = new StringBuilder( cTitle );					
		}
	    else
		{
		    for(	 int lSimpleKey : cCourbes ){

			if( lCompositeKey == null) 
			    lCompositeKey = new StringBuilder();
			else
			    lCompositeKey.append('/');	
					
			lCompositeKey.append( cTable.getValueAt( lLine, lSimpleKey) );										
			//	System.out.println( "\t\tfilterCourbes lSimpleKey " + lSimpleKey + "->" + lCompositeKey );
		    }		
		}
					
	    String lStrKey = lCompositeKey.toString();
	    ArrayList<Integer> lArrayLines = cSortCourbesLines.get( lStrKey );					
	    if( lArrayLines == null ) {
		lArrayLines = new ArrayList<>();
		cSortCourbesLines.put( lStrKey, lArrayLines );
	    }
					
					
	    lArrayLines.add( lLine);	

	    //	System.out.println( "\t\t\tfilterCourbes  " + lCompositeKey  + " add line:" + lLine);
	}
				
	System.out.println( "************** Filter Size : " + cSortCourbesLines.size() );
	for (String lMapKey : cSortCourbesLines.keySet()) {
	    System.out.println( lMapKey + " size:" + cSortCourbesLines.get(lMapKey).size());
	}
    }
    //----------------------------------------
    //----------------------------------------
    public final static Color sMyColors[] = { 
	Color.RED,
	Color.GREEN,
	Color.BLUE, 
	Color.MAGENTA,
	Color.ORANGE,
	Color.PINK,
	Color.BLACK,
	Color.YELLOW,
	Color.DARK_GRAY,
	Color.CYAN
    };
			
    static public Color GetColor(int i){
				
	return sMyColors[ i % sMyColors.length ];
    }
}
//****************************************************

	 
					
