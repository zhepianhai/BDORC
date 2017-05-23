package zph.zhjx.com.bddemo.app;
import android.app.Application;

public class App extends Application {
    private static App myApplaction;


    @Override
    public void onCreate() {
        super.onCreate();
        myApplaction=this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

    }
   public static App getIntance(){
        return myApplaction;
   }
}
