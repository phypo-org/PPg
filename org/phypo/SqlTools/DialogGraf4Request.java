package org.phypo.SqlTools;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.phypo.PPg.PPgWin.PPgAppli;
import org.phypo.PPg.PPgWin.PPgField;
import org.phypo.PPg.PPgWin.PPgInputField;
import org.phypo.PPg.PPgWin.PPgTable;

//*********************************************************
public class DialogGraf4Request   extends JDialog 
								  implements  ActionListener, ChangeListener, ItemListener	{
	
	private static final String PPgJFrame = null;
	PPgTable cMyTable;
	String  cTitle;

	JButton cButtonCancel ;
	JButton cButtonOk     ;
	
	
	//ArrayList<Checkbox>       cChoices = new ArrayList<>();
	ArrayList<Checkbox>   cCourbes  = new ArrayList<>();
	ArrayList<Checkbox>   cValues  = new ArrayList<>();
	ArrayList<Checkbox>   cAbsciss  = new ArrayList<>();
	
	CheckboxGroup         cAbscissGroup = new CheckboxGroup();
	GrafViewerControler   cMyGrafControl;
	
	static final String  sCloseDialog="Close";
	static final String  sCreateGrafDialog="Create graphic";
	static int  sNumGraf=1;
	
	PPgInputField cFieldTitle;
	Checkbox cCheck3D;
	Object cData;
	
	
	//ArrayList<CheckboxGroup>   cAbscissGroups  = new ArrayList<>();
	//---------------------------------------------------------------

	public DialogGraf4Request( String pTitle, PPgTable pTable, GrafViewerControler pGrafControl, Object pData){
		
		super( PPgAppli.GetAppli(), pTitle,  false );
		cMyGrafControl = pGrafControl;
		cTitle = pTitle;
		cMyTable = pTable;
		cData = pData;
		
		int lNbRow = cMyTable.getColumnCount();
		
		// On va faire un tableau avec une case par champs numerique pour pouvoir selectionner les valeurs qui nous interesse
		
		getContentPane().setLayout( new BorderLayout() );		
		JPanel lSouth = new JPanel();
		lSouth.setLayout( new GridLayout( 1, 4 ));
		lSouth.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));


		cButtonOk= new JButton( sCreateGrafDialog );
		cButtonOk.setActionCommand( sCreateGrafDialog);
		cButtonOk.addActionListener( this );
		lSouth.add( cButtonOk );
		getContentPane().add( lSouth, BorderLayout.SOUTH );

		cButtonCancel = new JButton( sCloseDialog );
		cButtonCancel.setActionCommand( sCloseDialog );
		cButtonCancel.addActionListener( this );
		lSouth.add( cButtonCancel );

		JPanel lNorth  = new JPanel();
		getContentPane().add( lNorth, BorderLayout.NORTH );
		
		lNorth.setLayout( new GridLayout( 1, 2 ));
		lNorth.add( new JLabel( "Choose your fields" ));
		lNorth.add( (cFieldTitle = new PPgInputField( "Title", "G"+sNumGraf++, PPgField.HORIZONTAL)));
		Checkbox cCheck3D = new Checkbox( "3D");			
		lNorth.add( cCheck3D );
		cCheck3D.addItemListener(this);
			
		
		JPanel lCenter = new JPanel();
		getContentPane().add( lCenter, BorderLayout.CENTER );
		lCenter.setLayout( new GridLayout( lNbRow+1, 3 ));
	
		lCenter.add( new JLabel( "Courbe"));
		lCenter.add( new JLabel( "Value"));
		Checkbox lAbsciss = new Checkbox( "Absciss", cAbscissGroup, true);
		cAbsciss.add(lAbsciss);			
		lCenter.add( lAbsciss );
		
		for( int i=0; i < lNbRow; i++ ){	
	//		CheckboxGroup lGroup = new CheckboxGroup();
	//		cAbscissGroups.add(lGroup);
			
			Checkbox lCourbe = new Checkbox(  cMyTable.getColumnName(i));
			cCourbes.add(lCourbe  );			
			lCenter.add( lCourbe );
			lCourbe.addItemListener(this);
	
			Checkbox lValue = new Checkbox( "" );
			cValues.add( lValue );			
			lCenter.add( lValue );
			lValue.addItemListener(this);
			
			lAbsciss = new Checkbox( "", cAbscissGroup, false );
			cAbsciss.add(lAbsciss);			
			lCenter.add( lAbsciss);			
			lAbsciss.addItemListener(this);
			
//			cAbscissGroup.add(lAbsciss);
			
			//lGroup.add(lCourbe);
		//	lGroup.add(lValue);
		//	lGroup.add(lAbsciss);
			
		}				
		
		pack();
		setVisible(true);			
	}
	
//---------------------------------------------------------------
	@Override
	public void itemStateChanged(ItemEvent e) {		
		
		// TODO Auto-generated method stub
	//	System.out.println( "itemStateChanged  " + e);
		
		if( e.getSource() == cCheck3D){						
			return;
		}
		
		
		int i=0;
		for( Checkbox lCheck : cCourbes){
			if( e.getSource().equals( lCheck )){  // On a cliquer sur un bouton de la 1ere colonne
		//		System.out.println("lCheck.getState()" );
				
				if( lCheck.getState() == false ){					
					return;
				}
				if( cValues.get(i).getState(  ))
					cValues.get(i).setState( false );
				
				if( cAbsciss.get(i+1).getState() )
					cAbsciss.get(0).setState( true );	
				return;
			}
			i++;
		}	
		
		i=0;
		for( Checkbox lCheck : cValues){
			if( e.getSource().equals( lCheck )){  // On a cliquer sur un bouton de la 1ere colonne
			//	System.out.println("lCheck.getState()" );
				
				if( lCheck.getState() == false ){					
					return;
				}
				if( cCourbes.get(i).getState(  ))
					cCourbes.get(i).setState( false );
				
				if( cAbsciss.get(i+1).getState() )
					cAbsciss.get(0).setState( true );	
				return;
			}
			i++;
		}	
		i=0;
		for( Checkbox lCheck : cAbsciss){
			if( e.getSource().equals( lCheck )){  // On a cliquer sur un bouton de la 1ere colonne
			//	System.out.println("lCheck.getState()" );
				if( i <= 0 )
						return;
				i--;

				if( cCourbes.get(i).getState(  ))
					cCourbes.get(i).setState( false );
				
				if( cValues.get(i).getState(  ))
					cValues.get(i).setState( false );
				
				return;
			}
			i++;
		}	
	}
	//-------------------------------------------
	@Override
	public void stateChanged(ChangeEvent e) {
		
		System.out.println( "stateChanged  " + e);
	}
	//-------------------------------------------

	@Override
	public void actionPerformed(ActionEvent pEv) {
		// TODO Auto-generated method stub
		if( pEv.getActionCommand().equals( sCloseDialog )){
			dispose();
		} else if( pEv.getActionCommand().equals( sCreateGrafDialog )){
			
		ArrayList<Integer> lCourbes = new ArrayList();
		int i =0;
		for( Checkbox lCheck : cCourbes){			
			if( lCheck.getState() ){					
				lCourbes.add( new Integer( i ));	
			}
			i++;
		}	
		
		ArrayList<Integer> lValues = new ArrayList();	
		i=0;
		for( Checkbox lCheck : cValues){
			if( lCheck.getState() ){					
				lValues.add( new Integer( i ));	
			}
			i++;
		}
		
		int lAbsciss = -1;  // -1 indique que l'on prend le rang comme abscisse
		for( Checkbox lCheck : cAbsciss){
			if( lCheck.getState() ){
				break;
			}
			lAbsciss++;
		}
				
		
		/*if(  lCourbes.size() == 0  ){
			//METTRE UUUN MESSAGE
				return;		
		}*/
		
		GrafViewer lGrafView;
//		if( cCheck3D.getState() )             
//	// A FAIRE		lGrafView = new GrafViewer3D( cTitle, cMyTable, lCourbes, lValues, lAbsciss ); 
//		else
			lGrafView = new GrafViewer2D( cFieldTitle.getString(), cMyTable, lCourbes, lValues, lAbsciss );
		 
		if( cMyGrafControl != null ) {
			cMyGrafControl.doIt( lGrafView, cData );
		} 
		else
		{
			JPanel lEast  = new JPanel();
			
			getContentPane().add( lEast, BorderLayout.EAST );		
			lEast.add( lGrafView);
				
			pack();
			setVisible(true);	
			//dispose();
		}
	}
	}
}

//*********************************************************				
