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
/* reference link
 * https://stackoverflow.com/questions/28083430/communication-between-broadcastreceiver-and-activity-android
 * public class BroadcastObserver extends Observable {
    private void triggerObservers() {
        setChanged();
        notifyObservers();
    }

    public void change() {
        triggerObservers();
    }
}
@Override
public void onReceive(Context context, Intent intent) {
    BroadcastObserver bco = new BroadcastObserver();
    bco.change();
}
public class YourActivity extends Activity implements
        Observer {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BroadcastObserver bco = new BroadcastObserver();
        bco.addObserver(this);
    }

    @Override
    public void update() {
        //TODO: call your desired function
    }
}
 */
package com.magnaconnect.receiver;

import java.util.Observable;

public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    private ObservableObject() {
    }

    public static ObservableObject getInstance() {
        return instance;
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}