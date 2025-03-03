package org.phypo.PPg.Sql;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.phypo.PPg.PPgWin.PPgAppli;
import org.phypo.PPg.PPgWin.PPgFrameChild;
import org.phypo.PPg.PPgWin.PPgTerm;

// **********************************

public class SqlTerm extends PPgFrameChild implements ActionListener{
		PPgTerm     cTerm;
		SqlServer  cServer;
		SqlServer  cServerBackup;
		SqlConnex  cSqlConnex;

		//		JMenuBar   cMenuBar;

		//-----------------------
		public SqlTerm( SqlServer pServer, SqlServer pServerBackup ){
				super( "SQL server : " + pServer.cName );

				cServer = pServer;
				cServerBackup = pServerBackup;


				cTerm = new PPgTerm("","");
			 	cTerm.addActionListener( this );

				getContentPane().add( cTerm );
				//				PPAppli.ThePPAppli.addChild( this );
				connect();

				//				setJMenuBar(cMenuBar = new JMenuBar());
				//				JMenuItem lItem = new JMenuItem( "Login" );
				//				lItem.addActionListener( this );
				//				cMenuBar.add( lItem)s
		}
		//-----------------------
		void connect(){
				while( true ) {
						if( (cServer.isComplete() || ( cServerBackup!= null && cServerBackup.isComplete()))
								&& (cSqlConnex = new SqlConnex( cServer, cServerBackup, cTerm.stream()  )).connect() ) {
								cTerm.appendln( "Connected to " + cSqlConnex.getSqlServerCurrent() );
								break;
						}
						SqlLogin lSqlLogin = new SqlLogin( PPgAppli.TheAppli, cServer, 300, 200  );
						if( !lSqlLogin.getValidation() ){
								cSqlConnex.disconnect();
								cSqlConnex = null;
								cTerm.appendln( "Not connected");
								break;
						}
				}
		}
		//-----------------------
		public boolean  sendCommand(String pQuery ){
				boolean lRes = cSqlConnex.sendCommandAndPrintResult( pQuery );
				cTerm.gotoEnd();
				return lRes;
		}
		//-----------------------
		@Override
		public void actionPerformed(ActionEvent pEv ){
			//		System.out.println( cTerm.getLastCmd() );

				//				if(  pEv.getActionCommand().equals( "Login")){
				//						new SqlLogin( PPAppli.ThePPAppli, cServer );
				//				}
				//				else
				//				if( actionPopupPerformed( pEv )==false){
				sendCommand( cTerm.getLastCmd() );
				//}
		}
	//-----------------------
		PPgTerm getTerm() { return cTerm; }
}
//***********************************
