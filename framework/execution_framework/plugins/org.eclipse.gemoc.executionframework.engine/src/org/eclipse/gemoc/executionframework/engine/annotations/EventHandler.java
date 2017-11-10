package org.eclipse.gemoc.executionframework.engine.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.eclipse.gemoc.executionframework.event.manager.IEventManager;

/**
 * Events derived from annotated methods can be sent to the
 * {@link IEventManager}, using the
 * {@link org.eclipse.gemoc.executionframework.event.manager.IEventManager#queueEvent(org.eclipse.gemoc.executionframework.event.model.event.Event)
 * queueEvent} method.
 * 
 * @author dorian
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EventHandler {
	/**
	 * Indicates a method that should evaluate to <code>true</code> for the event to
	 * be handled. The provided method must have the same parameter signature and
	 * return a boolean result.
	 */
	String precondition() default "";
	/**
	 * Indicates whether the event handling should be run to completion
	 * (<code>false</code>) or be allowed to be interrupted by other events (<code>true</code>).
	 * Default value: <code>false</code>.
	 */
	boolean interruptible() default false;
}
