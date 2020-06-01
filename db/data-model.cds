namespace ips.poc;
using {  managed } from '@sap/cds/common';

entity Desks {
  key ID : Integer;
  building: String;
  floor  : String;
  number  : Integer;
}

entity Employees {
  key ID : Integer;
  name   : String;
  Inumber  : String;
}

entity DeskToEmpMappings : managed {
  key ID  : UUID;
  deskId : Integer;
  empId  : Integer;
}