# Fragment跳转、继承PagerAdapter

跳转
```java
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
 ```
 
 
 继承
 ```java
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
```

![screenshot](https://github.com/ykmeory/Fragment_Jump_Inherit/blob/master/screenshot.jpg "截图")
