package zph.zhjx.com.bddemo.util;

import android.util.Log;

import com.watertek.geosot.GCode1D;
import com.watertek.geosot.GCode1DList;
import com.watertek.geosot.GCode1DNode;
import com.watertek.geosot.GCode2D;
import com.watertek.geosot.GCode2DList;
import com.watertek.geosot.GCode2DNode;
import com.watertek.geosot.GeoSOT;

/**
 * Created by adminZPH on 2017/5/23.
 * 主要进行GeoSot进行经纬度转换网格码
 */

public class GetSotUtils {
    private static GCode1DList mGCode1DList;
    private static GCode1DNode mGCode1DNode;
    private static GCode1D mGCode1D;

    private static GCode2DList mGCode2DList;
    private static GCode2DNode mGCode2DNode;
    private static GCode2D mGCode2D;

    static {
        try {
            System.loadLibrary("geosot");
            String str=System.getProperty("java.library.path");
            Log.i("TAG", "加载成功...");
        } catch (UnsatisfiedLinkError e) {
            Log.i("TAG", "加载失败..."+e.getMessage());
        }
    }
    /**
     * 根据经纬度得到一维的网格码
     * 默认的层级是15层级 即：2公里
     * */
    public static String getGeoSotGCode1D(double lat,double lng){
        String msg="";
        mGCode1D=GeoSOT.getGCode1DOfPoint(lng,lat,15);
        Log.i("TAG","当前矩阵的一维编码是:"+mGCode1D.getCode());
        return mGCode1D.getCode().toString();
    }






}
