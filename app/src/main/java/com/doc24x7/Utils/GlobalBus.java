package com.doc24x7.Utils;

import org.greenrobot.eventbus.EventBus;

public class GlobalBus {

    private static EventBus sBus;

    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }

}
