import { UserDto } from "./user";

export class ThesisDto {
  theme: string;
  supervisorUsername: string;
  supervisorNames: string;
  type: string;
  year: string;
  fieldName: string;
  language: string;
  status: string;
  authors:UserDto[];
  thesisId:number;;
  reporter:string;
}
