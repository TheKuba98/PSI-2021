import { UserDto } from "./user";

export class FilterOptionsDto {
  supervisors:UserDto[];
  thesisTypes:string[];
  years:string[];
  fieldNames:string[];
  languages:string[];
}
