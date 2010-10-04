/*
* generated by Xtext
*/
package org.eclipse.xtext.common.types.xtext.ui.ui.contentassist;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.access.ITypeProvider;
import org.eclipse.xtext.common.types.xtext.ui.ITypesProposalProvider;
import org.eclipse.xtext.common.types.xtext.ui.contentAssistTestLanguage.ContentAssistTestLanguagePackage;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.inject.Inject;
/**
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on how to customize content assistant
 */
public class ContentAssistTestLanguageProposalProvider extends AbstractContentAssistTestLanguageProposalProvider {

	@Inject
	private ITypesProposalProvider typesProposalProvider;
	
	@Inject
	private ITypeProvider.Factory typeProviderFactory;
	
	@Override
	public void completeReferenceHolder_CustomizedReference(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		typesProposalProvider.createTypeProposals(this, context, ContentAssistTestLanguagePackage.Literals.REFERENCE_HOLDER__CUSTOMIZED_REFERENCE, acceptor);
	}
	
	@Override
	public void completeReferenceHolder_SubtypeReference(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		ResourceSet resourceSet = model.eResource().getResourceSet();
		ITypeProvider typeProvider = typeProviderFactory.findTypeProvider(resourceSet);
		if (typeProvider == null)
			typeProvider = typeProviderFactory.createTypeProvider(resourceSet);
		JvmType superType = typeProvider.findTypeByName(Collection.class.getName());
		typesProposalProvider.createSubTypeProposals(superType, this, context, ContentAssistTestLanguagePackage.Literals.REFERENCE_HOLDER__SUBTYPE_REFERENCE, acceptor);
	}
	
}
