using { ips.poc, sap.common } from '../db/data-model';

service IPSService {
  entity Desks as projection on poc.Desks;
  entity Employees as projection on poc.Employees;
  entity DeskToEmpMapping as projection on poc.DeskToEmpMappings;
}