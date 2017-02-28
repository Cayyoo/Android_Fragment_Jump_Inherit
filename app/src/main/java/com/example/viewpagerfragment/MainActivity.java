package com.example.viewpagerfragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 *（一）、从当前Fragment跳转到另一个Fragment
 * 1、在相应Fragment全局中声明Fragment管理对象及事务对象。
 *       private FragmentManager manager;
 *       private FragmentTransaction ft;
 * 2、在OnCreate方法中初始化FragmentManager
 *      @Override
 *      public void onCreate(@Nullable Bundle savedInstanceState) {
 *           super.onCreate(savedInstanceState);
 *
 *           //初始化FragmentManager
 *           fragmentManager=this.getFragmentManager();
 *      }
 * 3、从FragmentManager获得一个FragmentTransaction实例
 *      ft = manager.beginTransaction();
 * 4、实现Fragment跳转或切换
 *      ft.replace(R.id.currentFragmentId, yourJumpFragmentObject);
 *      ft.addToBackStack(null);
 *      ft.commit();
 * 5、如何在Fragment切换时传递参数?
 *   使用setArguments()方法绑定一个bundle对象传递到另外一个fragment中。
 *      currentFragment.setArguments(bundle);
 *   在另外一个fragment中使用getArguments()可以拿到bundle对象。
 *      yourJumpToFragment.getArguments().getString("参数");
 *
 *  注意：在ViewPager+Fragment架构中，Fragment都在MainActivity中，点击Fragment中的组件实现切换Tab页时，
 *        Fragment传回的参数若大于Tab数量（如，Tab页有4个，默认设置id为0、1、2、3。当传回的值>3时,比如4等），
 *        均跳转到最后一个Tab页，即id为3的Tab页面。
 *
 *
 *
 *（二）、从当前的Fragment跳转到另一个Activity中
 *    Intent intent=new Intent(getActivity(), YourJumpToActivity.class);
 *    startActivity(intent);
 *  注意：如果要回到跳转前的fragment，直接finish()当前Activity即可。
 *
 *
 *（三）、从当前Activity跳转到另一个Activity中的Fragment
 *  1、在当前Activity中使用意图Intent跳转到目标Activity中，比如MainActivity。这里通过意图塞入了一个标识符（更严谨的方式是通过请求码和结果码实现）。
 *     Intent intent = new Intent(CurrentActivity.this , MainActivity.class);
 *     intent.putExtra("paramName", intParam);//此处传递的参数类型根据需求确定
 *     startActivity(intent);
 *  2、在MainActivity的onResume()方法中得到这个标识符，并且切换到相应的Tab即可。
 *     Int intParam = getIntent().getIntExtra("paramName",defaultValue_Int);
 *     if (id == intParam ) {
 *        mTabHost.setCurrentTab(id); //id代表要返回的Fragment所在Tab页的位置
 *     }
 */
public class MainActivity extends FragmentActivity {

	private RadioGroup radioGroup; 				//菜单组
	private ViewPager viewPager; 				//滑动界面

	private int defaultTab = 0; 				//默认显示的标签页
	
	private MainFragmentAdapter adapter;

	private Fragment1 fragment1;				//子界面,每个Fragment相当于一个Activity,
	private Fragment2 fragment2;				//如果需要从Fragment1传递值到Fragment2中,需要通过此类来传递
	private Fragment3 fragment3;
	private Fragment4 fragment4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();

		((RadioButton) radioGroup.getChildAt(defaultTab)).setChecked(true); // 初始化菜单选中

		initTab(getIntent());

		//根据Fragment传回的参数，切换Tab页面
		Intent intent=getIntent();
		int id=intent.getIntExtra("currentId",0);//0为设置的默认值
		viewPager.setCurrentItem(id);//当Fragment传回的参数id>3,比如设置为4，都等效于viewPager.setCurrentItem(3);

		//根据id的返回值，选择目的Tab页
		/*if (id==1){
			viewPager.setCurrentItem(1);
		}else if (id==2){
			viewPager.setCurrentItem(2);
		}else if (id==3){
			viewPager.setCurrentItem(3);
		}*/
	}

	
	/**
	 * 初始化显示某个界面
	 */
	private void initTab(Intent intent) {
		defaultTab = intent.getIntExtra("tab", 0);
		viewPager.setCurrentItem(defaultTab);
	}
	
	
	/**
	 * Intent界面时,指定显示某个tab
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		initTab(getIntent());
	}
	
	
	/**
	 * 初始化所有控件
	 */
	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setOffscreenPageLimit(4);		//这个很重要,设置4个界面来回切换不会重新加载

		List<Fragment> data = new ArrayList<>();

		fragment1 = new Fragment1();
		fragment2 = new Fragment2();
		fragment3 = new Fragment3();
		fragment4 = new Fragment4();

		data.add(fragment1);
		data.add(fragment2);
		data.add(fragment3);
		data.add(fragment4);

		adapter = new MainFragmentAdapter(getSupportFragmentManager(), data);
		viewPager.setAdapter(adapter); 								// 应用适配器

		//viewPager.setOnPageChangeListener(pageChangeListener); 		// 监听界面滑动,v4包
		viewPager.addOnPageChangeListener(pageChangeListener); 		// 监听界面滑动，v7包
	}
	
	
	/**
	 * 滑动监听
	 */
	private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int index) {
			((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	};

	/**
	 * 菜单组选择事件
	 */
	private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
				case R.id.menu1: {
					viewPager.setCurrentItem(0); 	
					break;
				}
				case R.id.menu2: {
					viewPager.setCurrentItem(1);
					break;
				}
				case R.id.menu3: {
					viewPager.setCurrentItem(2);
					break;
				}case R.id.menu4: {
					viewPager.setCurrentItem(3);
					break;
				}
			}
		}
	};

}
