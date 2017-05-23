package zph.zhjx.com.bddemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import zph.zhjx.com.bddemo.fragment.ComparFragment;
import zph.zhjx.com.bddemo.fragment.GeoSotFragment;
import zph.zhjx.com.bddemo.fragment.TraditionFragment;

public class MyPagerAdapter extends FragmentPagerAdapter{
	List<Fragment> list;
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		list=new ArrayList<Fragment>();
		list.add(new TraditionFragment());
		list.add(new GeoSotFragment());
		list.add(new ComparFragment());

	}
	public MyPagerAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		this.list=list;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

}
