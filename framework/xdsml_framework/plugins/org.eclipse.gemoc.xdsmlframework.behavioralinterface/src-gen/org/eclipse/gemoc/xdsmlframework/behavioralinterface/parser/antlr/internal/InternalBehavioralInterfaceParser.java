package org.eclipse.gemoc.xdsmlframework.behavioralinterface.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.gemoc.xdsmlframework.behavioralinterface.services.BehavioralInterfaceGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalBehavioralInterfaceParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ID", "RULE_INT", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'dsl'", "'input'", "'rule'", "'='", "'::'", "'params'", "','", "'interruptible'", "'true'", "'false'", "'precondition'", "'output'", "':'", "'*'", "'.'"
    };
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int RULE_ID=4;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int RULE_INT=5;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalBehavioralInterfaceParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalBehavioralInterfaceParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalBehavioralInterfaceParser.tokenNames; }
    public String getGrammarFileName() { return "InternalBehavioralInterface.g"; }



     	private BehavioralInterfaceGrammarAccess grammarAccess;

        public InternalBehavioralInterfaceParser(TokenStream input, BehavioralInterfaceGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "BehavioralInterface";
       	}

       	@Override
       	protected BehavioralInterfaceGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleBehavioralInterface"
    // InternalBehavioralInterface.g:64:1: entryRuleBehavioralInterface returns [EObject current=null] : iv_ruleBehavioralInterface= ruleBehavioralInterface EOF ;
    public final EObject entryRuleBehavioralInterface() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleBehavioralInterface = null;


        try {
            // InternalBehavioralInterface.g:64:60: (iv_ruleBehavioralInterface= ruleBehavioralInterface EOF )
            // InternalBehavioralInterface.g:65:2: iv_ruleBehavioralInterface= ruleBehavioralInterface EOF
            {
             newCompositeNode(grammarAccess.getBehavioralInterfaceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBehavioralInterface=ruleBehavioralInterface();

            state._fsp--;

             current =iv_ruleBehavioralInterface; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBehavioralInterface"


    // $ANTLR start "ruleBehavioralInterface"
    // InternalBehavioralInterface.g:71:1: ruleBehavioralInterface returns [EObject current=null] : (otherlv_0= 'dsl' ( (lv_dslName_1_0= ruleFQN ) ) ( (lv_events_2_0= ruleEvent ) )* ) ;
    public final EObject ruleBehavioralInterface() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        AntlrDatatypeRuleToken lv_dslName_1_0 = null;

        EObject lv_events_2_0 = null;



        	enterRule();

        try {
            // InternalBehavioralInterface.g:77:2: ( (otherlv_0= 'dsl' ( (lv_dslName_1_0= ruleFQN ) ) ( (lv_events_2_0= ruleEvent ) )* ) )
            // InternalBehavioralInterface.g:78:2: (otherlv_0= 'dsl' ( (lv_dslName_1_0= ruleFQN ) ) ( (lv_events_2_0= ruleEvent ) )* )
            {
            // InternalBehavioralInterface.g:78:2: (otherlv_0= 'dsl' ( (lv_dslName_1_0= ruleFQN ) ) ( (lv_events_2_0= ruleEvent ) )* )
            // InternalBehavioralInterface.g:79:3: otherlv_0= 'dsl' ( (lv_dslName_1_0= ruleFQN ) ) ( (lv_events_2_0= ruleEvent ) )*
            {
            otherlv_0=(Token)match(input,11,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getBehavioralInterfaceAccess().getDslKeyword_0());
            		
            // InternalBehavioralInterface.g:83:3: ( (lv_dslName_1_0= ruleFQN ) )
            // InternalBehavioralInterface.g:84:4: (lv_dslName_1_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:84:4: (lv_dslName_1_0= ruleFQN )
            // InternalBehavioralInterface.g:85:5: lv_dslName_1_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getBehavioralInterfaceAccess().getDslNameFQNParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_4);
            lv_dslName_1_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getBehavioralInterfaceRule());
            					}
            					set(
            						current,
            						"dslName",
            						lv_dslName_1_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalBehavioralInterface.g:102:3: ( (lv_events_2_0= ruleEvent ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12||LA1_0==22) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalBehavioralInterface.g:103:4: (lv_events_2_0= ruleEvent )
            	    {
            	    // InternalBehavioralInterface.g:103:4: (lv_events_2_0= ruleEvent )
            	    // InternalBehavioralInterface.g:104:5: lv_events_2_0= ruleEvent
            	    {

            	    					newCompositeNode(grammarAccess.getBehavioralInterfaceAccess().getEventsEventParserRuleCall_2_0());
            	    				
            	    pushFollow(FOLLOW_4);
            	    lv_events_2_0=ruleEvent();

            	    state._fsp--;


            	    					if (current==null) {
            	    						current = createModelElementForParent(grammarAccess.getBehavioralInterfaceRule());
            	    					}
            	    					add(
            	    						current,
            	    						"events",
            	    						lv_events_2_0,
            	    						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.Event");
            	    					afterParserOrEnumRuleCall();
            	    				

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBehavioralInterface"


    // $ANTLR start "entryRuleEvent"
    // InternalBehavioralInterface.g:125:1: entryRuleEvent returns [EObject current=null] : iv_ruleEvent= ruleEvent EOF ;
    public final EObject entryRuleEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvent = null;


        try {
            // InternalBehavioralInterface.g:125:46: (iv_ruleEvent= ruleEvent EOF )
            // InternalBehavioralInterface.g:126:2: iv_ruleEvent= ruleEvent EOF
            {
             newCompositeNode(grammarAccess.getEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEvent=ruleEvent();

            state._fsp--;

             current =iv_ruleEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEvent"


    // $ANTLR start "ruleEvent"
    // InternalBehavioralInterface.g:132:1: ruleEvent returns [EObject current=null] : (this_InputEvent_0= ruleInputEvent | this_OutputEvent_1= ruleOutputEvent ) ;
    public final EObject ruleEvent() throws RecognitionException {
        EObject current = null;

        EObject this_InputEvent_0 = null;

        EObject this_OutputEvent_1 = null;



        	enterRule();

        try {
            // InternalBehavioralInterface.g:138:2: ( (this_InputEvent_0= ruleInputEvent | this_OutputEvent_1= ruleOutputEvent ) )
            // InternalBehavioralInterface.g:139:2: (this_InputEvent_0= ruleInputEvent | this_OutputEvent_1= ruleOutputEvent )
            {
            // InternalBehavioralInterface.g:139:2: (this_InputEvent_0= ruleInputEvent | this_OutputEvent_1= ruleOutputEvent )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==12) ) {
                alt2=1;
            }
            else if ( (LA2_0==22) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // InternalBehavioralInterface.g:140:3: this_InputEvent_0= ruleInputEvent
                    {

                    			newCompositeNode(grammarAccess.getEventAccess().getInputEventParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_InputEvent_0=ruleInputEvent();

                    state._fsp--;


                    			current = this_InputEvent_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalBehavioralInterface.g:149:3: this_OutputEvent_1= ruleOutputEvent
                    {

                    			newCompositeNode(grammarAccess.getEventAccess().getOutputEventParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_OutputEvent_1=ruleOutputEvent();

                    state._fsp--;


                    			current = this_OutputEvent_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEvent"


    // $ANTLR start "entryRuleInputEvent"
    // InternalBehavioralInterface.g:161:1: entryRuleInputEvent returns [EObject current=null] : iv_ruleInputEvent= ruleInputEvent EOF ;
    public final EObject entryRuleInputEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInputEvent = null;


        try {
            // InternalBehavioralInterface.g:161:51: (iv_ruleInputEvent= ruleInputEvent EOF )
            // InternalBehavioralInterface.g:162:2: iv_ruleInputEvent= ruleInputEvent EOF
            {
             newCompositeNode(grammarAccess.getInputEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInputEvent=ruleInputEvent();

            state._fsp--;

             current =iv_ruleInputEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInputEvent"


    // $ANTLR start "ruleInputEvent"
    // InternalBehavioralInterface.g:168:1: ruleInputEvent returns [EObject current=null] : (otherlv_0= 'input' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )* )? (otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) ) )? (otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) ) )? ) ;
    public final EObject ruleInputEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_rule_6_0=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token lv_interruptible_14_1=null;
        Token lv_interruptible_14_2=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token lv_precondition_19_0=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_ruleDeclaringType_4_0 = null;

        EObject lv_params_9_0 = null;

        EObject lv_params_11_0 = null;

        AntlrDatatypeRuleToken lv_preconditionDeclaringType_17_0 = null;



        	enterRule();

        try {
            // InternalBehavioralInterface.g:174:2: ( (otherlv_0= 'input' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )* )? (otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) ) )? (otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) ) )? ) )
            // InternalBehavioralInterface.g:175:2: (otherlv_0= 'input' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )* )? (otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) ) )? (otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) ) )? )
            {
            // InternalBehavioralInterface.g:175:2: (otherlv_0= 'input' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )* )? (otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) ) )? (otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) ) )? )
            // InternalBehavioralInterface.g:176:3: otherlv_0= 'input' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )* )? (otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) ) )? (otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) ) )?
            {
            otherlv_0=(Token)match(input,12,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getInputEventAccess().getInputKeyword_0());
            		
            // InternalBehavioralInterface.g:180:3: ( (lv_name_1_0= ruleFQN ) )
            // InternalBehavioralInterface.g:181:4: (lv_name_1_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:181:4: (lv_name_1_0= ruleFQN )
            // InternalBehavioralInterface.g:182:5: lv_name_1_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getInputEventAccess().getNameFQNParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_5);
            lv_name_1_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInputEventRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getInputEventAccess().getRuleKeyword_2());
            		
            otherlv_3=(Token)match(input,14,FOLLOW_3); 

            			newLeafNode(otherlv_3, grammarAccess.getInputEventAccess().getEqualsSignKeyword_3());
            		
            // InternalBehavioralInterface.g:207:3: ( (lv_ruleDeclaringType_4_0= ruleFQN ) )
            // InternalBehavioralInterface.g:208:4: (lv_ruleDeclaringType_4_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:208:4: (lv_ruleDeclaringType_4_0= ruleFQN )
            // InternalBehavioralInterface.g:209:5: lv_ruleDeclaringType_4_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getInputEventAccess().getRuleDeclaringTypeFQNParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_7);
            lv_ruleDeclaringType_4_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInputEventRule());
            					}
            					set(
            						current,
            						"ruleDeclaringType",
            						lv_ruleDeclaringType_4_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,15,FOLLOW_3); 

            			newLeafNode(otherlv_5, grammarAccess.getInputEventAccess().getColonColonKeyword_5());
            		
            // InternalBehavioralInterface.g:230:3: ( (lv_rule_6_0= RULE_ID ) )
            // InternalBehavioralInterface.g:231:4: (lv_rule_6_0= RULE_ID )
            {
            // InternalBehavioralInterface.g:231:4: (lv_rule_6_0= RULE_ID )
            // InternalBehavioralInterface.g:232:5: lv_rule_6_0= RULE_ID
            {
            lv_rule_6_0=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(lv_rule_6_0, grammarAccess.getInputEventAccess().getRuleIDTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInputEventRule());
            					}
            					setWithLastConsumed(
            						current,
            						"rule",
            						lv_rule_6_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalBehavioralInterface.g:248:3: (otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )* )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==16) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalBehavioralInterface.g:249:4: otherlv_7= 'params' otherlv_8= '=' ( (lv_params_9_0= ruleEventParameter ) ) (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )*
                    {
                    otherlv_7=(Token)match(input,16,FOLLOW_6); 

                    				newLeafNode(otherlv_7, grammarAccess.getInputEventAccess().getParamsKeyword_7_0());
                    			
                    otherlv_8=(Token)match(input,14,FOLLOW_3); 

                    				newLeafNode(otherlv_8, grammarAccess.getInputEventAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalBehavioralInterface.g:257:4: ( (lv_params_9_0= ruleEventParameter ) )
                    // InternalBehavioralInterface.g:258:5: (lv_params_9_0= ruleEventParameter )
                    {
                    // InternalBehavioralInterface.g:258:5: (lv_params_9_0= ruleEventParameter )
                    // InternalBehavioralInterface.g:259:6: lv_params_9_0= ruleEventParameter
                    {

                    						newCompositeNode(grammarAccess.getInputEventAccess().getParamsEventParameterParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_9);
                    lv_params_9_0=ruleEventParameter();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInputEventRule());
                    						}
                    						add(
                    							current,
                    							"params",
                    							lv_params_9_0,
                    							"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.EventParameter");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalBehavioralInterface.g:276:4: (otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==17) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // InternalBehavioralInterface.g:277:5: otherlv_10= ',' ( (lv_params_11_0= ruleEventParameter ) )
                    	    {
                    	    otherlv_10=(Token)match(input,17,FOLLOW_3); 

                    	    					newLeafNode(otherlv_10, grammarAccess.getInputEventAccess().getCommaKeyword_7_3_0());
                    	    				
                    	    // InternalBehavioralInterface.g:281:5: ( (lv_params_11_0= ruleEventParameter ) )
                    	    // InternalBehavioralInterface.g:282:6: (lv_params_11_0= ruleEventParameter )
                    	    {
                    	    // InternalBehavioralInterface.g:282:6: (lv_params_11_0= ruleEventParameter )
                    	    // InternalBehavioralInterface.g:283:7: lv_params_11_0= ruleEventParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getInputEventAccess().getParamsEventParameterParserRuleCall_7_3_1_0());
                    	    						
                    	    pushFollow(FOLLOW_9);
                    	    lv_params_11_0=ruleEventParameter();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getInputEventRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"params",
                    	    								lv_params_11_0,
                    	    								"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.EventParameter");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }

            // InternalBehavioralInterface.g:302:3: (otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==18) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalBehavioralInterface.g:303:4: otherlv_12= 'interruptible' otherlv_13= '=' ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) )
                    {
                    otherlv_12=(Token)match(input,18,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getInputEventAccess().getInterruptibleKeyword_8_0());
                    			
                    otherlv_13=(Token)match(input,14,FOLLOW_10); 

                    				newLeafNode(otherlv_13, grammarAccess.getInputEventAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalBehavioralInterface.g:311:4: ( ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) ) )
                    // InternalBehavioralInterface.g:312:5: ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) )
                    {
                    // InternalBehavioralInterface.g:312:5: ( (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' ) )
                    // InternalBehavioralInterface.g:313:6: (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' )
                    {
                    // InternalBehavioralInterface.g:313:6: (lv_interruptible_14_1= 'true' | lv_interruptible_14_2= 'false' )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0==19) ) {
                        alt5=1;
                    }
                    else if ( (LA5_0==20) ) {
                        alt5=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;
                    }
                    switch (alt5) {
                        case 1 :
                            // InternalBehavioralInterface.g:314:7: lv_interruptible_14_1= 'true'
                            {
                            lv_interruptible_14_1=(Token)match(input,19,FOLLOW_11); 

                            							newLeafNode(lv_interruptible_14_1, grammarAccess.getInputEventAccess().getInterruptibleTrueKeyword_8_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInputEventRule());
                            							}
                            							setWithLastConsumed(current, "interruptible", true, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalBehavioralInterface.g:325:7: lv_interruptible_14_2= 'false'
                            {
                            lv_interruptible_14_2=(Token)match(input,20,FOLLOW_11); 

                            							newLeafNode(lv_interruptible_14_2, grammarAccess.getInputEventAccess().getInterruptibleFalseKeyword_8_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInputEventRule());
                            							}
                            							setWithLastConsumed(current, "interruptible", true, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalBehavioralInterface.g:339:3: (otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==21) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalBehavioralInterface.g:340:4: otherlv_15= 'precondition' otherlv_16= '=' ( (lv_preconditionDeclaringType_17_0= ruleFQN ) ) otherlv_18= '::' ( (lv_precondition_19_0= RULE_ID ) )
                    {
                    otherlv_15=(Token)match(input,21,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getInputEventAccess().getPreconditionKeyword_9_0());
                    			
                    otherlv_16=(Token)match(input,14,FOLLOW_3); 

                    				newLeafNode(otherlv_16, grammarAccess.getInputEventAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalBehavioralInterface.g:348:4: ( (lv_preconditionDeclaringType_17_0= ruleFQN ) )
                    // InternalBehavioralInterface.g:349:5: (lv_preconditionDeclaringType_17_0= ruleFQN )
                    {
                    // InternalBehavioralInterface.g:349:5: (lv_preconditionDeclaringType_17_0= ruleFQN )
                    // InternalBehavioralInterface.g:350:6: lv_preconditionDeclaringType_17_0= ruleFQN
                    {

                    						newCompositeNode(grammarAccess.getInputEventAccess().getPreconditionDeclaringTypeFQNParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_7);
                    lv_preconditionDeclaringType_17_0=ruleFQN();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInputEventRule());
                    						}
                    						set(
                    							current,
                    							"preconditionDeclaringType",
                    							lv_preconditionDeclaringType_17_0,
                    							"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_18=(Token)match(input,15,FOLLOW_3); 

                    				newLeafNode(otherlv_18, grammarAccess.getInputEventAccess().getColonColonKeyword_9_3());
                    			
                    // InternalBehavioralInterface.g:371:4: ( (lv_precondition_19_0= RULE_ID ) )
                    // InternalBehavioralInterface.g:372:5: (lv_precondition_19_0= RULE_ID )
                    {
                    // InternalBehavioralInterface.g:372:5: (lv_precondition_19_0= RULE_ID )
                    // InternalBehavioralInterface.g:373:6: lv_precondition_19_0= RULE_ID
                    {
                    lv_precondition_19_0=(Token)match(input,RULE_ID,FOLLOW_2); 

                    						newLeafNode(lv_precondition_19_0, grammarAccess.getInputEventAccess().getPreconditionIDTerminalRuleCall_9_4_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInputEventRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"precondition",
                    							lv_precondition_19_0,
                    							"org.eclipse.xtext.common.Terminals.ID");
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInputEvent"


    // $ANTLR start "entryRuleOutputEvent"
    // InternalBehavioralInterface.g:394:1: entryRuleOutputEvent returns [EObject current=null] : iv_ruleOutputEvent= ruleOutputEvent EOF ;
    public final EObject entryRuleOutputEvent() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOutputEvent = null;


        try {
            // InternalBehavioralInterface.g:394:52: (iv_ruleOutputEvent= ruleOutputEvent EOF )
            // InternalBehavioralInterface.g:395:2: iv_ruleOutputEvent= ruleOutputEvent EOF
            {
             newCompositeNode(grammarAccess.getOutputEventRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOutputEvent=ruleOutputEvent();

            state._fsp--;

             current =iv_ruleOutputEvent; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOutputEvent"


    // $ANTLR start "ruleOutputEvent"
    // InternalBehavioralInterface.g:401:1: ruleOutputEvent returns [EObject current=null] : (otherlv_0= 'output' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )* )? ) ;
    public final EObject ruleOutputEvent() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token lv_rule_6_0=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        AntlrDatatypeRuleToken lv_name_1_0 = null;

        AntlrDatatypeRuleToken lv_ruleDeclaringType_4_0 = null;

        EObject lv_params_8_0 = null;

        EObject lv_params_10_0 = null;



        	enterRule();

        try {
            // InternalBehavioralInterface.g:407:2: ( (otherlv_0= 'output' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )* )? ) )
            // InternalBehavioralInterface.g:408:2: (otherlv_0= 'output' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )* )? )
            {
            // InternalBehavioralInterface.g:408:2: (otherlv_0= 'output' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )* )? )
            // InternalBehavioralInterface.g:409:3: otherlv_0= 'output' ( (lv_name_1_0= ruleFQN ) ) otherlv_2= 'rule' otherlv_3= '=' ( (lv_ruleDeclaringType_4_0= ruleFQN ) ) otherlv_5= '::' ( (lv_rule_6_0= RULE_ID ) ) (otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )* )?
            {
            otherlv_0=(Token)match(input,22,FOLLOW_3); 

            			newLeafNode(otherlv_0, grammarAccess.getOutputEventAccess().getOutputKeyword_0());
            		
            // InternalBehavioralInterface.g:413:3: ( (lv_name_1_0= ruleFQN ) )
            // InternalBehavioralInterface.g:414:4: (lv_name_1_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:414:4: (lv_name_1_0= ruleFQN )
            // InternalBehavioralInterface.g:415:5: lv_name_1_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getOutputEventAccess().getNameFQNParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_5);
            lv_name_1_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOutputEventRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getOutputEventAccess().getRuleKeyword_2());
            		
            otherlv_3=(Token)match(input,14,FOLLOW_3); 

            			newLeafNode(otherlv_3, grammarAccess.getOutputEventAccess().getEqualsSignKeyword_3());
            		
            // InternalBehavioralInterface.g:440:3: ( (lv_ruleDeclaringType_4_0= ruleFQN ) )
            // InternalBehavioralInterface.g:441:4: (lv_ruleDeclaringType_4_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:441:4: (lv_ruleDeclaringType_4_0= ruleFQN )
            // InternalBehavioralInterface.g:442:5: lv_ruleDeclaringType_4_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getOutputEventAccess().getRuleDeclaringTypeFQNParserRuleCall_4_0());
            				
            pushFollow(FOLLOW_7);
            lv_ruleDeclaringType_4_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOutputEventRule());
            					}
            					set(
            						current,
            						"ruleDeclaringType",
            						lv_ruleDeclaringType_4_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,15,FOLLOW_3); 

            			newLeafNode(otherlv_5, grammarAccess.getOutputEventAccess().getColonColonKeyword_5());
            		
            // InternalBehavioralInterface.g:463:3: ( (lv_rule_6_0= RULE_ID ) )
            // InternalBehavioralInterface.g:464:4: (lv_rule_6_0= RULE_ID )
            {
            // InternalBehavioralInterface.g:464:4: (lv_rule_6_0= RULE_ID )
            // InternalBehavioralInterface.g:465:5: lv_rule_6_0= RULE_ID
            {
            lv_rule_6_0=(Token)match(input,RULE_ID,FOLLOW_12); 

            					newLeafNode(lv_rule_6_0, grammarAccess.getOutputEventAccess().getRuleIDTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOutputEventRule());
            					}
            					setWithLastConsumed(
            						current,
            						"rule",
            						lv_rule_6_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            // InternalBehavioralInterface.g:481:3: (otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==16) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // InternalBehavioralInterface.g:482:4: otherlv_7= 'params' ( (lv_params_8_0= ruleEventParameter ) ) (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )*
                    {
                    otherlv_7=(Token)match(input,16,FOLLOW_3); 

                    				newLeafNode(otherlv_7, grammarAccess.getOutputEventAccess().getParamsKeyword_7_0());
                    			
                    // InternalBehavioralInterface.g:486:4: ( (lv_params_8_0= ruleEventParameter ) )
                    // InternalBehavioralInterface.g:487:5: (lv_params_8_0= ruleEventParameter )
                    {
                    // InternalBehavioralInterface.g:487:5: (lv_params_8_0= ruleEventParameter )
                    // InternalBehavioralInterface.g:488:6: lv_params_8_0= ruleEventParameter
                    {

                    						newCompositeNode(grammarAccess.getOutputEventAccess().getParamsEventParameterParserRuleCall_7_1_0());
                    					
                    pushFollow(FOLLOW_13);
                    lv_params_8_0=ruleEventParameter();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOutputEventRule());
                    						}
                    						add(
                    							current,
                    							"params",
                    							lv_params_8_0,
                    							"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.EventParameter");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalBehavioralInterface.g:505:4: (otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) ) )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==17) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // InternalBehavioralInterface.g:506:5: otherlv_9= ',' ( (lv_params_10_0= ruleEventParameter ) )
                    	    {
                    	    otherlv_9=(Token)match(input,17,FOLLOW_3); 

                    	    					newLeafNode(otherlv_9, grammarAccess.getOutputEventAccess().getCommaKeyword_7_2_0());
                    	    				
                    	    // InternalBehavioralInterface.g:510:5: ( (lv_params_10_0= ruleEventParameter ) )
                    	    // InternalBehavioralInterface.g:511:6: (lv_params_10_0= ruleEventParameter )
                    	    {
                    	    // InternalBehavioralInterface.g:511:6: (lv_params_10_0= ruleEventParameter )
                    	    // InternalBehavioralInterface.g:512:7: lv_params_10_0= ruleEventParameter
                    	    {

                    	    							newCompositeNode(grammarAccess.getOutputEventAccess().getParamsEventParameterParserRuleCall_7_2_1_0());
                    	    						
                    	    pushFollow(FOLLOW_13);
                    	    lv_params_10_0=ruleEventParameter();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getOutputEventRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"params",
                    	    								lv_params_10_0,
                    	    								"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.EventParameter");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOutputEvent"


    // $ANTLR start "entryRuleEventParameter"
    // InternalBehavioralInterface.g:535:1: entryRuleEventParameter returns [EObject current=null] : iv_ruleEventParameter= ruleEventParameter EOF ;
    public final EObject entryRuleEventParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEventParameter = null;


        try {
            // InternalBehavioralInterface.g:535:55: (iv_ruleEventParameter= ruleEventParameter EOF )
            // InternalBehavioralInterface.g:536:2: iv_ruleEventParameter= ruleEventParameter EOF
            {
             newCompositeNode(grammarAccess.getEventParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEventParameter=ruleEventParameter();

            state._fsp--;

             current =iv_ruleEventParameter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEventParameter"


    // $ANTLR start "ruleEventParameter"
    // InternalBehavioralInterface.g:542:1: ruleEventParameter returns [EObject current=null] : ( ( (lv_name_0_0= ruleFQN ) ) otherlv_1= ':' ( (lv_type_2_0= ruleFQN ) ) ( (lv_many_3_0= '*' ) )? ) ;
    public final EObject ruleEventParameter() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token lv_many_3_0=null;
        AntlrDatatypeRuleToken lv_name_0_0 = null;

        AntlrDatatypeRuleToken lv_type_2_0 = null;



        	enterRule();

        try {
            // InternalBehavioralInterface.g:548:2: ( ( ( (lv_name_0_0= ruleFQN ) ) otherlv_1= ':' ( (lv_type_2_0= ruleFQN ) ) ( (lv_many_3_0= '*' ) )? ) )
            // InternalBehavioralInterface.g:549:2: ( ( (lv_name_0_0= ruleFQN ) ) otherlv_1= ':' ( (lv_type_2_0= ruleFQN ) ) ( (lv_many_3_0= '*' ) )? )
            {
            // InternalBehavioralInterface.g:549:2: ( ( (lv_name_0_0= ruleFQN ) ) otherlv_1= ':' ( (lv_type_2_0= ruleFQN ) ) ( (lv_many_3_0= '*' ) )? )
            // InternalBehavioralInterface.g:550:3: ( (lv_name_0_0= ruleFQN ) ) otherlv_1= ':' ( (lv_type_2_0= ruleFQN ) ) ( (lv_many_3_0= '*' ) )?
            {
            // InternalBehavioralInterface.g:550:3: ( (lv_name_0_0= ruleFQN ) )
            // InternalBehavioralInterface.g:551:4: (lv_name_0_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:551:4: (lv_name_0_0= ruleFQN )
            // InternalBehavioralInterface.g:552:5: lv_name_0_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getEventParameterAccess().getNameFQNParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_14);
            lv_name_0_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getEventParameterRule());
            					}
            					set(
            						current,
            						"name",
            						lv_name_0_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_1=(Token)match(input,23,FOLLOW_3); 

            			newLeafNode(otherlv_1, grammarAccess.getEventParameterAccess().getColonKeyword_1());
            		
            // InternalBehavioralInterface.g:573:3: ( (lv_type_2_0= ruleFQN ) )
            // InternalBehavioralInterface.g:574:4: (lv_type_2_0= ruleFQN )
            {
            // InternalBehavioralInterface.g:574:4: (lv_type_2_0= ruleFQN )
            // InternalBehavioralInterface.g:575:5: lv_type_2_0= ruleFQN
            {

            					newCompositeNode(grammarAccess.getEventParameterAccess().getTypeFQNParserRuleCall_2_0());
            				
            pushFollow(FOLLOW_15);
            lv_type_2_0=ruleFQN();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getEventParameterRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_2_0,
            						"org.eclipse.gemoc.xdsmlframework.behavioralinterface.BehavioralInterface.FQN");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalBehavioralInterface.g:592:3: ( (lv_many_3_0= '*' ) )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==24) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // InternalBehavioralInterface.g:593:4: (lv_many_3_0= '*' )
                    {
                    // InternalBehavioralInterface.g:593:4: (lv_many_3_0= '*' )
                    // InternalBehavioralInterface.g:594:5: lv_many_3_0= '*'
                    {
                    lv_many_3_0=(Token)match(input,24,FOLLOW_2); 

                    					newLeafNode(lv_many_3_0, grammarAccess.getEventParameterAccess().getManyAsteriskKeyword_3_0());
                    				

                    					if (current==null) {
                    						current = createModelElement(grammarAccess.getEventParameterRule());
                    					}
                    					setWithLastConsumed(current, "many", true, "*");
                    				

                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEventParameter"


    // $ANTLR start "entryRuleFQN"
    // InternalBehavioralInterface.g:610:1: entryRuleFQN returns [String current=null] : iv_ruleFQN= ruleFQN EOF ;
    public final String entryRuleFQN() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFQN = null;


        try {
            // InternalBehavioralInterface.g:610:43: (iv_ruleFQN= ruleFQN EOF )
            // InternalBehavioralInterface.g:611:2: iv_ruleFQN= ruleFQN EOF
            {
             newCompositeNode(grammarAccess.getFQNRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFQN=ruleFQN();

            state._fsp--;

             current =iv_ruleFQN.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFQN"


    // $ANTLR start "ruleFQN"
    // InternalBehavioralInterface.g:617:1: ruleFQN returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleFQN() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalBehavioralInterface.g:623:2: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // InternalBehavioralInterface.g:624:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // InternalBehavioralInterface.g:624:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // InternalBehavioralInterface.g:625:3: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_16); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getFQNAccess().getIDTerminalRuleCall_0());
            		
            // InternalBehavioralInterface.g:632:3: (kw= '.' this_ID_2= RULE_ID )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==25) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // InternalBehavioralInterface.g:633:4: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,25,FOLLOW_3); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getFQNAccess().getFullStopKeyword_1_0());
            	    			
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_16); 

            	    				current.merge(this_ID_2);
            	    			

            	    				newLeafNode(this_ID_2, grammarAccess.getFQNAccess().getIDTerminalRuleCall_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFQN"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000401002L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000250002L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000260002L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000002000002L});

}