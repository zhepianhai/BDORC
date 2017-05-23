package zph.zhjx.com.bddemo.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import zph.zhjx.com.bddemo.R;
import zph.zhjx.com.bddemo.adapter.MyPagerAdapter;
import zph.zhjx.com.bddemo.base.BaseActivity;

public class MenuActivity extends BaseActivity implements View.OnClickListener {
    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initview();
        setstatusbarcolor();
        setLisiner();
    }

    private void initview() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    private void setLisiner() {

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
        mRadioGroup.check(R.id.fragment01);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int pos = group.indexOfChild(group.findViewById(checkedId));
                //加false是为了瞬间切换过去
                mViewPager.setCurrentItem(pos, false);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exit();
    }
    private boolean isExit=false;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    private void exit() {
        if (!isExit) {
            isExit = true;
            toast(getString(R.string.exit_message));
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View view) {

    }
}