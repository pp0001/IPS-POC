# IPS-POC Backend Service Project

Project repository: https://github.com/pp0001/IPS-POC  

### Project Structure

#### Create a CAP Project in WEBIDE

new project with Template "*SAP Cloud Business Application*", following wizard input need information. After that you will get the project with the structure shown below. 

[CAP Model Link](https://cap.cloud.sap/docs/about/) 

<img src="./img/Project Structure.png" alt="Project Structure" align="Left" style="zoom:50%;" />

`/db` : Setup database model & mocked master data  

`/srv` : Backend related login  

#### Database Setup  

Defined database model: `/db/Schema.cds ` 

Master Data Related Entities: `Buildings` `Consultants` `Desks`   

Usages Entity: `Usages`  

Map Related Entities and Views: `DeskMap` `DetailtoMap` `DeskMapView` `UsagesView`   

Mock data: `/db/csv`  

For Example:   

	1. Create a file suffix of .hdbtabledata, in this file contains needed impoted table's information. Like target_table, column_mappings, import_columns, source_data, dialect  
 	2. Create a file suffix of .csv, store mock data in this file   

#### Backend Service Setup

##### ODATA Service  

IPS ODATA Service defined in `/srv/ips-poc.cds`   

Read Only Entities used to retrieve master data and mapping related data: `Building` `Desk` `Consultant` `Deskmap` `DetailtoMap` `UsagesView` `DeskMapView`   

Usage Entity used to store desk occupy data: `Usage`  

Function import used to retrieve user ID according to user email: `getEmailByUserId`    

##### APIs

`API Prefix`:  /odata/v2/IPSService

`metadata`: /$metadata  

`Entities`:  `Building` `Desk` `Consultant` `Deskmap` `DetailtoMap` `UsagesView` `DeskMapView` `Usage`  

`Function Import`:  `getEmailByUserId?iNumber=’I320869’`   

`POST Usage Record`: 

api: /odata/v2/Usage

body: 

```json
{
    "consultant_email": "",
    "desk_deskID": "",
    "occupiedFrom": "",
    "occupiedTo": ""
}
```

##### Backend Project Logic Implementation  

Framework: Spring Boot & SAP Cloud Application Programming Model  

Database: HANA  

Function import implementation logic: `/srv/src/main/java/ips/poc/cap/IPSService.java/getEmailById`  [getEmailById](https://github.com/pp0001/IPS-POC/blob/master/srv/src/main/java/ips/poc/cap/IPSService.java)    

```java
@Function(Name = CommonConstant.FUNCTION_IMPORT_GETEMAILBYID, serviceName = CommonConstant.SERVICE_NAME)
	public OperationResponse getEmailById(OperationRequest functionRequest, ExtensionHelper helper) {

		DataSourceHandler handler = helper.getHandler();
		String iNumber = FunctionRequest.getParameters().get(CommonConstant.INUMBER).toString();

		Map<String, Object> keys = new HashMap<>();
		keys.put(CommonConstant.SAPID, iNumber);
		List<String> flattenedElementNames = new ArrayList<>();
		flattenedElementNames.add(CommonConstant.EMAIL);

		EntityData result = null;
		try {
			result = handler.executeRead(CommonConstant.ENTITY_CONSULTANT, keys, flattenedElementNames);
		} catch (DatasourceException e) {
			logger.error("Retrieve Consultant failed");
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("email", result.getElementValue(CommonConstant.EMAIL));
		
		List<Map<String, Object>> complexData = Arrays.asList(map);
		
		return OperationResponse.setSuccess().setComplexData(complexData).response();
	}
```

### Deploy Project to Cloud Foundry

Following [this link](https://sap.github.io/cloud-mta-build-tool/download/) install Cloud MTA Build Tool  

In local project folder, open CMD, run `mbt build` , after successfully build a folder named as `mta_archives` will be created. Then run  `cf deploy mta_archive\IPS_POC_0.0.1.mtar` .   

Before you run cf deploy, please make sure you have already connect our cloud foundry acccount.

#### Check Deployed Service

Open our Cloud Foundry account, checked IPS_POC Application is working.

#### Check Deployed HANA Database

1. Open WEBIDE -> setting -> Cloud Foundry -> setup correct endpoint according our cloud foundry environment  
2. Enable database explorer  
3. Click database explorer -> click add database -> select “IPS_POC-db-hdi-container”    

### Run Project in WEBIDE

1. clone project to WEBIDE

2. build CDS 

   <img src="./img/Build CDS in WEBIDE.png" alt="Build CDS" align="Left" style="zoom:50%;"/>

3. run JAVA application

   <img src="./img/Run Jave Application in WEBIDE.png" alt="Run application" align="Left" style="zoom:50%;" />

### Run Project Locally

1. Java and Maven already installed  
2. In local project update database information to `/db/default-env.json` and `/srv/src/main/resources/application.yaml` line 16 - line 18  
3. Following [this link](https://github.infra.hana.ondemand.com/cloudfoundry/chisel) prepare chisel, then connect chisel in local
4. In local project folder `/db` open cmd, run `npm install` and `npm start` , to deploy data to hana database  
5. Import IPS_POC project to your IDE, and run our project as Spring Boot App  

