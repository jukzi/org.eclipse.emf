/**
 * <copyright>
 *
 * Copyright (c) 2002-2004 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: XMISaveImpl.java,v 1.8 2005/04/26 17:37:46 elena Exp $
 */
package org.eclipse.emf.ecore.xmi.impl;


import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.w3c.dom.Element;


public class XMISaveImpl extends XMLSaveImpl
{
  protected boolean xmiType;

  protected static final String XMI_ID_NS = XMIResource.XMI_NS + ":" + XMIResource.XMI_ID; // xmi:id
  protected static final String XMI_TAG_NS = XMIResource.XMI_NS + ":" + XMIResource.XMI_TAG_NAME; // xmi:XMI
  protected static final String XMI_TYPE_NS = XMIResource.XMI_NS + ":" + XMLResource.TYPE; // xmi:type
  protected static final String XMI_VER_NS = XMIResource.XMI_NS + ":" + XMIResource.VERSION_NAME; // xmi:version
  protected static final String XMI_XMLNS = XMLResource.XML_NS + ":" + XMIResource.XMI_NS; // xmlns:xmi

  public XMISaveImpl(XMLHelper helper)
  {
    super(helper);
    idAttributeName = XMI_ID_NS;
    idAttributeNS = XMIResource.XMI_NS;
  }

  public XMISaveImpl(Map options, XMLHelper helper, String encoding)
  {
    super(options, helper, encoding);
    this.xmiType = Boolean.TRUE.equals(options.get(XMIResource.OPTION_USE_XMI_TYPE));
    idAttributeName = XMI_ID_NS;
    idAttributeNS = XMIResource.XMI_NS;
  }

  protected void init(XMLResource resource, Map options)
  {
    super.init(resource, options);
    this.xmiType = Boolean.TRUE.equals(options.get(XMIResource.OPTION_USE_XMI_TYPE));
    helper.getPrefixToNamespaceMap().put(XMIResource.XMI_NS, XMIResource.XMI_URI);
  }

  public Object writeTopObjects(List contents)
  {
    if (!toDOM)
    {
      doc.startElement(XMI_TAG_NS);
      Object mark = doc.mark();

      for (int i = 0, size = contents.size(); i < size; i++)
      {
        EObject top = (EObject)contents.get(i);
        EClass eClass = top.eClass();
        String name = helper.getQName(eClass);
        doc.startElement(name);
        saveElementID(top);
      }

      doc.endElement();
      return mark;
    }
    else
    {
      // create dummy documentElement
      currentNode = document.createElementNS(XMIResource.XMI_URI, XMI_TAG_NS);
      document.appendChild(currentNode);
      for (int i = 0, size = contents.size(); i < size; i++)
      {
        EObject top = (EObject)contents.get(i);
        EClass eClass = top.eClass();
        helper.populateNameInfo(nameInfo, eClass);
        currentNode = currentNode.appendChild(document.createElementNS(nameInfo.getNamespaceURI(), nameInfo.getQualifiedName()));
        handler.recordValues(currentNode, null, null, top);
        saveElementID(top);
      }
      return null;
    }
  }

  protected void saveTypeAttribute(EClass eClass)
  {
    if (xmiType)
    {
      if (!toDOM)
      {
        doc.addAttribute(XMI_TYPE_NS, helper.getQName(eClass));
      }
      else
      {
        ((Element)currentNode).setAttributeNS(XMIResource.XMI_URI, XMI_TYPE_NS, helper.getQName(eClass));
      }
    }
    else
    {
      super.saveTypeAttribute(eClass);
    }
  }

  public void addNamespaceDeclarations()
  {
    if (!toDOM)
    {
      doc.addAttribute(XMI_VER_NS, XMIResource.VERSION_VALUE);
      doc.addAttribute(XMI_XMLNS, XMIResource.XMI_URI);
    }
    else
    {
      ((Element)currentNode).setAttributeNS(XMIResource.XMI_URI, XMI_VER_NS, XMIResource.VERSION_VALUE);
      ((Element)currentNode).setAttributeNS(ExtendedMetaData.XMLNS_URI, XMI_XMLNS, XMIResource.XMI_URI);
    }
    super.addNamespaceDeclarations();
  }

  public boolean isDuplicateURI(String nsURI)
  {
    return XMIResource.XMI_URI.equals(nsURI);
  }

  protected void saveFeatureMapElementReference(EObject o, EReference f)
  {
    if (extendedMetaData == null || extendedMetaData.getFeatureKind(f) != ExtendedMetaData.ELEMENT_FEATURE)
    {
      saveHref(o, f);
    }
    else
    {
      saveElementReference(o, f);
    } 
  }
}
