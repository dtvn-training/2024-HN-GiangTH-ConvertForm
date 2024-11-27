[1mdiff --git a/backend/convertform/pom.xml b/backend/convertform/pom.xml[m
[1mindex d3e7c22..2d19bec 100644[m
[1m--- a/backend/convertform/pom.xml[m
[1m+++ b/backend/convertform/pom.xml[m
[36m@@ -67,6 +67,15 @@[m
 			<artifactId>xlsmapper</artifactId>[m
 			<version>2.2</version>[m
 		</dependency>[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.springframework.boot</groupId>[m
[32m+[m			[32m<artifactId>spring-boot-starter-validation</artifactId>[m
[32m+[m		[32m</dependency>[m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.apache.poi</groupId>[m
[32m+[m			[32m<artifactId>poi</artifactId>[m
[32m+[m			[32m<version>5.2.3</version>[m
[32m+[m		[32m</dependency>[m
 	</dependencies>[m
 [m
 	<build>[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/ConvertformApplication.java b/backend/convertform/src/main/java/com/example/convertform/ConvertformApplication.java[m
[1mindex fc5290f..3d6f09c 100644[m
[1m--- a/backend/convertform/src/main/java/com/example/convertform/ConvertformApplication.java[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertform/ConvertformApplication.java[m
[36m@@ -1,28 +1,16 @@[m
 package com.example.convertform;[m
 [m
[31m-import com.example.convertform.dto.input_sheet.PlacementSheet;[m
[31m-import com.example.convertform.dto.input_sheet.SearchTargetSheet;[m
[31m-import com.example.convertform.dto.input_sheet.SiteCategorySheet;[m
[31m-import com.gh.mygreen.xlsmapper.XlsMapper;[m
 import org.springframework.boot.SpringApplication;[m
 import org.springframework.boot.autoconfigure.SpringBootApplication;[m
 import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;[m
[32m+[m[32mimport org.springframework.context.annotation.ComponentScan;[m
 [m
[31m-import java.io.FileInputStream;[m
 import java.io.IOException;[m
 [m
 @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)[m
[32m+[m[32m@ComponentScan[m
 public class ConvertformApplication {[m
[31m-[m
 	public static void main(String[] args) throws IOException {[m
 		SpringApplication.run(ConvertformApplication.class, args);[m
[31m-[m
[31m-		FileInputStream fileInputStream = new FileInputStream("C:\\Users\\GiangTH\\Downloads\\template_copy.xlsx");[m
[31m-[m
[31m-		XlsMapper xlsMapper = new XlsMapper();[m
[31m-[m
[31m-		SearchTargetSheet searchTargetSheet = xlsMapper.load(fileInputStream, SearchTargetSheet.class);[m
[31m-		System.out.println(searchTargetSheet.show());[m
 	}[m
[31m-[m
 }[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/config/BeanCustomConfig.java b/backend/convertform/src/main/java/com/example/convertform/config/BeanCustomConfig.java[m
[1mnew file mode 100644[m
[1mindex 0000000..82a4590[m
[1m--- /dev/null[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertform/config/BeanCustomConfig.java[m
[36m@@ -0,0 +1,13 @@[m
[32m+[m[32mpackage com.example.convertform.config;[m
[32m+[m
[32m+[m[32mimport com.gh.mygreen.xlsmapper.XlsMapper;[m
[32m+[m[32mimport org.springframework.context.annotation.Bean;[m
[32m+[m[32mimport org.springframework.context.annotation.Configuration;[m
[32m+[m
[32m+[m[32m@Configuration[m
[32m+[m[32mpublic class BeanCustomConfig {[m
[32m+[m[32m    @Bean[m
[32m+[m[32m    public XlsMapper xlsMapperCfg() {[m
[32m+[m[32m        return new XlsMapper();[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/controller/FileController.java b/backend/convertform/src/main/java/com/example/convertform/controller/FileController.java[m
[1mnew file mode 100644[m
[1mindex 0000000..418b587[m
[1m--- /dev/null[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertform/controller/FileController.java[m
[36m@@ -0,0 +1,30 @@[m
[32m+[m[32mpackage com.example.convertform.controller;[m
[32m+[m
[32m+[m[32mimport com.example.convertform.service.impl.FileReadService;[m
[32m+[m[32mimport org.springframework.beans.factory.annotation.Autowired;[m
[32m+[m[32mimport org.springframework.http.ResponseEntity;[m
[32m+[m[32mimport org.springframework.stereotype.Controller;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.GetMapping;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.PostMapping;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.RequestMapping;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.RestController;[m
[32m+[m
[32m+[m[32mimport java.io.IOException;[m
[32m+[m
[32m+[m[32m@RestController[m
[32m+[m[32m@RequestMapping("/api")[m
[32m+[m[32mpublic class FileController {[m
[32m+[m[32m    @Autowired[m
[32m+[m[32m    FileReadService fileReadService;[m
[32m+[m
[32m+[m[32m    @PostMapping("/upload")[m
[32m+[m[32m    ResponseEntity<String> upload() throws IOException {[m
[32m+[m[32m        String ans = fileReadService.readInputFileDemo();[m
[32m+[m[32m        return ResponseEntity.status(500).body(ans);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @GetMapping("/get")[m
[32m+[m[32m    String get() {[m
[32m+[m[32m        return "Still work";[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/controller/GlobalExceptionHandler.java b/backend/convertform/src/main/java/com/example/convertform/controller/GlobalExceptionHandler.java[m
[1mnew file mode 100644[m
[1mindex 0000000..93d8825[m
[1m--- /dev/null[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertform/controller/GlobalExceptionHandler.java[m
[36m@@ -0,0 +1,60 @@[m
[32m+[m[32mpackage com.example.convertform.controller;[m
[32m+[m
[32m+[m[32mimport com.gh.mygreen.xlsmapper.cellconverter.TypeBindException;[m
[32m+[m[32mimport com.gh.mygreen.xlsmapper.textformatter.TextParseException;[m
[32m+[m[32mimport jakarta.validation.ValidationException;[m
[32m+[m[32mimport org.slf4j.Logger;[m
[32m+[m[32mimport org.slf4j.LoggerFactory;[m
[32m+[m[32mimport org.springframework.http.HttpStatus;[m
[32m+[m[32mimport org.springframework.http.ResponseEntity;[m
[32m+[m[32mimport org.springframework.validation.FieldError;[m
[32m+[m[32mimport org.springframework.web.bind.MethodArgumentNotValidException;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.ExceptionHandler;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.ResponseStatus;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.RestControllerAdvice;[m
[32m+[m
[32m+[m[32mimport java.io.IOException;[m
[32m+[m[32mimport java.text.ParseException;[m
[32m+[m[32mimport java.util.HashMap;[m
[32m+[m[32mimport java.util.Map;[m
[32m+[m
[32m+[m[32m@RestControllerAdvice[m
[32m+[m[32mpublic class GlobalExceptionHandler {[m
[32m+[m[32m    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);[m
[32m+[m
[32m+[m[32m    @ExceptionHandler(TypeBindException.class)[m
[32m+[m[32m    public ResponseEntity<String> handleTypeBind(TypeBindException e) {[m
[32m+[m[32m        logger.trace(e.toString());[m
[32m+[m[32m        String msg = e.getMessage();[m
[32m+[m[32m        int indexClass1 = msg.lastIndexOf('.');[m
[32m+[m[32m        int indexClass2 = msg.indexOf('#');[m
[32m+[m[32m        int index1 = e.getMessage().indexOf('(');[m
[32m+[m[32m        int index2 = e.getMessage().indexOf(')');[m
[32m+[m[32m        return ResponseEntity.status(500).body(msg.substring(35, indexClass2) + " - " + msg.substring(index1 + 1, index2));[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @ExceptionHandler(ValidationException.class)[m
[32m+[m[32m    public ResponseEntity<String> handleIO(ValidationException e) {[m
[32m+[m[32m        logger.trace(e.toString());[m
[32m+[m[32m        return ResponseEntity.status(500).body(e.getMessage());[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @ExceptionHandler(ParseException.class)[m
[32m+[m[32m    public ResponseEntity<String> handleParse(ValidationException e) {[m
[32m+[m[32m        logger.trace(e.toString());[m
[32m+[m[32m        return ResponseEntity.status(500).body(e.getMessage());[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @ResponseStatus(HttpStatus.BAD_REQUEST)[m
[32m+[m[32m    @ExceptionHandler(MethodArgumentNotValidException.class)[m
[32m+[m[32m    public Map<String, String> handleMethod(MethodArgumentNotValidException ex) {[m
[32m+[m[32m        Map<String, String> errors = new HashMap<>();[m
[32m+[m[32m        ex.getBindingResult().getAllErrors().forEach((err) -> {[m
[32m+[m[32m            String fieldName = ((FieldError) err).getField();[m
[32m+[m[32m            String errMessage = err.getDefaultMessage();[m
[32m+[m[32m            errors.put(fieldName, errMessage);[m
[32m+[m[32m        });[m
[32m+[m
[32m+[m[32m        return errors;[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/dto/input/AdGroupRecord.java b/backend/convertform/src/main/java/com/example/convertform/dto/input/AdGroupRecord.java[m
[1mindex 6dd2d2b..de2f7f3 100644[m
[1m--- a/backend/convertform/src/main/java/com/example/convertform/dto/input/AdGroupRecord.java[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertform/dto/input/AdGroupRecord.java[m
[36m@@ -1,15 +1,109 @@[m
 package com.example.convertform.dto.input;[m
 [m
[32m+[m[32mimport com.example.convertform.validation.annotation.SelectionRequired;[m
 import com.gh.mygreen.xlsmapper.annotation.XlsColumn;[m
[32m+[m[32mimport jakarta.validation.constraints.NotNull;[m
 import lombok.Data;[m
[32m+[m[32mimport lombok.EqualsAndHashCode;[m
 [m
[32m+[m[32m@EqualsAndHashCode(callSuper = true)[m
 @Data[m
[31m-public class AdGroupRecord {[m
[32m+[m[32mpublic class AdGroupRecord extends BaseRecord {[m
[32m+[m[32m    @XlsColumn(columnName = "No")[m
[32m+[m[32m    int no;[m
[32m+[m
     @XlsColumn(columnName = "Status")[m
[32m+[m[32m    @NotNull(message = "AdGp status is required")[m
[32m+[m[32m    @SelectionRequired(values = {"New Request", "Pre-Request", "Published", "Pending", "Suspended"})[m
     String status;[m
[32m+[m
     @XlsColumn(columnName = "Campaign Name")[m
[32m+[m[32m    @NotNull(message = "Campaign Name is required")[m
     String cName;[m
[32m+[m
     @XlsColumn(columnName = "Ad Group Name")[m
[32m+[m[32m    @NotNull(message = "Ad Group Name is required")[m
     String adGpName;[m
 [m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 1")[m
[32m+[m[32m    @SelectionRequired(values = {"Search Targeting", "Placement"})[m
[32m+[m[32m    String targetName1;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 1")[m
[32m+[m[32m    @SelectionRequired(values = {"①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩"})[m
[32m+[m[32m    String number1;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 2")[m
[32m+[m[32m    @SelectionRequired(values = {"Search Targeting", "Placement"})[m
[32m+[m[32m    String targetName2;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 2")[m
[32m+[m[32m    String number2;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 3")[m
[32m+[m[32m    @SelectionRequired(values = {"Search Targeting", "Placement"})[m
[32m+[m[32m    String targetName3;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 3")[m
[32m+[m[32m    String number3;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 4")[m
[32m+[m[32m    @SelectionRequired(values = {"Search Targeting", "Placement"})[m
[32m+[m[32m    String targetName4;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 4")[m
[32m+[m[32m    String number4;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 5")[m
[32m+[m[32m    String targetName5;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 5")[m
[32m+[m[32m    String number5;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 6")[m
[32m+[m[32m    String targetName6;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 6")[m
[32m+[m[32m    String number6;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 7")[m
[32m+[m[32m    String targetName7;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 7")[m
[32m+[m[32m    String number7;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 8")[m
[32m+[m[32m    String targetName8;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 8")[m
[32m+[m[32m    String number8;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 9")[m
[32m+[m[32m    String targetName9;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 9")[m
[32m+[m[32m    String number9;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Targeting Name 10")[m
[32m+[m[32m    String targetName10;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Number 10")[m
[32m+[m[32m    String number10;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Device")[m
[32m+[m[32m    @SelectionRequired(values = {"ALL", "PC/TB", "SP/TB", "PC/SP", "PC", "TB", "SP"})[m
[32m+[m[32m    String device;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Gender")[m
[32m+[m[32m    @SelectionRequired(values = {"ALL", "Male", "Female"})[m
[32m+[m[32m    String gender;[m
[32m+[m
[32m+[m[32m    @XlsColumn(columnName = "Age")[m
[32m+[m[32m    @SelectionRequired(values = {"ALL", "18+", "25+", "30+", "40+", "50+", "60+", "70+"})[m
[32m+[m[32m    String age;[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int getNo() {[m
[32m+[m[32m        return this.no;[m
[32m+[m[32m    }[m
 }[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/dto/input/AreaRecord.java b/backend/convertform/src/main/java/com/example/convertform/dto/input/AreaRecord.java[m
[1mindex bb1b2d1..9901688 100644[m
[1m--- a/backend/convertform/src/main/java/com/example/convertform/dto/input/AreaRecord.java[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertform/dto/input/AreaRecord.java[m
[36m@@ -1,16 +1,36 @@[m
 package com.example.convertform.dto.input;[m
 [m
[32m+[m[32mimport com.example.convertform.validation.annotation.RadiusLimit;[m
 import com.gh.mygreen.xlsmapper.annotation.XlsColumn;[m
[32m+[m[32mimport com.gh.mygreen.xlsmapper.annotation.XlsDefaultValue;[m
[32m+[m[32mimport com.gh.mygreen.xlsmapper.annotation.XlsTrim;[m
[32m+[m[32mimport jakarta.validation.constraints.NotNull;[m
 import lombok.Data;[m
[32m+[m[32mimport lombok.EqualsAndHashCode;[m
 [m
[32m+[m[32m@EqualsAndHashCode(callSuper = true)[m
 @Data[m
[31m-public class AreaRecord {[m
[32m+[m[32mpublic class AreaRecord extends BaseRecord {[m
[32m+[m[32m    @XlsColumn(columnName = "No.")[m
[32m+[m[32m    int no;[m
[32m+[m
     @XlsColumn(columnName = "Campaign Name")[m
[32m+[m[32m    @NotNull(message = "Campaign Name is required")[m
     String cName;[m
[32m+[m
     @XlsColumn(columnName = "Ad Group Name")[m
[32m+[m[32m    @NotNull(message = "Campaign Name is required")[m
     String adGpName;[m
[32m+[m
     @XlsColumn(columnName = "Location")[m
     String location;[m
[32m+[m
     @XlsColumn(columnName = "Radius")[m
[31m-    double radius;[m
[32m+[m[32m    @RadiusLimit(message = "radius must be >=1 and <= 80")[m
[32m+[m[32m    String radius;[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public int getNo() {[m
[32m+[m[32m        return this.no;[m
[32m+[m[32m    }[m
 }[m
[1mdiff --git a/backend/convertform/src/main/java/com/example/convertform/dto/input/BaseRecord.java b/backend/convertform/src/main/java/com/example/convertform/dto/input/BaseRecord.java[m
[1mnew file mode 100644[m
[1mindex 0000000..6150f05[m
[1m--- /dev/null[m
[1m+++ b/backend/convertform/src/main/java/com/example/convertfo