package zph.zhjx.com.bddemo.imp;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import zph.zhjx.com.bddemo.bean.BaseEntity;

/**
 * Created by adminZPH on 2017/5/23.
 * RetrofotService 网络访问接口
 */

public interface RetrofitService {

    @FormUrlEncoded
    @POST("getUser")
    Observable<BaseEntity<String>> getUser(@FieldMap Map<String, String> map);
}
