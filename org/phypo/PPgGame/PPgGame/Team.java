package org.phypo.PPgGame.PPgGame;


import java.io.*;
import java.util.*;
import java.lang.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;

import org.phypo.PPg.PPgUtils.*;


//*************************************************
//*************************************************
public class Team{
		public String cName=null;
		
		public final static HashMap<String, Team> sHashTeam = new HashMap<String, Team>();
		

		public static Team  GetTeam( String pName ){
				return sHashTeam.get( pName );
		}
}
//*************************************************
