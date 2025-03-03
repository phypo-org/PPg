package org.phypo.SqlTools;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.util.*;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.ArrayList;

import java.io.PrintStream;

//import com.natixis.eda.Viewer.Farm;
//import com.natixis.eda.Viewer.FrameFarmViewer;
//import com.natixis.eda.Viewer.FrameFarmViewer3D;
//import com.natixis.eda.Viewer.
import org.phypo.PPg.PPgWin.*;
import org.phypo.PPg.Sql.*;

import java.awt.datatransfer.DataFlavor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//***********************************
class MultiPanelTable extends PPgPanelTable implements GrafViewerControler {

    int cLastMenuLine;
    FrameSqlMultiTerm cMother_FrameSqlMultiTerm;

    static String cStrGraph = "Graphics dialog ...";

    String cMyQuery;

    public MultiPanelTable(FrameSqlMultiTerm pMother, String pMyQuery, String pName, PPgTable pTable,
			   boolean pFilterFlag) {
	super(pName, pTable, pFilterFlag);
	cMother_FrameSqlMultiTerm = pMother;
	cMyQuery = pMyQuery;
    }

    // -------------------------------------------------

    public void mousePressed(MouseEvent pEv) {

	cLastMenuLine = getTable().getRow(pEv);

	// System.out.println("MouseEvent" + pEv + " COUNT:" +
	// pEv.getClickCount() + " Last: " + cLastMenuLine);

	getTable().getJTable().setDefaultRenderer(Color.class, new MyTableRenderer());

	if (SwingUtilities.isRightMouseButton(pEv) == true && pEv.getClickCount() == 1) {

	    JPopupMenu lPopmenu = new JPopupMenu();

	    JMenuItem lItem;

	    if (cLastMenuLine != -1) {
		lItem = new JMenuItem(cStrGraph);
		lPopmenu.add(lItem);
		lItem.addActionListener(this);

	    }

	    lPopmenu.show(pEv.getComponent(), pEv.getX(), pEv.getY());
	}
    }

    // -------------------------------------------------
    public void actionPerformed(ActionEvent pEv) {

	System.out.println("actionPerformed");

	if (pEv.getActionCommand().equals(cStrGraph)) {
	    System.out.println("Create Graphics Dialog for request <<<" + cMyQuery + " >>>");
			
	    int lSelect = cMother_FrameSqlMultiTerm.cTabbedPane.getSelectedIndex();
		 	
	    FrameSqlMultiTerm.PanelSqlTerm lPanelTerm = cMother_FrameSqlMultiTerm.getCurrentTerm();
		 	
	    // System.out.println("$$$$$$$$$$$$$$$" + lSelect );
	    if( lPanelTerm !=null )
		new DialogGraf4Request("Create Graphics Dialog", getTable(), this, lPanelTerm );
	}
    }

    // ------------------------------------------------
    @Override
    public void doIt(GrafViewer pView, Object pData) {

	System.out.println("*************** " + pData );
	//		FrameSqlMultiTerm.PanelSqlTerm lPanelSqlTerm= (FrameSqlMultiTerm.PanelSqlTerm) cMother_FrameSqlMultiTerm.cTabbedPane.getTabComponentAt(pData);
	FrameSqlMultiTerm.PanelSqlTerm lPanelSqlTerm  = (FrameSqlMultiTerm.PanelSqlTerm)pData;
		
	lPanelSqlTerm.cTabbedPaneResults.addTab(pView.getTitle(), pView);
	lPanelSqlTerm.cTabbedPaneResults.setSelectedComponent(pView);
	

	//		cMother_FrameSqlMultiTerm.cCurrentTerm.cTabbedPaneResults.addTab(pView.getTitle(), pView);
	//		cMother_FrameSqlMultiTerm.cCurrentTerm.cTabbedPaneResults.setSelectedComponent(pView);
    }

}

// ***************************************
// ***************************************
// ***********************************
public class FrameSqlMultiTerm extends PPgFrameChild {
    // implements ActionListener, MenuListener{

    static Rectangle sRect = new Rectangle(0, 1999999999, 1, 1);

    public enum SendingMode {
	SendSimple(1), SendSelect(2), SendSelectWin(4), SendSimpleAndSelect(3), SendSimpleAndWin(5), SendSelectAndWin(
														      6), SendSimpleAndSelectAndWin(7);

	private final int cVal;

	public int getVal() {
	    return cVal;
	}

	SendingMode(int pVal) {
	    cVal = pVal;
	}
    };

    public final String cStrCut = "Cut";
    public final String cStrCopy = "Copy";
    public final String cStrPaste = "Paste";
    public final String cStrClearAll = "Clear all";
    public final String cStrNewEditor = "New Editor";

    final String cStrConnect = "Connect ";
    final String cStrSendBufferWin = "Send buffer ";
    final String cStrSendSelectWin = "Send selection ";

    final String cStrSendBufferTxt = "Send buffer / text ";
    final String cStrSendSelectTxt = "Send selection / text";
    final String cStrSend = "Send selection / insert, update";
    final String cStrCancelRequestTxt = "Cancel request";

    final String cStrGraph = "Create graphique";

    // =========================================
    public static class PanelSqlTerm extends JPanel
	implements MouseListener, ActionListener, MenuListener, SqlOutListener {

	FrameSqlMultiTerm cMother_FrameSqlMultiTerm;

	JEditorPane cTextCmd;
	JTextArea cTextResult;
	JTabbedPane cTabbedPaneResults;

	JPanel cSudPanel;
	JLabel cStatus;

	SqlConnex cSqlConnex;
	PrintStream cPrintStream;

	String cName;

	String cLastSendQuery;

	public JEditorPane getTextCmd() {
	    return cTextCmd;
	}

	public JTextArea getTextResult() {
	    return cTextResult;
	}

	SqlThreadRequest cThreadRequest;
	SqlDataResult cCurrentResult;

	// -------------------------
	PanelSqlTerm(FrameSqlMultiTerm pMother, String pName, String pText) {

	    cMother_FrameSqlMultiTerm = pMother;
	    cName = pName;

	    setLayout(new BorderLayout());

	    cTextCmd = new JEditorPane("text/plain", ""); // JTextArea( pText );
	    Font police = new Font("Monospaced", Font.PLAIN, 14);
	    cTextCmd.setFont(police);
	    cTextCmd.addMouseListener(this);

	    cTextResult = new JTextArea("");
	    cTextResult.setFont(police);

	    cTabbedPaneResults = new JTabbedPane();
	    cTabbedPaneResults.addTab("Log", new JScrollPane(cTextResult));

	    // ==============
	    JSplitPane lSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(cTextCmd,
											  JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
					       cTabbedPaneResults);
	    lSplit.setDividerLocation(100);
	    add(lSplit, BorderLayout.CENTER);
	    // ==============

	    cSudPanel = new JPanel();
	    cSudPanel.setLayout(new GridLayout(1, 0));
	    cStatus = new JLabel(cMother_FrameSqlMultiTerm.cServer.cName + "." + cMother_FrameSqlMultiTerm.cServer.cUser);

	    // setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );

	    cSudPanel.add(cStatus);

	    add(cSudPanel, BorderLayout.SOUTH);

	    cPrintStream = new PrintStream(new PPgTextStream(cTextResult), true);

	    connect(false);
	}

	// -----------------------
	void connect(boolean pLogin) {

	    while (true) {
		if (pLogin == false) {
		    if (cMother_FrameSqlMultiTerm.cServer != null && cMother_FrameSqlMultiTerm.cServer.isComplete()
			&& (cSqlConnex = new SqlConnex(cMother_FrameSqlMultiTerm.cServer, cMother_FrameSqlMultiTerm.cServerBackup, cPrintStream))
			.connectOrLogin(PPgAppli.TheAppli, -1, -1)) {
			cPrintStream.println("Connected to " + cMother_FrameSqlMultiTerm.cServer.cName);

			// cTextResult.append( "Connected to " +
			// cMother_FrameSqlMultiTerm.cServer.cName );
			break;
		    }
		}
		pLogin = false;

		SqlLogin lSqlLogin = new SqlLogin(PPgAppli.TheAppli, cMother_FrameSqlMultiTerm.cServer, 300, 200);
		if (lSqlLogin.getValidation() == false) {
		    cSqlConnex.disconnect();
		    cSqlConnex = null;
		    cTextResult.append("Not connected");
		    break;
		}
	    }
	}
	// ----------------------

	public void gotoEnd() {
	    cTextResult.scrollRectToVisible(sRect);
	}

	// ------------------------------------------------
	public void select(int pBegin, int pEnd) {
	    cTextCmd.select(pBegin, pEnd);
	}

	// ------------------------------------------------
	public void clearAll() {
	    cTextCmd.setText("");
	    cTextResult.setText("");
	}

	// ------------------------------------------------
	// ------------------------------------------------
	// ------------------------------------------------

	protected JPopupMenu initPopup(JPopupMenu pPopmenu) {

	    /*
	     * for( int i=1; i< cMother_FrameSqlMultiTerm.cMenuSql.getItemCount(); i++ ){
	     * 
	     * if( cTextCmd.getSelectedText() == null )
	     * cMother_FrameSqlMultiTerm.cMenuSql.getItem(i).setEnabled(false); else
	     * cMother_FrameSqlMultiTerm.cMenuSql.getItem(i).setEnabled(true); }
	     */
	    boolean lSelectedText = cTextCmd != null && cTextCmd.getSelectedText() != null;
	    boolean lText = cTextCmd != null && cTextCmd.getText() != null && cTextCmd.getText().length() > 0;
	    JMenuItem lItem;

	    pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrCut)));
	    lItem.addActionListener(this);
	    lItem.setEnabled(lSelectedText);

	    pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrCopy)));
	    lItem.addActionListener(this);
	    lItem.setEnabled(lSelectedText);

	    pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrPaste)));
	    lItem.addActionListener(this);
	    if (Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable(DataFlavor.stringFlavor) == false
		|| cTextCmd.isEditable() == false) {
		lItem.setEnabled(false);
	    }

	    pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrClearAll)));
	    lItem.addActionListener(this);

	    pPopmenu.add(new JSeparator());

	    if ((cMother_FrameSqlMultiTerm.cSendingMode.getVal() & SendingMode.SendSelect.getVal()) != 0) {
		pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrSendBufferWin)));
		lItem.addActionListener(this);
		lItem.setEnabled(lText);

		pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrSendSelectWin)));
		lItem.addActionListener(this);
		lItem.setEnabled(lSelectedText);

		pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrSendBufferTxt)));
		lItem.addActionListener(this);
		lItem.setEnabled(lText);

		pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrSendSelectTxt)));
		lItem.addActionListener(this);
		lItem.setEnabled(lSelectedText);

		pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrCancelRequestTxt)));
		lItem.addActionListener(this);
	    }

	    if ((cMother_FrameSqlMultiTerm.cSendingMode.getVal() & SendingMode.SendSimple.getVal()) != 0) {
		pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrSend)));
		lItem.addActionListener(this);
		if (cTextCmd.getSelectedText() == null)
		    lItem.setEnabled(false);
	    }

	    pPopmenu.add(new JSeparator());

	    pPopmenu.add((lItem = new JMenuItem(cMother_FrameSqlMultiTerm.cStrNewEditor)));
	    lItem.addActionListener(this);

	    return pPopmenu;
	}

	// -------------------------------------------
	// -------------------------------------------
	// implements MouseListener
	// -------------------------------------------
	// -------------------------------------------
	public void mousePressed(MouseEvent pE) {

	    if (SwingUtilities.isRightMouseButton(pE) == true && pE.getClickCount() == 1) {

		JPopupMenu lPopmenu = new JPopupMenu();
		initPopup(lPopmenu);

		lPopmenu.show(pE.getComponent(), pE.getX(), pE.getY());
	    }
	}

	// --------------------------
	public void mouseReleased(MouseEvent pEv) {
	    ;
	}

	public void mouseEntered(MouseEvent pEv) {
	    ;
	}

	public void mouseExited(MouseEvent pEv) {
	    ;
	}

	public void mouseClicked(MouseEvent pEv) {
	    ;
	}

	// ------------------------------------------------
	int cNbResult;

	public void actionPerformed(ActionEvent pEv) {

	    System.out.println("actionPerformed");

	    if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrNewEditor)) {
		cMother_FrameSqlMultiTerm.createNewEditor();
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrCut)) {
		cTextCmd.cut();
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrCopy)) {
		cTextCmd.copy();
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrPaste)) {
		cTextCmd.paste();
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrClearAll)) {
		clearAll();
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrConnect)) {

		connect(true);
	    }

	    String lQuery = null;
	    if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrSendSelectTxt)) {
		if ((lQuery = cTextCmd.getSelectedText()) != null) {
		    sendRequest(lQuery, false);
		}
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrSendSelectWin)) {
		if ((lQuery = cTextCmd.getSelectedText()) != null) {
		    sendRequest(lQuery, true);
		}
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrSendBufferWin)) {
		if ((lQuery = cTextCmd.getText()) != null) {
					
		    // METTRE UN DICTIONNAIRE AVEC DES VALEUR RENSEIGNE 
		    // Control.getDico() ; // par exemple
		    // Et faire un truc efficace pour tout remplacer
		    //lQuery.replace( "%DATE%",  );
		    //lQuery.replace( "%DATE_TIME%", "" );
														
		    sendRequest(lQuery, true);
		}
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrSendBufferTxt)) {
		if ((lQuery = cTextCmd.getText()) != null) {
		    sendRequest(lQuery, false);
		}
	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrSend)) {
		if ((lQuery = cTextCmd.getSelectedText()) != null) {
		    System.out.println("Send SQL:");
		    boolean lRes = cSqlConnex.sendCommandWithGo(lQuery);
		    gotoEnd();
		}

	    } else if (pEv.getActionCommand().equals(cMother_FrameSqlMultiTerm.cStrCancelRequestTxt)) {
		cSqlConnex.cancelRequest();
		cMother_FrameSqlMultiTerm.setComponentName(this, cName + " x ");
	    }
	}

	// ------------------------------------------------
	void sendRequest(String pQuery, boolean pWindows) {

	    if (cThreadRequest != null && cThreadRequest.isRunning()) {

		JOptionPane.showMessageDialog(null, "A request is  already running ... ", "Information",
					      JOptionPane.INFORMATION_MESSAGE);
		return;
	    }

	    if (pWindows) {
		cCurrentResult = new SqlDataResult(cMother_FrameSqlMultiTerm.cServer, "R" + cNbResult++, pQuery, this);
		cThreadRequest = new SqlThreadRequest(cSqlConnex, pQuery, cCurrentResult);

	    } else {
		cThreadRequest = new SqlThreadRequest(this, cSqlConnex, pQuery);
	    }
	    cLastSendQuery = pQuery;

	    cMother_FrameSqlMultiTerm.setComponentName(this, cName + " @");
	    cPrintStream.println("running ...");

	    cThreadRequest.start();
	}

	// ------------------------------------------------
	public void menuCanceled(MenuEvent pEv) {

	}

	// ------------------------------------------------
	public void menuSelected(MenuEvent pEv) {
	}

	// ------------------------------------------------
	public void menuDeselected(MenuEvent pEv) {

	}

	// ------------------------------------------------
	// Appelé par le thread d'execution au moment du retour des resultats.
	// Creation d'un nouvel set de resultat dans le tabbed panel !

	public void SqlOutListener_setResult(SqlDataResult pResult) {

	    // pour debug cTabbedPane.addTab( cWinTitle, new JTextArea( "RESULT
	    // WIN" ));
	    final SqlDataResult lCurrent = cCurrentResult;
	    final int fNbLine = pResult.getNbRow();
	    final String fName = cName;
	    final PanelSqlTerm fMyThis = this;
	    final String fQuery = cLastSendQuery;

	    // ++++++++++
	    SwingUtilities.invokeLater(new Runnable() {

		    public void run() {

			if (fNbLine >= 0) {
			    MultiPanelTable lPanelTable;

			    cTabbedPaneResults
				.addTab(pResult.cWinTitle,
					(lPanelTable = new MultiPanelTable(cMother_FrameSqlMultiTerm, fQuery, pResult.cWinTitle,
									   new PPgTable(pResult.getRows().get(0), (ArrayList) pResult.getRows()),
									   true)));

			    lPanelTable.getTable().getJTable().getTableHeader().addMouseListener(lPanelTable);
			} else {

			    JOptionPane.showMessageDialog(null, "Information", "No data found for this request ",
							  JOptionPane.INFORMATION_MESSAGE);
			}
			cThreadRequest = null;
			cMother_FrameSqlMultiTerm.setComponentName(fMyThis, fName);
		    }
		});
	    // ++++++++++

	}

	// ------------------------------------------------
	public void SqlOutListener_setResult() {

	    final String fName = cName;
	    final PanelSqlTerm fMyThis = this;

	    // ++ ++++++++
	    SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
			cMother_FrameSqlMultiTerm.setComponentName(fMyThis, fName);
		    }
		});
	    // ++++++++++

	}

    }

    // ==========================================
    // ==========================================
    // ==========================================

    JTabbedPane cTabbedPane = null;

    JMenuBar cMenubar;
    JMenu cMenuEdit;

    public JMenuBar getJMenuBar() {
	return cMenubar;
    }

    public JMenu getMenuEdit() {
	return cMenuEdit;
    }

    SendingMode cSendingMode = SendingMode.SendSimpleAndSelectAndWin;

    SqlServer cServer;
    SqlServer cServerBackup;

    JMenu cMenuSql = null;

    PanelSqlTerm cCurrentTerm;
    ArrayList<PanelSqlTerm> cArrayTerm = new ArrayList<>();

    // ------------------------------------------------
    final String sMenuEdit = "Edit";

    // -------------------------------------
    public FrameSqlMultiTerm(SqlServer pServer, SqlServer pServerBackup, String pText) {
	super(pServer.cName + "." + pServer.cUser);

	cServer = pServer;

	/*
	 * setJMenuBar((cMenubar = new JMenuBar())); // Edit cMenuEdit = new
	 * JMenu( sMenuEdit ); cMenuEdit.addMenuListener( this );
	 * cMenubar.add(cMenuEdit);
	 * 
	 * cMenuSql = new JMenu( "sql server" ); cMenuSql.addMenuListener( this
	 * ); cMenubar.add( cMenuSql );
	 */

	cTabbedPane = new JTabbedPane();

	createNewEditor();

	getContentPane().add(cTabbedPane, BorderLayout.CENTER);

	pack();
	setVisible(true);
    }

    // ------------------------------------------------
    public PanelSqlTerm getCurrentTerm() {

	int lSelected = cTabbedPane.getSelectedIndex();
	if (lSelected != -1)
	    return cArrayTerm.get(lSelected);

	return null;
    }

    // ------------------------------------------------
    public void createNewEditor() {
	System.out.println("createNewEditor");

	cArrayTerm.add(cCurrentTerm = new PanelSqlTerm(this, "" + (cArrayTerm.size() + 1), ""));
	cTabbedPane.addTab(cCurrentTerm.cName, cCurrentTerm);

    }

    // ------------------------------------------------
    public JEditorPane getCurrentText() {
	PanelSqlTerm lTerm = getCurrentTerm();
	if (lTerm != null)
	    return lTerm.cTextCmd;

	return null;
    }

    // ------------------------------------------------
    void setComponentName(PanelSqlTerm pComponent, String pName) {
	int lIndex = cTabbedPane.indexOfComponent(pComponent);
	if (lIndex > -1)
	    cTabbedPane.setTitleAt(lIndex, pName);
    }

    // ------------------------------------------------

};
// **********************************
