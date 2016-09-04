package com.hawk.gank.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.dim.library.Tinker;
import com.hawk.gank.util.UIHelper;

import java.util.List;

/**
 * Created by heyong on 16/9/4.
 */

public class InstallDexTask extends AsyncTask<Void, Void, List<String>> {
    private Context mContext;

    public InstallDexTask(Context context) {
        mContext = context;
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        return Tinker.install();
    }

    @Override
    protected void onPostExecute(List<String> installList) {
        super.onPostExecute(installList);
        if (installList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (String string : installList) {
                sb.append("成功安装: " + string + "\n");
            }
            UIHelper.showToast(mContext, sb.toString());

/*            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);*/
        }
        else {
            UIHelper.showToast(mContext, "没有新的patch安装");
        }
    }
}
