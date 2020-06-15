package ips.poc.cap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sap.cloud.sdk.service.prov.api.DataSourceHandler;
import com.sap.cloud.sdk.service.prov.api.EntityData;
import com.sap.cloud.sdk.service.prov.api.ExtensionHelper;
import com.sap.cloud.sdk.service.prov.api.exception.DatasourceException;
import com.sap.cloud.sdk.service.prov.api.operations.Update;
import com.sap.cloud.sdk.service.prov.api.request.CreateRequest;
import com.sap.cloud.sdk.service.prov.api.request.UpdateRequest;
import com.sap.cloud.sdk.service.prov.api.response.CreateResponse;
import com.sap.cloud.sdk.service.prov.api.response.UpdateResponse;

import ips.poc.constant.CommonConstant;

@Component
public class IPSService {

    private static final Logger logger = LoggerFactory.getLogger(IPSService.class.getName());
    
    private static final List<String> KEY_LIST = Arrays.asList("ID");
    
//    @Update(entity = CommonConstant.ENTITY_USAGE, serviceName = CommonConstant.SERVICE_NAME)
    public UpdateResponse updateUsage(UpdateRequest updateRequest, ExtensionHelper extensionHelper) {
    	logger.info("Start Update Usage Entity");
    	
    	Map<String, Object> entity = updateRequest.getMapData();
    	
    	return UpdateResponse.setSuccess().response();
    }

    
//    @Create(entity = CommonConstant.ENTITY_USAGE, serviceName = CommonConstant.SERVICE_NAME)
    public CreateResponse createUsageEntity(CreateRequest req, ExtensionHelper helper) throws DatasourceException {
    	logger.info("Start Create Usage Entity");
    	
    	DataSourceHandler handler = helper.getHandler();
		EntityData entityData = req.getData();
		
		Map<String, Object> entMap = entityData.asMap();
		
		EntityData request = EntityData.createFromMap(entMap, KEY_LIST, CommonConstant.ENTITY_DESK);
		EntityData result = handler.executeInsert(request, true);
    	
    	
		return CreateResponse.setSuccess().setData(result).response();
    }


}