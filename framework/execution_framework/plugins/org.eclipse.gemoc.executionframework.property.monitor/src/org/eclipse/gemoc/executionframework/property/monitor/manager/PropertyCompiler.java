package org.eclipse.gemoc.executionframework.property.monitor.manager;

import org.eclipse.gemoc.executionframework.property.model.property.Absence;
import org.eclipse.gemoc.executionframework.property.model.property.After;
import org.eclipse.gemoc.executionframework.property.model.property.AfterUntil;
import org.eclipse.gemoc.executionframework.property.model.property.Before;
import org.eclipse.gemoc.executionframework.property.model.property.Between;
import org.eclipse.gemoc.executionframework.property.model.property.Existence;
import org.eclipse.gemoc.executionframework.property.model.property.Precedence;
import org.eclipse.gemoc.executionframework.property.model.property.PropertyPackage;
import org.eclipse.gemoc.executionframework.property.model.property.Response;
import org.eclipse.gemoc.executionframework.property.model.property.TemporalProperty;
import org.eclipse.gemoc.executionframework.property.model.property.Universality;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AbstractTemporalProperty;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AlwaysPAfterQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AlwaysPAfterQUntilR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AlwaysPBeforeQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AlwaysPBetweenQAndR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.AlwaysPGlobally;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.ExistsPAfterQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.ExistsPAfterQUntilR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.ExistsPBeforeQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.ExistsPBetweenQAndR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.ExistsPGlobally;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.NeverPAfterQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.NeverPAfterQUntilR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.NeverPBeforeQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.NeverPBetweenQAndR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.NeverPGlobally;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SPrecedesPAfterQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SPrecedesPAfterQUntilR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SPrecedesPBeforeQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SPrecedesPBetweenQAndR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SPrecedesPGlobally;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SRespondsToPAfterQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SRespondsToPAfterQUntilR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SRespondsToPBeforeQ;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SRespondsToPBetweenQAndR;
import org.eclipse.gemoc.executionframework.property.monitor.esper.properties.SRespondsToPGlobally;
import org.eclipse.viatra.query.patternlanguage.emf.specification.SpecificationBuilder;

public class PropertyCompiler {

	private final SpecificationBuilder builder;
	
	public PropertyCompiler(SpecificationBuilder builder) {
		this.builder = builder;
	}
	
	public AbstractTemporalProperty compileProperty(TemporalProperty property) {
		AbstractTemporalProperty result = null;
		int propertyType = property.eClass().getClassifierID();
		int scopeType = property.getScope().eClass().getClassifierID();
		switch (propertyType) {
		case PropertyPackage.UNIVERSALITY:
			final Universality always = (Universality) property;
			switch (scopeType) {
			case PropertyPackage.GLOBALLY:
				result = new AlwaysPGlobally(builder, "Always_P_Globally", always);
				result.compileEPL();
				break;
			case PropertyPackage.BEFORE:
				final Before before = (Before) property.getScope();
				result = new AlwaysPBeforeQ(builder, "Always_P_Before_Q", always, before);
				result.compileEPL();
				break;
			case PropertyPackage.BETWEEN:
				final Between between = (Between) property.getScope();
				result = new AlwaysPBetweenQAndR(builder, "Always_P_Between_Q_And_R", always, between);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER_UNTIL:
				final AfterUntil afterUntil = (AfterUntil) property.getScope();
				result = new AlwaysPAfterQUntilR(builder, "Always_P_After_Q_Until_R", always, afterUntil);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER:
				final After after = (After) property.getScope();
				result = new AlwaysPAfterQ(builder, "Always_P_After_Q", always, after);
				result.compileEPL();
				break;
			}
			break;
		case PropertyPackage.EXISTENCE:
			final Existence exists = (Existence) property;
			switch (scopeType) {
			case PropertyPackage.GLOBALLY:
				result = new ExistsPGlobally(builder, "Exists_P_Globally", exists);
				result.compileEPL();
				break;
			case PropertyPackage.BEFORE:
				final Before before = (Before) property.getScope();
				result = new ExistsPBeforeQ(builder, "Exists_P_Before_Q", exists, before);
				result.compileEPL();
				break;
			case PropertyPackage.BETWEEN:
				final Between between = (Between) property.getScope();
				result = new ExistsPBetweenQAndR(builder, "Exists_P_Between_Q_And_R", exists, between);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER_UNTIL:
				final AfterUntil afterUntil = (AfterUntil) property.getScope();
				result = new ExistsPAfterQUntilR(builder, "Exists_P_After_Q_Until_R", exists, afterUntil);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER:
				final After after = (After) property.getScope();
				result = new ExistsPAfterQ(builder, "Exists_P_After_Q", exists, after);
				result.compileEPL();
				break;
			}
			break;
		case PropertyPackage.ABSENCE:
			final Absence never = (Absence) property;
			switch (scopeType) {
			case PropertyPackage.GLOBALLY:
				result = new NeverPGlobally(builder, "Never_P_Globally", never);
				result.compileEPL();
				break;
			case PropertyPackage.BEFORE:
				final Before before = (Before) property.getScope();
				result = new NeverPBeforeQ(builder, "Never_P_Before_Q", never, before);
				result.compileEPL();
				break;
			case PropertyPackage.BETWEEN:
				final Between between = (Between) property.getScope();
				result = new NeverPBetweenQAndR(builder, "Never_P_Between_Q_And_R", never, between);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER_UNTIL:
				final AfterUntil afterUntil = (AfterUntil) property.getScope();
				result = new NeverPAfterQUntilR(builder, "Never_P_After_Q_Until_R", never, afterUntil);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER:
				final After after = (After) property.getScope();
				result = new NeverPAfterQ(builder, "Never_P_After_Q", never, after);
				result.compileEPL();
				break;
			}
			break;
		case PropertyPackage.RESPONSE:
			final Response response = (Response) property;
			switch (scopeType) {
			case PropertyPackage.GLOBALLY:
				result = new SRespondsToPGlobally(builder, "S_RespondsTo_P_Globally", response);
				result.compileEPL();
				break;
			case PropertyPackage.BEFORE:
				final Before before = (Before) property.getScope();
				result = new SRespondsToPBeforeQ(builder, "S_RespondsTo_P_Before_Q", response, before);
				result.compileEPL();
				break;
			case PropertyPackage.BETWEEN:
				final Between between = (Between) property.getScope();
				result = new SRespondsToPBetweenQAndR(builder, "S_RespondsTo_P_Between_Q_And_R", response, between);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER_UNTIL:
				final AfterUntil afterUntil = (AfterUntil) property.getScope();
				result = new SRespondsToPAfterQUntilR(builder, "S_RespondsTo_P_After_Q_Until_R", response, afterUntil);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER:
				final After after = (After) property.getScope();
				result = new SRespondsToPAfterQ(builder, "S_RespondsTo_P_After_Q", response, after);
				result.compileEPL();
				break;
			}
			break;
		case PropertyPackage.PRECEDENCE:
			final Precedence precedence = (Precedence) property;
			switch (scopeType) {
			case PropertyPackage.GLOBALLY:
				result = new SPrecedesPGlobally(builder, "S_Precedes_P_Globally", precedence);
				result.compileEPL();
				break;
			case PropertyPackage.BEFORE:
				final Before before = (Before) property.getScope();
				result = new SPrecedesPBeforeQ(builder, "S_Precedes_P_Before_Q", precedence, before);
				result.compileEPL();
				break;
			case PropertyPackage.BETWEEN:
				final Between between = (Between) property.getScope();
				result = new SPrecedesPBetweenQAndR(builder, "S_Precedes_P_Between_Q_And_R", precedence, between);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER_UNTIL:
				final AfterUntil afterUntil = (AfterUntil) property.getScope();
				result = new SPrecedesPAfterQUntilR(builder, "S_Precedes_P_After_Q_Until_R", precedence, afterUntil);
				result.compileEPL();
				break;
			case PropertyPackage.AFTER:
				final After after = (After) property.getScope();
				result = new SPrecedesPAfterQ(builder, "S_Precedes_P_After_Q", precedence, after);
				result.compileEPL();
				break;
			}
			break;
		}
		return result;
	}
}
