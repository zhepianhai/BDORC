package zph.zhjx.com.bddemo.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zph.zhjx.com.bddemo.R;
import zph.zhjx.com.bddemo.app.ActivityCollector;
import zph.zhjx.com.bddemo.util.Net;


public class BaseActivity extends FragmentActivity {
    private Toast toast;
    private final long RETRY_TIMES = 1;
    public Function<Observable, ObservableSource> composeFunction;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        ActivityCollector.addActivity(this);
        initob();
    }
    private void initob() {
        composeFunction = new Function<Observable, ObservableSource>() {
            @Override
            public ObservableSource apply(Observable observable) throws Exception {
                return observable.retry(RETRY_TIMES)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if(Net.isNetworkAvailable(BaseActivity.this)){

                                }
                                else{
                                    toast("网络连接异常，请检查网络");
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public void setstatusbackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public void setstatusbarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            tintManager.setTintColor(R.color.colorHome);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(R.color.colorHome1);
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.colorHome);
        }
    }




    public void setstatusbarcolor(String color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            tintManager.setTintColor(Color.parseColor(color));
            //给状态栏设置颜色
//            tintManager.setStatusBarTintResource(Color.parseColor(color));
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(Color.parseColor(color));
        }
    }

    //写一些打印“吐司”的方法

    public void toast(String text){

        if(TextUtils.isEmpty(text)){
            return;
        }
        toast.setText(text);
        toast.show();
    }


    public boolean CheckIsEmpty(String... strings) {
        for(String s:strings){
            if (s.isEmpty()){
                return false;
            }
        }
        return  true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
