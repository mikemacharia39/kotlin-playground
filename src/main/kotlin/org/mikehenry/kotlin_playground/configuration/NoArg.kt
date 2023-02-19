package org.mikehenry.kotlin_playground.configuration

/**
 * Annotation to be used on classes that should be generated with a no-arg constructor.
 * This is set via gradle plugin.
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NoArg
