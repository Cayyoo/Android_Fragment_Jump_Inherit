package com.example.viewpagerfragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * （一）、FragmentPagerAdapter
 * 继承自PagerAdapter，相比通用的PagerAdapter，该类更专注于每一页均为Fragment的情况。
 * 如文档所述，该类内的每一个生成的Fragment都将保存在内存之中，因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter。
 * FragmentPagerAdapter重载实现了几个必须的函数，因此来自PagerAdapter的函数，只需要实现getCount()即可。
 * 且，由于FragmentPagerAdapter.instantiateItem()的实现中，调用了一个新增的虚函数getItem()，因此，我们还至少需要实现一个getItem()。
 * 总体上来说，相对于继承自PagerAdapter更方便一些。
 *
 * 当我们使用FragmentPagerAdapter的时候，它的宿主ViewPager必须有一个id。
 *
 * （二）、FragmentStatePagerAdapter
 * 也是继承自PagerAdapter，但是该PagerAdapter的实现将只保留当前页面，当页面离开视线后，就会被消除，释放其资源；
 * 而在页面需要显示时，生成新的页面(其工作方式和listview非常相似)。
 * 这么实现的好处就是当拥有大量的页面时，不必在内存中占用大量的内存。
 *
 * FragmentStatePagerAdapter比FragmentPagerAdapter更适合用于很多界面之间的转换，而且消耗更少的内存资源。
 * 同样的，宿主ViewPager也必须有一个id。
 */


/**
 * 对于页面相对较少的情况，我仍旧希望能够将生成的 Fragment 保存在内存中，在需要显示的时候直接调用，而不要产生生成、销毁对象的额外的开销，这样效率更高。
 * 这种情况下，选择FragmentPagerAdapter是更适合，不加考虑的选择FragmentStatePagerAdapter是不合适的。
 *
 * FragmentStatePagerAdapter会在因POSITION_NONE触发调用的destroyItem()中真正的释放资源，重新建立一个新的Fragment；
 * 而FragmentPagerAdapter仅仅会在destroyItem()中detach这个Fragment，在instantiateItem()时会使用旧的Fragment，并触发attach，因此没有释放资源及重建的过程。
 */


/**
 * 平常使用的FragmentPagerAdapter和FragmentStatePagerAdapter来自android.support.v4.app包用来构建ViewPager。
 * FragmentPagerAdapter更多的用于少量界面的ViewPager，比如Tab。划过的fragment会保存在内存中，尽管已经划过。
 * 而FragmentStatePagerAdapter和ListView有点类似，会保存当前界面，以及下一个界面和上一个界面（如果有），最多保存3个，其他会被销毁掉。
 *
 * 要注意的是FragmentStatePagerAdapter可能不经意间会造成内存未正常回收，严重导致内存溢出。比如图片资源没有释放，资源引用问题。
 * （之前碰到过EditTextt由于保存焦点导致Fragment未被释放，以至于内存溢出，设置editText.saveEanble(false)就可以解决此问题)。
 */
public class MainFragmentAdapter extends FragmentStatePagerAdapter {
	private List<Fragment> data;

	public MainFragmentAdapter(FragmentManager fm, List<Fragment> data) {
		super(fm);
		this.data = data;
	}

	public MainFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		return data.get(position);
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

}
