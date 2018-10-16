package io.alexanderschaefer.u2764.model.giftmanager;

import android.content.Context;

public class GiftManagerFactory {

    public static GiftManager createInstance(Context context) {
        return new GiftManagerMockImpl(context);
    }
}
