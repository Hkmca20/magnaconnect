/*
 * Created by Hariom.Gupta on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the Activity to be memorised in the
 * correct component.
 */
package com.magnaconnect.di;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
