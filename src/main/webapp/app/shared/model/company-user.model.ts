import { IUserProfile } from 'app/shared/model/user-profile.model';
import { IBusinessUnit } from 'app/shared/model/business-unit.model';

export interface ICompanyUser {
  id?: number;
  usersUuid?: string | null;
  userProfile?: IUserProfile | null;
  businessUnit?: IBusinessUnit | null;
}

export const defaultValue: Readonly<ICompanyUser> = {};
