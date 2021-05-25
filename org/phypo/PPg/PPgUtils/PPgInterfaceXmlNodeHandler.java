package org.phypo.PPg.PPgUtils;


import org.w3c.dom.*;


//**********************************************************
// Prend en charge le traitement d un noeud et de sa descendance

public interface PPgInterfaceXmlNodeHandler {

		public boolean processNode(  PPgXml pXml, Node pMotherNode, Node pNode, PPgInterfaceXmlNodeHandler pHandler);
}

//*************************************************
