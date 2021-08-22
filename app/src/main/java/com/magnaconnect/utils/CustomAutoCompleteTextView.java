/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 *
 * Copyright 2014 KC Ochibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magnaconnect.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

public class CustomAutoCompleteTextView extends AppCompatAutoCompleteTextView {

        public CustomAutoCompleteTextView(Context context) {
            super(context);
        }

        public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && isPopupShowing()) {
                InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(inputManager.hideSoftInputFromWindow(findFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS)){    
                    return true;
               }
            }

            return super.onKeyPreIme(keyCode, event);
        }

    }