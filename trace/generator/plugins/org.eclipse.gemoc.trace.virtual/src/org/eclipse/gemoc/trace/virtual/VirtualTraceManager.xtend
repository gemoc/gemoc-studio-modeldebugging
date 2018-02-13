package org.eclipse.gemoc.trace.virtual

import java.util.ArrayList
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.gemoc.trace.commons.model.trace.Dimension
import org.eclipse.gemoc.trace.commons.model.trace.State
import org.eclipse.gemoc.trace.commons.model.trace.Step
import org.eclipse.gemoc.trace.commons.model.trace.Trace
import org.eclipse.gemoc.trace.commons.model.trace.TracedObject
import org.eclipse.gemoc.trace.commons.model.trace.Value
import org.eclipse.gemoc.trace.gemoc.api.ITraceExtractor
import org.eclipse.gemoc.trace.gemoc.traceaddon.GenericTraceExtractor
import org.eclipse.gemoc.trace.virtual.virtualtrace.StateGroup
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualTrace
import org.eclipse.gemoc.trace.virtual.virtualtrace.VirtualtraceFactory

class VirtualTraceManager {
	
	private val VirtualTrace virtualTrace
	private val factory = VirtualtraceFactory::eINSTANCE
	private val ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, ?> extractor
	private val Map<State<?, ?>, StateGroup> stateToStateGroup = new HashMap
	
	new(Trace<Step<?>, TracedObject<?>, State<?, ?>> trace) {
		this(trace, new GenericTraceExtractor(trace))
	}
	
	new(Trace<Step<?>, TracedObject<?>, State<?, ?>> trace,
		ITraceExtractor<Step<?>, State<?, ?>, TracedObject<?>, Dimension<?>, Value<?>> extractor) {
		this.extractor = extractor
		this.extractor.statesAdded(trace.states)
		virtualTrace = factory.createVirtualTrace
		virtualTrace.trace = trace
		virtualTrace.selectedDimensions.addAll(trace.tracedObjects.map[dimensions].flatten)
		virtualTrace.stateGroups.addAll(virtualTrace.trace.states.map[s|
			factory.createStateGroup => [
				states.add(s)
				stateToStateGroup.put(s, it)
			]
		])
		
	}
	
	public def void ignoreDimension(Dimension<?> dimension) {
		if (virtualTrace.selectedDimensions.contains(dimension)) {
			virtualTrace.selectedDimensions.remove(dimension)
			extractor.ignoreDimension(dimension, true)
			update
		}
	}
	
	public def void selectDimension(Dimension<?> dimension) {
		if (!virtualTrace.selectedDimensions.contains(dimension)) {
			virtualTrace.selectedDimensions.add(dimension)
			extractor.ignoreDimension(dimension, false)
			update
		}
	}
	
	public def StateGroup getStateGroupForState(State<?, ?> state) {
		return stateToStateGroup.get(state)
	}
	
	public def VirtualTrace getVirtualTrace() {
		return virtualTrace
	}
	
	public def List<Value<?>> getValuesForDimension(Dimension<?> dimension) {
		virtualTrace.stateGroups.map[g|
			g.values.findFirst[v|v.eContainer == dimension]
		].filterNull.toList
	}
	
	private def void update() {
		val List<List<State<?, ?>>> classes = extractor.computeStateEquivalenceClasses
		val Map<State<?, ?>, List<State<?, ?>>> stateToEquivalencyClass = new HashMap
		val states = virtualTrace.trace.states
		states.forEach[s|
			stateToEquivalencyClass.put(s, classes.findFirst[c|c.contains(s)])
		]
		stateToStateGroup.clear
		val List<StateGroup> stateGroups = new ArrayList
		var i = 0
		var State<?, ?> previousState = null
		var StateGroup currentStateGroup = null
		while (i < states.size) {
			val state = states.get(i)
			if (previousState !== null) {
				val c1 = stateToEquivalencyClass.get(state)
				val c2 = stateToEquivalencyClass.get(previousState)
				if (c1 != c2) {
					currentStateGroup = factory.createStateGroup
					stateGroups.add(currentStateGroup)
				}
			} else {
				currentStateGroup = factory.createStateGroup
				stateGroups.add(currentStateGroup)
			}
			currentStateGroup.states.add(state)
			stateToStateGroup.put(state, currentStateGroup)
			previousState = state
			i++
		}
		virtualTrace.stateGroups.clear
		virtualTrace.stateGroups.addAll(stateGroups)
	}
	
//	private def List<List<List<Value<?>>>> computeEquivalenceClasses(List<List<Value<?>>> states) {
//		val List<Pair<List<Value<?>>, List<Integer>>> stateToValueIndexes = new ArrayList
//		val List<Value<?>> observedValues = new ArrayList
//
//		for (List<Value<?>> state : states) {
//			val List<Integer> valueIndexes = new ArrayList
//			stateToValueIndexes.add(new Pair(state, valueIndexes))
//			for (var i = 0; i < state.size(); i++) {
//				val Value<?> value = state.get(i)
//				var idx = -1
//				for (var j = 0; idx == -1 && j < observedValues.size(); j++) {
//					val Value<?> v1 = observedValues.get(j)
//					val Value<?> v2 = value
//					if (compareEObjects(v1, v2)) {
//						idx = j
//					}
//				}
//				if (idx != -1) {
//					valueIndexes.add(idx);
//				} else {
//					valueIndexes.add(observedValues.size())
//					observedValues.add(value)
//				}
//			}
//		}
//
//		val List<List<Integer>> distinctClasses = stateToValueIndexes.map[value].stream.distinct.collect(Collectors::toList)
//		val Map<Integer, List<List<Value<?>>>> result = new HashMap
//
//		stateToValueIndexes.forEach[p|
//			val List<Value<?>> state = p.getKey()
//			val List<Integer> indexes = p.getValue()
//			var v = distinctClasses.indexOf(indexes)
//			var List<List<Value<?>>> equivalentStates = result.get(v)
//			if (equivalentStates === null) {
//				equivalentStates = new ArrayList
//				result.put(v, equivalentStates)
//			}
//			if (equivalentStates.isEmpty()) {
//				equivalentStates.add(state)
//			} else {
//				if (states.indexOf(state) < states.indexOf(equivalentStates.get(0))) {
//					equivalentStates.add(0, state)
//				} else {
//					equivalentStates.add(state)
//				}
//			}
//		]
//
//		return result.values().stream().collect(Collectors.toList())
//	}
}