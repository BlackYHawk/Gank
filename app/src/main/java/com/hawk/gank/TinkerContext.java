package com.hawk.gank;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by heyong on 2016/12/22.
 */

public class TinkerContext extends TinkerApplication {

    public TinkerContext() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.hawk.gank.AppContext",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
