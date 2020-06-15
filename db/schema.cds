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
	monitorNum		: Integer						//number of monitors
}

entity Usages : cuid, managed {
    consultant      : Association to Consultants;	//consultant_email
    desk	        : Association to Desks;			//desk_deskID
    occupiedFrom    : DateTime;
    occupiedTo      : DateTime;
    resource        : Integer default 1;            //for extensibility
}