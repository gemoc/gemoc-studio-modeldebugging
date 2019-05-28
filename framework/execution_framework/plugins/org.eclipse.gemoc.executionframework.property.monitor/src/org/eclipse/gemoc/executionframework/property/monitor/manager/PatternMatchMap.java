package org.eclipse.gemoc.executionframework.property.monitor.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.viatra.query.runtime.api.IPatternMatch;
import org.eclipse.viatra.query.runtime.api.IQuerySpecification;
import org.eclipse.viatra.query.runtime.api.ViatraQueryMatcher;

public class PatternMatchMap<V> implements Map<IPatternMatch, V> {

	private Map<IQuerySpecification<? extends ViatraQueryMatcher<? extends IPatternMatch>>, Map<IPatternMatch, V>> internalMap = new HashMap<>();

	private IPatternMatch getActualKey(IPatternMatch key) {
		final IPatternMatch match = (IPatternMatch) key;
		final Map<IPatternMatch, V> map = internalMap.computeIfAbsent(match.specification(), k -> new HashMap<>());
		final IPatternMatch actualKey = map.keySet().stream().filter(m -> m.isCompatibleWith(match)).findFirst().orElse(match);
		return actualKey;
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
	public boolean containsKey(Object key) {
		if (key instanceof IPatternMatch) {
			final IPatternMatch match = (IPatternMatch) key;
			final IPatternMatch actualKey = getActualKey((IPatternMatch) key);
			final Map<IPatternMatch, V> map = internalMap.get(match.specification());
			return map.containsKey(actualKey);
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return internalMap.values().stream().anyMatch(m -> m.containsValue(value));
	}

	@Override
	public V get(Object key) {
		if (key instanceof IPatternMatch) {
			final IPatternMatch match = (IPatternMatch) key;
			final IPatternMatch actualKey = getActualKey((IPatternMatch) key);
			final Map<IPatternMatch, V> map = internalMap.get(match.specification());
			return map.get(actualKey);
		}
		return null;
	}

	@Override
	public V put(IPatternMatch key, V value) {
		final IPatternMatch actualKey = getActualKey(key);
		final Map<IPatternMatch, V> map = internalMap.get(key.specification());
		return map.put(actualKey, value);
	}

	@Override
	public V remove(Object key) {
		if (key instanceof IPatternMatch) {
			final IPatternMatch match = (IPatternMatch) key;
			final IPatternMatch actualKey = getActualKey((IPatternMatch) key);
			final Map<IPatternMatch, V> map = internalMap.get(match.specification());
			return map.remove(actualKey);
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends IPatternMatch, ? extends V> m) {
		m.entrySet().forEach(e -> put(e.getKey(), e.getValue()));
	}

	@Override
	public void clear() {
		internalMap.values().forEach(m -> m.clear());
		internalMap.clear();
	}

	@Override
	public Set<IPatternMatch> keySet() {
		return internalMap.values().stream().flatMap(m -> m.keySet().stream()).collect(Collectors.toSet());
	}

	@Override
	public Collection<V> values() {
		return internalMap.values().stream().flatMap(m -> m.values().stream()).collect(Collectors.toList());
	}

	@Override
	public Set<Entry<IPatternMatch, V>> entrySet() {
		return internalMap.values().stream().flatMap(m -> m.entrySet().stream()).collect(Collectors.toSet());
	}

}
