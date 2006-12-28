package org.eclipse.emf.codegen.ecore.templates.model;

import java.util.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;

public class PluginXML
{
  protected static String nl;
  public static synchronized PluginXML create(String lineSeparator)
  {
    nl = lineSeparator;
    PluginXML result = new PluginXML();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<?eclipse version=\"3.0\"?>" + NL;
  protected final String TEXT_2 = NL;
  protected final String TEXT_3 = "<!--" + NL + " <copyright>" + NL + " </copyright>" + NL;
  protected final String TEXT_4 = NL + " ";
  protected final String TEXT_5 = "Id";
  protected final String TEXT_6 = NL + "-->" + NL + "" + NL + "<plugin";
  protected final String TEXT_7 = ">";
  protected final String TEXT_8 = NL + "    name = \"%pluginName\"" + NL + "    id = \"";
  protected final String TEXT_9 = "\"" + NL + "    version = \"1.0.0\"";
  protected final String TEXT_10 = NL + "    provider-name = \"%providerName\"" + NL + "    class = \"";
  protected final String TEXT_11 = "$Implementation\">";
  protected final String TEXT_12 = NL + "    provider-name = \"%providerName\">";
  protected final String TEXT_13 = NL + NL + "  <requires>";
  protected final String TEXT_14 = NL + "    <import plugin=\"";
  protected final String TEXT_15 = "\" ";
  protected final String TEXT_16 = "export=\"true\"";
  protected final String TEXT_17 = "/>";
  protected final String TEXT_18 = NL + "  </requires>" + NL + "" + NL + "  <runtime>";
  protected final String TEXT_19 = NL + "    <library name=\"";
  protected final String TEXT_20 = ".jar\">";
  protected final String TEXT_21 = NL + "    <library name=\".\">";
  protected final String TEXT_22 = NL + "      <export name=\"*\"/>" + NL + "    </library>" + NL + "  </runtime>";
  protected final String TEXT_23 = NL + NL + "  <extension point=\"org.eclipse.emf.ecore.generated_package\">" + NL + "    <package " + NL + "       uri = \"";
  protected final String TEXT_24 = "\" " + NL + "       class = \"";
  protected final String TEXT_25 = "\"";
  protected final String TEXT_26 = " />";
  protected final String TEXT_27 = NL + "       genModel = \"";
  protected final String TEXT_28 = "\" /> ";
  protected final String TEXT_29 = NL + "  </extension>";
  protected final String TEXT_30 = NL + NL + "  <extension point=\"org.eclipse.emf.ecore.extension_parser\">" + NL + "    <parser " + NL + "       type=\"";
  protected final String TEXT_31 = "\" " + NL + "       class=\"";
  protected final String TEXT_32 = "\" />" + NL + "  </extension>";
  protected final String TEXT_33 = NL + NL + "</plugin>";
  protected final String TEXT_34 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
/**
 * <copyright>
 *
 * Copyright (c) 2002-2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *   IBM - Initial API and implementation
 *
 * </copyright>
 */

    GenModel genModel = (GenModel)argument; if (false) {/* Trick to import java.util.* without warnings */Iterator.class.getName();}
    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    stringBuffer.append("$");
    stringBuffer.append(TEXT_5);
    stringBuffer.append("$");
    stringBuffer.append(TEXT_6);
    if (genModel.isBundleManifest()) {
    stringBuffer.append(TEXT_7);
    } else {
    stringBuffer.append(TEXT_8);
    stringBuffer.append(genModel.getModelPluginID());
    stringBuffer.append(TEXT_9);
    if (genModel.hasModelPluginClass()) { 
    stringBuffer.append(TEXT_10);
    stringBuffer.append(genModel.getQualifiedModelPluginClassName());
    stringBuffer.append(TEXT_11);
    } else {
    stringBuffer.append(TEXT_12);
    }
    stringBuffer.append(TEXT_13);
    for (String pluginID : genModel.getModelRequiredPlugins()) {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(pluginID);
    stringBuffer.append(TEXT_15);
    if (!pluginID.startsWith("org.eclipse.core.runtime")) {
    stringBuffer.append(TEXT_16);
    }
    stringBuffer.append(TEXT_17);
    }
    stringBuffer.append(TEXT_18);
    if (genModel.isRuntimeJar()) {
    stringBuffer.append(TEXT_19);
    stringBuffer.append(genModel.getModelPluginID());
    stringBuffer.append(TEXT_20);
    } else {
    stringBuffer.append(TEXT_21);
    }
    stringBuffer.append(TEXT_22);
    }
    for (GenPackage genPackage : genModel.getAllGenPackagesWithClassifiers()) {
    stringBuffer.append(TEXT_23);
    stringBuffer.append(genPackage.getNSURI());
    stringBuffer.append(TEXT_24);
    stringBuffer.append(genPackage.getQualifiedPackageInterfaceName());
    stringBuffer.append(TEXT_25);
    if (!genModel.hasLocalGenModel()) {
    stringBuffer.append(TEXT_26);
    } else {
    stringBuffer.append(TEXT_27);
    stringBuffer.append(genModel.getRelativeGenModelLocation());
    stringBuffer.append(TEXT_28);
    }
    stringBuffer.append(TEXT_29);
    if (genPackage.getResource() != GenResourceKind.NONE_LITERAL) {
    stringBuffer.append(TEXT_30);
    stringBuffer.append(genPackage.getPrefix().toLowerCase());
    stringBuffer.append(TEXT_31);
    stringBuffer.append(genPackage.getQualifiedResourceFactoryClassName());
    stringBuffer.append(TEXT_32);
    }
    }
    stringBuffer.append(TEXT_33);
    stringBuffer.append(TEXT_34);
    return stringBuffer.toString();
  }
}
