using { ns2, sap.common } from '../db/schema';

service IPSService {
	entity Building 	@readonly as projection on ns2.Buildings;
	entity Desk 		@readonly as projection on ns2.Desks;
	entity Consultant 	@readonly as projection on ns2.Consultants;
	entity Usage 				  as projection on ns2.Usages;
}