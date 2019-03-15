/*
 * generated by Xtext 2.14.0
 */
package org.eclipse.gemoc.xdsmlframework.behavioralinterface.ide.contentassist.antlr;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.ide.contentassist.antlr.internal.InternalBehavioralInterfaceParser;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.services.BehavioralInterfaceGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class BehavioralInterfaceParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(BehavioralInterfaceGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, BehavioralInterfaceGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getJvmTypeReferenceAccess().getAlternatives(), "rule__JvmTypeReference__Alternatives");
			builder.put(grammarAccess.getJvmArgumentTypeReferenceAccess().getAlternatives(), "rule__JvmArgumentTypeReference__Alternatives");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getAlternatives_2(), "rule__JvmWildcardTypeReference__Alternatives_2");
			builder.put(grammarAccess.getXImportDeclarationAccess().getAlternatives_1(), "rule__XImportDeclaration__Alternatives_1");
			builder.put(grammarAccess.getXImportDeclarationAccess().getAlternatives_1_0_3(), "rule__XImportDeclaration__Alternatives_1_0_3");
			builder.put(grammarAccess.getEventTypeAccess().getAlternatives(), "rule__EventType__Alternatives");
			builder.put(grammarAccess.getBehavioralInterfaceAccess().getGroup(), "rule__BehavioralInterface__Group__0");
			builder.put(grammarAccess.getEventAccess().getGroup(), "rule__Event__Group__0");
			builder.put(grammarAccess.getEventAccess().getGroup_4(), "rule__Event__Group_4__0");
			builder.put(grammarAccess.getEventAccess().getGroup_4_4(), "rule__Event__Group_4_4__0");
			builder.put(grammarAccess.getEventParameterAccess().getGroup(), "rule__EventParameter__Group__0");
			builder.put(grammarAccess.getJvmTypeReferenceAccess().getGroup_0(), "rule__JvmTypeReference__Group_0__0");
			builder.put(grammarAccess.getJvmTypeReferenceAccess().getGroup_0_1(), "rule__JvmTypeReference__Group_0_1__0");
			builder.put(grammarAccess.getJvmTypeReferenceAccess().getGroup_0_1_0(), "rule__JvmTypeReference__Group_0_1_0__0");
			builder.put(grammarAccess.getArrayBracketsAccess().getGroup(), "rule__ArrayBrackets__Group__0");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getGroup(), "rule__XFunctionTypeRef__Group__0");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getGroup_0(), "rule__XFunctionTypeRef__Group_0__0");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getGroup_0_1(), "rule__XFunctionTypeRef__Group_0_1__0");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getGroup_0_1_1(), "rule__XFunctionTypeRef__Group_0_1_1__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup(), "rule__JvmParameterizedTypeReference__Group__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1(), "rule__JvmParameterizedTypeReference__Group_1__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1_2(), "rule__JvmParameterizedTypeReference__Group_1_2__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1_4(), "rule__JvmParameterizedTypeReference__Group_1_4__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1_4_0(), "rule__JvmParameterizedTypeReference__Group_1_4_0__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1_4_0_0(), "rule__JvmParameterizedTypeReference__Group_1_4_0_0__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1_4_2(), "rule__JvmParameterizedTypeReference__Group_1_4_2__0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getGroup_1_4_2_2(), "rule__JvmParameterizedTypeReference__Group_1_4_2_2__0");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getGroup(), "rule__JvmWildcardTypeReference__Group__0");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getGroup_2_0(), "rule__JvmWildcardTypeReference__Group_2_0__0");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getGroup_2_1(), "rule__JvmWildcardTypeReference__Group_2_1__0");
			builder.put(grammarAccess.getJvmUpperBoundAccess().getGroup(), "rule__JvmUpperBound__Group__0");
			builder.put(grammarAccess.getJvmUpperBoundAndedAccess().getGroup(), "rule__JvmUpperBoundAnded__Group__0");
			builder.put(grammarAccess.getJvmLowerBoundAccess().getGroup(), "rule__JvmLowerBound__Group__0");
			builder.put(grammarAccess.getJvmLowerBoundAndedAccess().getGroup(), "rule__JvmLowerBoundAnded__Group__0");
			builder.put(grammarAccess.getJvmTypeParameterAccess().getGroup(), "rule__JvmTypeParameter__Group__0");
			builder.put(grammarAccess.getJvmTypeParameterAccess().getGroup_1(), "rule__JvmTypeParameter__Group_1__0");
			builder.put(grammarAccess.getQualifiedNameAccess().getGroup(), "rule__QualifiedName__Group__0");
			builder.put(grammarAccess.getQualifiedNameAccess().getGroup_1(), "rule__QualifiedName__Group_1__0");
			builder.put(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup(), "rule__QualifiedNameWithWildcard__Group__0");
			builder.put(grammarAccess.getXImportDeclarationAccess().getGroup(), "rule__XImportDeclaration__Group__0");
			builder.put(grammarAccess.getXImportDeclarationAccess().getGroup_1_0(), "rule__XImportDeclaration__Group_1_0__0");
			builder.put(grammarAccess.getQualifiedNameInStaticImportAccess().getGroup(), "rule__QualifiedNameInStaticImport__Group__0");
			builder.put(grammarAccess.getBehavioralInterfaceAccess().getNameAssignment_1(), "rule__BehavioralInterface__NameAssignment_1");
			builder.put(grammarAccess.getBehavioralInterfaceAccess().getEventsAssignment_2(), "rule__BehavioralInterface__EventsAssignment_2");
			builder.put(grammarAccess.getEventAccess().getTypeAssignment_0(), "rule__Event__TypeAssignment_0");
			builder.put(grammarAccess.getEventAccess().getNameAssignment_2(), "rule__Event__NameAssignment_2");
			builder.put(grammarAccess.getEventAccess().getParamsAssignment_4_3(), "rule__Event__ParamsAssignment_4_3");
			builder.put(grammarAccess.getEventAccess().getParamsAssignment_4_4_1(), "rule__Event__ParamsAssignment_4_4_1");
			builder.put(grammarAccess.getEventParameterAccess().getNameAssignment_0(), "rule__EventParameter__NameAssignment_0");
			builder.put(grammarAccess.getEventParameterAccess().getTypeAssignment_2(), "rule__EventParameter__TypeAssignment_2");
			builder.put(grammarAccess.getEventParameterAccess().getManyAssignment_3(), "rule__EventParameter__ManyAssignment_3");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getParamTypesAssignment_0_1_0(), "rule__XFunctionTypeRef__ParamTypesAssignment_0_1_0");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getParamTypesAssignment_0_1_1_1(), "rule__XFunctionTypeRef__ParamTypesAssignment_0_1_1_1");
			builder.put(grammarAccess.getXFunctionTypeRefAccess().getReturnTypeAssignment_2(), "rule__XFunctionTypeRef__ReturnTypeAssignment_2");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeAssignment_0(), "rule__JvmParameterizedTypeReference__TypeAssignment_0");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsAssignment_1_1(), "rule__JvmParameterizedTypeReference__ArgumentsAssignment_1_1");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsAssignment_1_2_1(), "rule__JvmParameterizedTypeReference__ArgumentsAssignment_1_2_1");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeAssignment_1_4_1(), "rule__JvmParameterizedTypeReference__TypeAssignment_1_4_1");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsAssignment_1_4_2_1(), "rule__JvmParameterizedTypeReference__ArgumentsAssignment_1_4_2_1");
			builder.put(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsAssignment_1_4_2_2_1(), "rule__JvmParameterizedTypeReference__ArgumentsAssignment_1_4_2_2_1");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsAssignment_2_0_0(), "rule__JvmWildcardTypeReference__ConstraintsAssignment_2_0_0");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsAssignment_2_0_1(), "rule__JvmWildcardTypeReference__ConstraintsAssignment_2_0_1");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsAssignment_2_1_0(), "rule__JvmWildcardTypeReference__ConstraintsAssignment_2_1_0");
			builder.put(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsAssignment_2_1_1(), "rule__JvmWildcardTypeReference__ConstraintsAssignment_2_1_1");
			builder.put(grammarAccess.getJvmUpperBoundAccess().getTypeReferenceAssignment_1(), "rule__JvmUpperBound__TypeReferenceAssignment_1");
			builder.put(grammarAccess.getJvmUpperBoundAndedAccess().getTypeReferenceAssignment_1(), "rule__JvmUpperBoundAnded__TypeReferenceAssignment_1");
			builder.put(grammarAccess.getJvmLowerBoundAccess().getTypeReferenceAssignment_1(), "rule__JvmLowerBound__TypeReferenceAssignment_1");
			builder.put(grammarAccess.getJvmLowerBoundAndedAccess().getTypeReferenceAssignment_1(), "rule__JvmLowerBoundAnded__TypeReferenceAssignment_1");
			builder.put(grammarAccess.getJvmTypeParameterAccess().getNameAssignment_0(), "rule__JvmTypeParameter__NameAssignment_0");
			builder.put(grammarAccess.getJvmTypeParameterAccess().getConstraintsAssignment_1_0(), "rule__JvmTypeParameter__ConstraintsAssignment_1_0");
			builder.put(grammarAccess.getJvmTypeParameterAccess().getConstraintsAssignment_1_1(), "rule__JvmTypeParameter__ConstraintsAssignment_1_1");
			builder.put(grammarAccess.getXImportSectionAccess().getImportDeclarationsAssignment(), "rule__XImportSection__ImportDeclarationsAssignment");
			builder.put(grammarAccess.getXImportDeclarationAccess().getStaticAssignment_1_0_0(), "rule__XImportDeclaration__StaticAssignment_1_0_0");
			builder.put(grammarAccess.getXImportDeclarationAccess().getExtensionAssignment_1_0_1(), "rule__XImportDeclaration__ExtensionAssignment_1_0_1");
			builder.put(grammarAccess.getXImportDeclarationAccess().getImportedTypeAssignment_1_0_2(), "rule__XImportDeclaration__ImportedTypeAssignment_1_0_2");
			builder.put(grammarAccess.getXImportDeclarationAccess().getWildcardAssignment_1_0_3_0(), "rule__XImportDeclaration__WildcardAssignment_1_0_3_0");
			builder.put(grammarAccess.getXImportDeclarationAccess().getMemberNameAssignment_1_0_3_1(), "rule__XImportDeclaration__MemberNameAssignment_1_0_3_1");
			builder.put(grammarAccess.getXImportDeclarationAccess().getImportedTypeAssignment_1_1(), "rule__XImportDeclaration__ImportedTypeAssignment_1_1");
			builder.put(grammarAccess.getXImportDeclarationAccess().getImportedNamespaceAssignment_1_2(), "rule__XImportDeclaration__ImportedNamespaceAssignment_1_2");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private BehavioralInterfaceGrammarAccess grammarAccess;

	@Override
	protected InternalBehavioralInterfaceParser createParser() {
		InternalBehavioralInterfaceParser result = new InternalBehavioralInterfaceParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public BehavioralInterfaceGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(BehavioralInterfaceGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}