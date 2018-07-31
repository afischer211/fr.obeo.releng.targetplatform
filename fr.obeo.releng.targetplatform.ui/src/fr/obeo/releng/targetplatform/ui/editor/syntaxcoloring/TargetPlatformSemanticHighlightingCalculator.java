/*******************************************************************************
 * Copyright (c) 2012-2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package fr.obeo.releng.targetplatform.ui.editor.syntaxcoloring;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;

import fr.obeo.releng.targetplatform.Environment;
import fr.obeo.releng.targetplatform.IU;
import fr.obeo.releng.targetplatform.TargetPlatformPackage;

public class TargetPlatformSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	@Override
	protected boolean highlightElement(EObject object, IHighlightedPositionAcceptor acceptor) {
		if (object instanceof IU) {
			highlightFeature(acceptor, object, TargetPlatformPackage.Literals.IU__VERSION, TargetPlatformHighlightingConfiguration.VERSION_RANGE_ID);
		} else if (object instanceof Environment) {
			highlightFeature(acceptor, object, TargetPlatformPackage.Literals.ENVIRONMENT__ENV, TargetPlatformHighlightingConfiguration.OPTION_ID);
		}
		return false;
	}
	
	protected void highlightFeature(IHighlightedPositionAcceptor acceptor, EObject object, EStructuralFeature feature,
			String... styleIds) {
		List<INode> children = NodeModelUtils.findNodesForFeature(object, feature);
		for(INode child: children)
			highlightNode(acceptor, child, styleIds);
	}
}
