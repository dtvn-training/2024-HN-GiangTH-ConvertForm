package com.example.convertform;

import com.example.convertform.dto.input_sheet.PlacementSheet;
import com.example.convertform.dto.input_sheet.SearchTargetSheet;
import com.example.convertform.dto.input_sheet.SiteCategorySheet;
import com.gh.mygreen.xlsmapper.XlsMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConvertformApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ConvertformApplication.class, args);

		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\GiangTH\\Downloads\\template_copy.xlsx");

		XlsMapper xlsMapper = new XlsMapper();

		SearchTargetSheet searchTargetSheet = xlsMapper.load(fileInputStream, SearchTargetSheet.class);
		System.out.println(searchTargetSheet.show());
	}

}
