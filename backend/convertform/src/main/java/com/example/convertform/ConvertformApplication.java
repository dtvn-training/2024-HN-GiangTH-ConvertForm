package com.example.convertform;

import com.example.convertform.dto.input_sheet.*;
import com.gh.mygreen.xlsmapper.XlsMapper;
import com.gh.mygreen.xlsmapper.cellconverter.TypeBindException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConvertformApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ConvertformApplication.class, args);

		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\GiangTH\\Downloads\\input_true.xlsx");

		XlsMapper xlsMapper = new XlsMapper();

		try {
			Object[] sheets = xlsMapper.loadMultiple(
					fileInputStream,
					new Class[]{CampaignSheet.class, AdGroupSheet.class, AreaSheet.class, TextSheet.class, PlacementSheet.class, SearchTargetSheet.class, SiteCategorySheet.class}
			);

			for (Object sheet : sheets) {
				System.out.println(sheet.toString());
			}
			System.out.println(((CampaignSheet) sheets[0]).show());
		} catch (TypeBindException e) {
			System.out.println(e.getMessageVars().getOrDefault("type", new Object()));
		}

	}

}
