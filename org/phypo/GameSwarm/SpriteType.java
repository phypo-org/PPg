package org.phypo.GameSwarm;

import java.io.*;
import java.lang.*;
import java.util.*;

import javax.swing.*;

import java.io.*;
import java.io.PrintStream;
import java.util.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.WindowConstants;


import java.awt.geom.Point2D;



import org.phypo.PPgGame.PPgGame.*;

//=====================================		
public enum SpriteType{
		SpriteWeapon(1),
				SpriteShip(2),
				SpriteFleet(3),
				SpriteExplosion(4);
		public final int code;
		SpriteType( int pCode) {code=pCode;}
};
//=====================================
