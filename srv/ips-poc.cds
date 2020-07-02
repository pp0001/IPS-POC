using { ns2, sap.common } from '../db/schema';

service IPSService {
	entity Building 	@readonly as projection on ns2.Buildings;
	entity Desk 		@readonly as projection on ns2.Desks;
	entity Consultant 	@readonly as projection on ns2.Consultants;
	entity Usage 				  as projection on ns2.Usages;
	entity DeskMap		@readonly as projection on ns2.DeskMap;
	entity DetailtoMap	@readonly as projection on ns2.DetailtoMap;
	entity UsagesView   @readonly as projection on ns2.UsagesView;
	entity DeskMapView 	@readonly as projection on ns2.DeskMapView;
	type EmailByINumber {
		email: String;
	};
	function getEmailByUserId(iNumber: String) returns EmailByINumber;
}