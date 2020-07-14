namespace ns2;
using { managed, cuid, temporal } from '@sap/cds/common';

entity Buildings {
    key buildingID	: String(5);
    buildingName    : String(200);
    country         : String(20);
    LocationID      : String(3);      //city
    LocationName    : String(20);     //city name
    postalCode      : String;
    street          : String(300);
    longitude       : String;
    latitude        : String;
    geoLink         : String;     //google map link
    portalLink      : String      //buidling information in portal
}

entity Consultants {
    key email       : String(35);
    firstName       : String(20);
    lastName        : String(20);
    SAPID           : String(7);
    mobile          : Integer;
    department      : String(50);
    restricted      : Boolean;        //Not allowed to use desk
}

entity Desks {
    key deskID      : String(15);
    deskNum         : Integer;
    building        : Association to Buildings;		//building_buildingID
    floor           : Integer;
    room            : String(10); 
    resource        : Integer default 1;            //for extensibility
    isHotdesk		: Boolean;
	nearWindow		: Boolean;
	nearKitchin		: Boolean;
	nearEntrance	: Boolean;						
	isLiftable		: Boolean;
	monitorNum		: Integer;						//number of monitors
	
	to_DeskMap		: Association to DeskMap on to_DeskMap.desk = $self
}

entity Usages : cuid, managed {
    consultant      : Association to Consultants;	//consultant_email
    desk	        : Association to Desks;			//desk_deskID
    occupiedFrom    : DateTime;
    occupiedTo      : DateTime;
    resource        : Integer default 1;            //for extensibility
}

entity DeskMap {
	key desk      	: Association to Desks;
	detailID 		: String(30);
	x				: String(10);
	y				: String(10);
	xMin			: String(10);
	yMin			: String(10);
	xMax			: String(10);
	yMax			: String(10);
}

entity DetailtoMap {
	key detailID	:String(30);
	mapID			:String(30); 
	xMin			: String(10);
	yMin			: String(10);
	xMax			: String(10);
	yMax			: String(10);
}

view DeskMapView as select from DeskMap as dm
{
	*,
	desk.isHotdesk,
	desk.isLiftable
};

view UsagesView as select from Usages as u
{
	Key ID,
	occupiedFrom,
	occupiedTo,
	resource,
	u.desk.isHotdesk,
	u.desk.to_DeskMap.detailID,
	u.desk.to_DeskMap.x,
	u.desk.to_DeskMap.y,
	u.desk.to_DeskMap.xMin,
	u.desk.to_DeskMap.yMin,
	u.desk.to_DeskMap.xMax,
	u.desk.to_DeskMap.yMax,
	consultant.SAPID,
	consultant.department,
	consultant.email,
	consultant.firstName,
	consultant.lastName
	consultant,
	desk
};
