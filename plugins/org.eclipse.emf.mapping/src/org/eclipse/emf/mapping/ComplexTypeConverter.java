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
 * $Id: ComplexTypeConverter.java,v 1.2 2004/12/16 21:55:07 emerks Exp $
 */
package org.eclipse.emf.mapping;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Complex Type Converter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.mapping.ComplexTypeConverter#getIn2out <em>In2out</em>}</li>
 *   <li>{@link org.eclipse.emf.mapping.ComplexTypeConverter#getOut2in <em>Out2in</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.mapping.MappingPackage#getComplexTypeConverter()
 * @model
 * @generated
 */
public interface ComplexTypeConverter extends TypeConverter
{
  /**
   * Returns the value of the '<em><b>In2out</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>In2out</em>' reference isn't clear, 
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>In2out</em>' reference.
   * @see #setIn2out(Mapping)
   * @see org.eclipse.emf.mapping.MappingPackage#getComplexTypeConverter_In2out()
   * @model
   * @generated
   */
  Mapping getIn2out();

  /**
   * Sets the value of the '{@link org.eclipse.emf.mapping.ComplexTypeConverter#getIn2out <em>In2out</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>In2out</em>' reference.
   * @see #getIn2out()
   * @generated
   */
  void setIn2out(Mapping value);

  /**
   * Returns the value of the '<em><b>Out2in</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Out2in</em>' reference isn't clear, 
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Out2in</em>' reference.
   * @see #setOut2in(Mapping)
   * @see org.eclipse.emf.mapping.MappingPackage#getComplexTypeConverter_Out2in()
   * @model
   * @generated
   */
  Mapping getOut2in();

  /**
   * Sets the value of the '{@link org.eclipse.emf.mapping.ComplexTypeConverter#getOut2in <em>Out2in</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Out2in</em>' reference.
   * @see #getOut2in()
   * @generated
   */
  void setOut2in(Mapping value);

} // ComplexTypeConverter
