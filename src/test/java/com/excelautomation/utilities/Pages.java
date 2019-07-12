package com.excelautomation.utilities;

import com.excelautomation.pages.MileagePage;

public class Pages {
	
	private MileagePage mileage;
	
	public MileagePage mileage() {
		if(mileage==null)
		mileage=new MileagePage();
		return mileage;
	}

}
