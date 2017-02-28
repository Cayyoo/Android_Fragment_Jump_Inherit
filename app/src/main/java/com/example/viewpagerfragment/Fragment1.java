package com.example.viewpagerfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Fragment1 extends Fragment implements View.OnClickListener{
	private View view; // 界面主布局
	private TextView textView;
	private Button btn_01,btn_02,btn_03,btn_04;

	/*private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;*/

	private View findViewById(int id) {
		return view.findViewById(id);
	}

	private void initView(){
		textView = (TextView) findViewById(R.id.textView);
		textView.setText("界面1");

		btn_01= (Button) this.findViewById(R.id.btn_01);
		btn_02= (Button) this.findViewById(R.id.btn_02);
		btn_03= (Button) this.findViewById(R.id.btn_03);
		btn_04= (Button) this.findViewById(R.id.btn_04);

		btn_01.setOnClickListener(this);
		btn_02.setOnClickListener(this);
		btn_03.setOnClickListener(this);
		btn_04.setOnClickListener(this);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//初始化FragmentManager
		/*fragmentManager=this.getFragmentManager();*/
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment, null);

		initView();

		return view;
	}


	@Override
	public void onClick(View v) {
		/*fragmentTransaction=fragmentManager.beginTransaction();*/

		Intent intent=new Intent(getActivity(),MainActivity.class);

		switch (v.getId()){
			case R.id.btn_01:
				//fragmentTransaction.replace(R.id.fragment, new Fragment2());

				intent.putExtra("currentId", 0);
				break;
			case R.id.btn_02:
				/*fragmentTransaction.replace(R.id.fragment,new Fragment3());*/

				intent.putExtra("currentId", 1);
				break;
			case R.id.btn_03:
				/*fragmentTransaction.replace(R.id.fragment,new Fragment4());*/

				intent.putExtra("currentId", 2);
				break;
			case R.id.btn_04:
				intent.putExtra("currentId", 3);
				break;
			default:
				break;
		}

		/*fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();*/

		startActivity(intent);
	}

}
