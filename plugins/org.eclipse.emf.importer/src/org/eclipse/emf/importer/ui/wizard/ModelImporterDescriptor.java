/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
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
 * $Id: ModelImporterDescriptor.java,v 1.2 2005/05/12 14:08:43 marcelop Exp $
 */
package org.eclipse.emf.importer.ui.wizard;

import java.util.List;

import org.eclipse.swt.graphics.Image;



/**
 * It is highly recommended not to implement this interface.  If you need to create
 * instances of a <tt>ModelImporterDescriptor</tt>, use 
 * {@link org.eclipse.emf.importer.ui.wizard.ModelImporterUtil#ModelImporterDescriptorImpl}.
 * 
 * @since 2.1.0
 */
public interface ModelImporterDescriptor
{
  int TYPE_PROJECT = 1;
  int TYPE_FILE = 2;
  int TYPE_DEFAULT = TYPE_PROJECT | TYPE_FILE;

  String getId();

  String getName();

  List getExtensions();

  int getTypes();

  Image getIcon();
  
  String getDescription();

  IModelImporterWizard getWizard();
}
