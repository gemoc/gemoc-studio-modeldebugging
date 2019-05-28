package org.eclipse.gemoc.executionframework.property.monitor.manager;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;

public class PatternMatchSet extends AbstractSet<IPatternMatch> implements Set<IPatternMatch> {

	private Map<IQuerySpecification<? extends ViatraQueryMatcher<? extends IPatternMatch>>, Set<IPatternMatch>> internalMap = new HashMap<>();

	private IPatternMatch getActualValue(IPatternMatch value) {
		final IPatternMatch match = (IPatternMatch) value;
		final Set<IPatternMatch> set = internalMap.computeIfAbsent(match.specification(), k -> new HashSet<>());
		final IPatternMatch actualValue = set.stream().filter(m -> m.isCompatibleWith(match)).findFirst().orElse(match);
		return actualValue;
	}

	private boolean containsValue(IPatternMatch value) {
		final IPatternMatch match = (IPatternMatch) value;
		final Set<IPatternMatch> set = internalMap.computeIfAbsent(match.specification(), k -> new HashSet<>());
		return set.stream().filter(m -> m.isCompatibleWith(match)).findFirst().isPresent();
	}

	@Override
	public int size() {
		return internalMap.values().stream().map(m -> m.size()).reduce((a, b) -> a + b).orElse(0);
	}

	@Override
	public boolean isEmpty() {
		return internalMap.values().stream().allMatch(m -> m.isEmpty());
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof IPatternMatch) {
			return containsValue((IPatternMatch) o);
		}
		return false;
	}

	@Override
	public Iterator<IPatternMatch> iterator() {
		return internalMap.values().stream().flatMap(s -> s.stream()).iterator();
	}

	@Override
	public Object[] toArray() {
		return internalMap.values().stream().flatMap(s -> s.stream()).collect(Collectors.toList()).toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return internalMap.values().stream().flatMap(s -> s.stream()).collect(Collectors.toList()).toArray(a);
	}

	@Override
	public boolean add(IPatternMatch e) {
		if (containsValue(e)) {
			return false;
		}
		return internalMap.computeIfAbsent(e.specification(), k -> new HashSet<>()).add(e);
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof IPatternMatch) {
			final IPatternMatch actualValue = getActualValue((IPatternMatch) o);
			final Set<IPatternMatch> set = internalMap.computeIfAbsent(actualValue.specification(),
					k -> new HashSet<>());
			return set.remove(actualValue);
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return c.stream().map(m -> remove(m)).reduce((b1,  b2) -> b1 || b2).orElse(false);
	}

	@Override
	public void clear() {
		internalMap.values().forEach(s -> s.clear());
		internalMap.clear();
	}
}
